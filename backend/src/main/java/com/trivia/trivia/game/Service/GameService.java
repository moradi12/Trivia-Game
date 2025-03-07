package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.GameMode;
import com.trivia.trivia.game.Entity.Player;
import com.trivia.trivia.game.Entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private static final int MAX_FAILURES = 3;
    private static final int TIME_LIMIT_SECONDS = 30;

    // Track how many failures so far; 3 means game over
    private int failureCount = 0;
    // Track how many correct answers in PvC mode
    private int correctAnswers = 0;
    // Track how many correct answers for each player in PvP mode
    private int player1Correct = 0;
    private int player2Correct = 0;

    // Category is set once from the main menu
    private Category selectedCategory;

    // Game mode: PVC or PVP
    private GameMode gameMode = GameMode.PVC;
    // Players
    private Player player1;
    private Player player2;
    // Whose turn it is in PvP
    private int currentPlayerTurn = 1;

    /**
     * Map to track question start times: (questionId -> start time in seconds).
     * Once an answer is submitted, we remove that question’s entry.
     */
    private final Map<Integer, Integer> questionStartTimeMap = new ConcurrentHashMap<>();

    private final QuestionService questionService;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Starts a new game session.
     *
     * @param mode      Game mode (PVC or PVP).
     * @param category  Category of questions.
     * @param player1   The first player.
     * @param player2   The second player (may be null if mode is PVC).
     */
    public void startGame(GameMode mode, Category category, Player player1, Player player2) {
        this.gameMode = mode;
        this.selectedCategory = category;
        this.player1 = player1;
        this.player2 = player2;

        // Reset counters
        this.failureCount = 0;
        this.correctAnswers = 0;
        this.player1Correct = 0;
        this.player2Correct = 0;
        this.currentPlayerTurn = 1;

        questionStartTimeMap.clear();

        logger.info("Game started in {} mode with category {}.", mode, category);
    }

    /**
     * Processes an answer from the client.
     *
     * @param answerDTO Contains the questionId and selectedAnswerIndex.
     * @return GameResponse indicating correctness, next question, and relevant info.
     */
    public GameResponse processAnswer(AnswerDTO answerDTO) {

        // If game is already over due to failures, notify the client
        if (failureCount >= MAX_FAILURES) {
            logger.warn("Game over! Maximum failures reached.");
            return new GameResponse(false, "Game is already over!", null, failureCount);
        }

        // Retrieve and remove the question’s start time from the map
        Integer questionStartTime = questionStartTimeMap.remove(answerDTO.getQuestionId());
        if (questionStartTime == null) {
            logger.error("No start time recorded for question ID {}.", answerDTO.getQuestionId());
            return new GameResponse(
                    false,
                    "Invalid question timing data or you are re-answering a finished question.",
                    null,
                    failureCount
            );
        }

        // Check timing
        int currentTimeSeconds = (int) (System.currentTimeMillis() / 1000);
        int elapsedSeconds = currentTimeSeconds - questionStartTime;
        if (elapsedSeconds > TIME_LIMIT_SECONDS) {
            failureCount++;
            logger.info("Answer timed out ({} seconds). Failures: {}/{}", elapsedSeconds, failureCount, MAX_FAILURES);
            if (failureCount >= MAX_FAILURES) {
                return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
            }
            return new GameResponse(false, "Time's up! You did not answer within 30 seconds.", null, failureCount);
        }

        // Check correctness
        Optional<Question> questionOpt = questionService.getQuestionById(answerDTO.getQuestionId());
        if (!questionOpt.isPresent()) {
            logger.error("Question ID {} not found in DB!", answerDTO.getQuestionId());
            return new GameResponse(false, "Question not found.", null, failureCount);
        }
        Question question = questionOpt.get();
        boolean isCorrect = (question.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());

        // Build response message
        String message;
        if (gameMode == GameMode.PVP) {
            // In PvP, track current player's correctness
            int answeringPlayer = currentPlayerTurn;
            if (!isCorrect) {
                failureCount++;
                logger.info("Player {} answered incorrectly. Failures: {}/{}",
                        answeringPlayer, failureCount, MAX_FAILURES);
            } else {
                if (answeringPlayer == 1) {
                    player1Correct++;
                } else {
                    player2Correct++;
                }
                logger.info("Player {} answered correctly.", answeringPlayer);
            }
            // Switch turn
            currentPlayerTurn = (answeringPlayer == 1) ? 2 : 1;

            message = (isCorrect
                    ? "Player " + answeringPlayer + " answered correctly! "
                    : "Player " + answeringPlayer + " answered incorrectly! ")
                    + "Next turn: Player " + currentPlayerTurn + ".";
        } else {
            // PvC
            if (!isCorrect) {
                failureCount++;
                logger.info("Incorrect answer. Failures: {}/{}", failureCount, MAX_FAILURES);
            } else {
                correctAnswers++;
                logger.info("Correct answer! Total correct: {}", correctAnswers);
            }

            message = isCorrect
                    ? "Correct answer! Total correct: " + correctAnswers + ". Here is your next question."
                    : "Wrong answer. Total correct: " + correctAnswers + ". Here is your next question.";
        }

        // Check if game ended due to new failure
        if (failureCount >= MAX_FAILURES) {
            return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
        }

        // Fetch the next question
        Optional<Question> nextQuestionOpt = questionService.getRandomQuestionByCategory(selectedCategory);
        Question nextQuestion = nextQuestionOpt.orElse(null);

        // Record start time for the new question if not null
        if (nextQuestion != null) {
            int startTime = (int) (System.currentTimeMillis() / 1000);
            questionStartTimeMap.put(nextQuestion.getId(), startTime);
        }

        QuestionDTO nextQuestionDto = null;
        if (nextQuestion != null) {
            nextQuestionDto = new QuestionDTO(
                    nextQuestion.getId(),
                    nextQuestion.getText(),
                    nextQuestion.getOptions(),
                    nextQuestion.getCorrectIndex(),
                    nextQuestion.getCategory(),
                    nextQuestion.getDifficulty()
            );
        }

        return new GameResponse(isCorrect, message, nextQuestionDto, failureCount);
    }

    /**
     * Serves (fetches) a new question, records its start time, and returns a DTO.
     */
    public QuestionDTO serveNextQuestion() {
        Optional<Question> nextQuestionOpt = questionService.getRandomQuestionByCategory(selectedCategory);
        if (!nextQuestionOpt.isPresent()) {
            return null;
        }

        Question nextQuestion = nextQuestionOpt.get();
        int startTime = (int) (System.currentTimeMillis() / 1000);
        questionStartTimeMap.put(nextQuestion.getId(), startTime);

        logger.info("Serving question ID {} with start time {}.", nextQuestion.getId(), startTime);

        return new QuestionDTO(
                nextQuestion.getId(),
                nextQuestion.getText(),
                nextQuestion.getOptions(),
                nextQuestion.getCorrectIndex(),
                nextQuestion.getCategory(),
                nextQuestion.getDifficulty()
        );
    }

    /**
     * Resets game counters but keeps the selected category.
     */
    public void resetGame() {
        failureCount = 0;
        correctAnswers = 0;
        player1Correct = 0;
        player2Correct = 0;
        currentPlayerTurn = 1;
        questionStartTimeMap.clear();

        logger.info("Game state reset.");
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getCorrectAnswers() {
        // In PvP, total correct is simply the sum of both players’ correct answers
        return gameMode == GameMode.PVC ? correctAnswers : (player1Correct + player2Correct);
    }

    /**
     * Returns a textual summary of the current game state.
     */
    public String getGameState() {
        String baseState = "Category: " + (selectedCategory != null ? selectedCategory : "Not selected")
                + ", Failures: " + failureCount + "/" + MAX_FAILURES;

        if (gameMode == GameMode.PVP) {
            return "Game Mode: PvP. " + baseState
                    + ", Player 1 Correct: " + player1Correct
                    + ", Player 2 Correct: " + player2Correct
                    + ", Next turn: Player " + currentPlayerTurn + ".";
        } else {
            return "Game Mode: PvC. " + baseState
                    + ", Total Correct: " + correctAnswers;
        }
    }
}
