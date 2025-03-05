package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.Entity.Category;
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
    private int correctAnswers = 0;
    private Category selectedCategory = null;

    // Map to track when a question was served (questionId -> start time in seconds)
    private final Map<Integer, Integer> questionStartTimeMap = new ConcurrentHashMap<>();

    private final QuestionService questionService;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public void selectCategory(Category category) {
        this.selectedCategory = category;
        this.failureCount = 0;
        this.correctAnswers = 0;
        questionStartTimeMap.clear();
        logger.info("Category selected: {}. Game state reset.", category);
    }

    public GameResponse processAnswer(AnswerDTO answerDTO) {
        if (selectedCategory == null) {
            return new GameResponse(false, "Please select a category before playing!", null, failureCount);
        }

        if (failureCount >= MAX_FAILURES) {
            logger.warn("Game over! Player is still attempting to answer.");
            return new GameResponse(false, "Game is already over!", null, failureCount);
        }

        // Retrieve the recorded start time (in seconds) for the question.
        Integer questionStartTime = questionStartTimeMap.get(answerDTO.getQuestionId());
        if (questionStartTime != null) {
            // Get current time in seconds.
            int currentTimeSeconds = (int) (System.currentTimeMillis() / 1000);
            int elapsedSeconds = currentTimeSeconds - questionStartTime;
            if (elapsedSeconds > TIME_LIMIT_SECONDS) {
                failureCount++;
                logger.info("Answer timed out ({} seconds elapsed). Failures: {}/{}",
                        elapsedSeconds, failureCount, MAX_FAILURES);
                if (failureCount >= MAX_FAILURES) {
                    logger.info("Game over! Maximum failures reached.");
                    return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
                }
                // Return a response indicating time expired.
                return new GameResponse(false, "Time's up! You did not answer within 30 seconds.", null, failureCount);
            }
        } else {
            logger.error("No start time recorded for question ID {}.", answerDTO.getQuestionId());
            return new GameResponse(false, "Invalid question timing data.", null, failureCount);
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
        if (!isCorrect) {
            failureCount++;
            logger.info("Incorrect answer. Failures: {}/{}", failureCount, MAX_FAILURES);
        } else {
            correctAnswers++;
            logger.info("Correct answer! Total correct answers: {}", correctAnswers);
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
            // Record the start time (in seconds) for the next question.
            int startTime = (int) (System.currentTimeMillis() / 1000);
            questionStartTimeMap.put(nextQuestion.getId(), startTime);
        }

        String message = isCorrect
                ? "Correct answer! Here is your next question."
                : "Wrong answer. Here is your next question.";

        return new GameResponse(isCorrect, message, nextQuestion, failureCount);
    }

    public void resetGame() {
        failureCount = 0;
        correctAnswers = 0;
        selectedCategory = null;
        questionStartTimeMap.clear();
        logger.info("Game state reset.");
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }


    public String getGameState() {
        return "Game Progress - Category: " + (selectedCategory != null ? selectedCategory : "Not selected")
                + ", Correct: " + correctAnswers
                + ", Failures: " + failureCount + "/" + MAX_FAILURES;
    }
}
