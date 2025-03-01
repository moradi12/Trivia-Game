package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private static final int MAX_FAILURES = 3;

    private int failureCount = 0;
    private int correctAnswers = 0;
    private Category selectedCategory = null;

    private final QuestionService questionService;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * בחירת קטגוריית משחק - השחקן יקבל רק שאלות מקטגוריה זו
     */
    public void selectCategory(Category category) {
        this.selectedCategory = category;
        this.failureCount = 0;
        this.correctAnswers = 0;
        logger.info("Category selected: {}. Game state reset.", category);
    }

    /**
     * עיבוד תשובת השחקן
     */
    public GameResponse processAnswer(AnswerDTO answerDTO) {
        if (selectedCategory == null) {
            return new GameResponse(false, "Please select a category before playing!", null, failureCount);
        }

        if (failureCount >= MAX_FAILURES) {
            logger.warn("Game over! Player is still attempting to answer.");
            return new GameResponse(false, "Game is already over!", null, failureCount);
        }

        // שליפת השאלה
        Question question = questionService.getQuestionById(answerDTO.getQuestionId()).orElse(null);
        if (question == null) {
            logger.error("Question ID {} not found!", answerDTO.getQuestionId());
            return new GameResponse(false, "Question not found.", null, failureCount);
        }

        // בדיקת התשובה
        boolean isCorrect = (question.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());
        if (!isCorrect) {
            failureCount++;
            logger.info("Incorrect answer. Failures: {}/{}", failureCount, MAX_FAILURES);
        } else {
            correctAnswers++;
            logger.info("Correct answer! Total correct answers: {}", correctAnswers);
        }

        // בדיקה אם המשחק הסתיים
        if (failureCount >= MAX_FAILURES) {
            logger.info("Game over! Maximum failures reached.");
            return new GameResponse(false, "Game over! Maximum failures reached.", null, failureCount);
        }

        // שליפת שאלה אקראית מתוך הקטגוריה שנבחרה
        Optional<Question> nextQuestionOpt = questionService.getRandomQuestionByCategory(selectedCategory);
        Question nextQuestion = nextQuestionOpt.orElse(null);

        String message = isCorrect
                ? "Correct answer! Here is your next question."
                : "Wrong answer. Here is your next question.";

        return new GameResponse(isCorrect, message, nextQuestion, failureCount);
    }

    /**
     * איפוס המשחק
     */
    public void resetGame() {
        failureCount = 0;
        correctAnswers = 0;
        selectedCategory = null;
        logger.info("Game state reset.");
    }

    /**
     * החזרת כמות השגיאות
     */
    public int getFailureCount() {
        return failureCount;
    }

    /**
     * החזרת מספר התשובות הנכונות
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * קבלת מצב המשחק
     */
    public String getGameState() {
        return "Game Progress - Category: " + (selectedCategory != null ? selectedCategory : "Not selected")
                + ", Correct: " + correctAnswers
                + ", Failures: " + failureCount + "/" + MAX_FAILURES;
    }
}
