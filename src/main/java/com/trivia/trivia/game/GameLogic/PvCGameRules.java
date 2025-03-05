package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.Entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvCGameRules implements GameRules {

    private static final Logger logger = LoggerFactory.getLogger(PvCGameRules.class);

    @Override
    public void processAnswer(GameSession session, AnswerDTO answerDTO) {
        // Retrieve the current question from the session.
        Question currentQuestion = session.getCurrentQuestion();
        if (currentQuestion == null) {
            logger.warn("No current question available in session.");
            return;
        }

        // Check if the player's answer is correct.
        boolean isCorrect = (currentQuestion.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());
        if (isCorrect) {
            // Increase the player's correct count.
            session.setCorrectCount(session.getCorrectCount() + 1);
            logger.info("Player answered correctly. Total correct count: {}", session.getCorrectCount());
        } else {
            // Increase the failure count.
            session.setFailureCount(session.getFailureCount() + 1);
            logger.info("Player answered incorrectly. Total failure count: {}", session.getFailureCount());
        }

        // Optionally, store or log additional details about the question result here.

        // Advance to the next question.
        session.getNextQuestion();  // This call advances the question index and updates the start time.

        // Log the new current question if available.
        Question nextQuestion = session.getCurrentQuestion();
        if (nextQuestion != null) {
            logger.info("Next question set. Current question index: {}", session.getCurrentQuestionIndex());
        } else {
            logger.info("No more questions available in the session.");
        }
    }

    @Override
    public boolean isGameOver(GameSession session) {
        // The game is over if the failure count reaches or exceeds the maximum allowed.
        boolean gameOver = session.getFailureCount() >= session.getMaxFailures();
        if (gameOver) {
            logger.info("Game over reached. Failure count: {}, Maximum allowed: {}",
                    session.getFailureCount(), session.getMaxFailures());
        }
        return gameOver;
    }
}
