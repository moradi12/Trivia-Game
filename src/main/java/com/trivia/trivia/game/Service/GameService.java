package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.GameMode;  // New enum: PVP, PVC
import com.trivia.trivia.game.Entity.Player;    // New simple Player class
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
    private static final int TIME_LIMIT_SECONDS = 30; // 30 seconds limit

    private int failureCount = 0;
    // For PVC mode, a single total correct counter is used.
    private int correctAnswers = 0;
    // For PVP mode, separate scores are maintained.
    private int player1Correct = 0;
    private int player2Correct = 0;

    private Category selectedCategory = null;

    // New fields for game mode and players:
    private GameMode gameMode = GameMode.PVC;  // default mode is Player vs Computer
    private Player player1;
    private Player player2; // used only in PvP; for PVC this remains null
    // In PvP mode, we track whose turn it is (1 or 2)
    private int currentPlayerTurn = 1;

    // Map to track when a question was served (questionId -> start time in seconds)
    private final Map<Integer, Integer> questionStartTimeMap = new ConcurrentHashMap<>();

    private final QuestionService questionService;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Starts a new game session.
     * For PVC mode, player2 can be passed as null.
     */
    public void startGame(GameMode mode, Category category, Player player1, Player player2) {
        this.gameMode = mode;
        this.selectedCategory = category;
        this.player1 = player1;
        this.player2 = player2; // For PVC mode, this may be null.
        this.failureCount = 0;
        this.correctAnswers = 0;
        this.player1Correct = 0;
        this.player2Correct = 0;
        this.currentPlayerTurn = 1;
        questionStartTimeMap.clear();
        logger.info("Game started in {} mode with category {}.", mode, category);
    }

    public GameResponse processAnswer(AnswerDTO answerDTO) {
        // Validate the incoming answer data.
        if (answerDTO == null) {
            logger.error("AnswerDTO is null.");
            return new GameResponse(false, "Invalid answer data.", null, failureCount);
        }

        if (selectedCategory == null) {
            return new GameResponse(false, "Please select a category before playing!", null, failureCount);
        }

        if (failureCount >= MAX_FAILURES) {
            logger.warn("Game over! Player is still attempting to answer.");
            return new GameResponse(false, "Game is already over!", null, failureCount);
        }

        // Retrieve and validate the recorded start time for the question.
        Integer questionStartTime = questionStartTimeMap.get(answerDTO.getQuestionId());
        if (questionStartTime == null) {
            logger.error("No start time recorded for question ID {}.", answerDTO.getQuestionId());
            return new GameResponse(false, "Invalid question timing data.", null, failureCount);
        }

        // Remove the start time regardless of outcome to clean up memory.
        questionStartTimeMap.remove(answerDTO.getQuestionId());

        // Calculate elapsed time in seconds.
        int currentTimeSeconds = (int) (System.currentTimeMillis() / 1000);
        int elapsedSeconds = currentTimeSeconds - questionStartTime;
        if (elapsedSeconds > TIME_LIMIT_SECONDS) {
            failureCount++;
            logger.info("Answer timed out ({} seconds elapsed). Failures: {}/{}", elapsedSeconds, failureCount, MAX_FAILURES);
            if (failureCount >= MAX_FAILURES) {
                logger.info("Game over! Maximum failures reached.");
                return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
            }
            return new GameResponse(false, "Time's up! You did not answer within 30 seconds.", null, failureCount);
        }

        // Retrieve the question by its ID.
        Optional<Question> questionOpt = questionService.getQuestionById(answerDTO.getQuestionId());
        if (!questionOpt.isPresent()) {
            logger.error("Question ID {} not found!", answerDTO.getQuestionId());
            return new GameResponse(false, "Question not found.", null, failureCount);
        }
        Question question = questionOpt.get();

        // Check whether the player's answer is correct.
        boolean isCorrect = (question.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());
        String message = "";

        if (gameMode == GameMode.PVP) {
            // In PvP mode, assume the answer is coming from the player whose turn it is.
            int answeringPlayer = currentPlayerTurn;
            if (!isCorrect) {
                failureCount++;
                logger.info("Player {} answered incorrectly. Failures: {}/{}", answeringPlayer, failureCount, MAX_FAILURES);
            } else {
                if (answeringPlayer == 1) {
                    player1Correct++;
                } else {
                    player2Correct++;
                }
                logger.info("Player {} answered correctly.", answeringPlayer);
            }
            // Toggle turn for next round.
            currentPlayerTurn = (answeringPlayer == 1) ? 2 : 1;
            message = (isCorrect
                    ? "Player " + answeringPlayer + " answered correctly! "
                    : "Player " + answeringPlayer + " answered incorrectly! ")
                    + "Next turn: Player " + currentPlayerTurn + ".";
        } else {
            // PVC mode: update global correct counter.
            if (!isCorrect) {
                failureCount++;
                logger.info("Incorrect answer. Failures: {}/{}", failureCount, MAX_FAILURES);
            } else {
                correctAnswers++;
                logger.info("Correct answer! Total correct answers: {}", correctAnswers);
            }
            message = isCorrect
                    ? "Correct answer! Total correct: " + correctAnswers + ". Here is your next question."
                    : "Wrong answer. Total correct: " + correctAnswers + ". Here is your next question.";
        }

        // Check if the game has ended.
        if (failureCount >= MAX_FAILURES) {
            logger.info("Game over! Maximum failures reached.");
            return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
        }

        // Fetch a random question from the selected category.
        Optional<Question> nextQuestionOpt = questionService.getRandomQuestionByCategory(selectedCategory);
        Question nextQuestion = nextQuestionOpt.orElse(null);
        if (nextQuestion != null) {
            // Record the start time for the next question.
            int startTime = (int) (System.currentTimeMillis() / 1000);
            questionStartTimeMap.put(nextQuestion.getId(), startTime);
        }

        return new GameResponse(isCorrect, message, nextQuestion, failureCount);
    }

    public void resetGame() {
        failureCount = 0;
        correctAnswers = 0;
        player1Correct = 0;
        player2Correct = 0;
        selectedCategory = null;
        questionStartTimeMap.clear();
        logger.info("Game state reset.");
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getCorrectAnswers() {
        // For PVC mode, return the global correct count.
        // In PvP mode, you might want to return a combined score or separate scores.
        return gameMode == GameMode.PVC ? correctAnswers : (player1Correct + player2Correct);
    }

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
