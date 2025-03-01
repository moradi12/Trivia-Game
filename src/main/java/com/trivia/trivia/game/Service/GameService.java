package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private static final int MAX_FAILURES = 3;
    private int failureCount = 0;

    private final QuestionService questionService;

    @Autowired
    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Processes the player's answer:
     *  - Looks up the question by ID
     *  - Checks correctness
     *  - Increments failureCount if wrong
     *  - Ends game if MAX_FAILURES reached
     *  - Otherwise fetches and returns next question
     */
    public GameResponse processAnswer(AnswerDTO answerDTO) {
        // 1. Retrieve the question by its ID
        Question question = questionService.getQuestionById(answerDTO.getQuestionId())
                .orElse(null);
        if (question == null) {
            return new GameResponse(false,
                    "Question not found.",
                    null,
                    failureCount);
        }

        // 2. Check if the answer is correct
        boolean isCorrect = (question.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());
        if (!isCorrect) {
            failureCount++;
        }

        // 3. Check if the game is over
        if (failureCount >= MAX_FAILURES) {
            return new GameResponse(false,
                    "Game over! Maximum failures reached.",
                    null,
                    failureCount);
        }

        // 4. If not over, fetch a new question from the same category
        Question nextQuestion = questionService.getRandomQuestionByCategory(question.getCategory())
                .orElse(null);

        String message = isCorrect
                ? "Correct answer! Here is your next question."
                : "Wrong answer. Here is your next question.";

        return new GameResponse(isCorrect,
                message,
                nextQuestion,
                failureCount);
    }

    /**
     * Resets the game state (failure count).
     */
    public void resetGame() {
        failureCount = 0;
    }

    public int getFailureCount() {
        return failureCount;
    }
}
