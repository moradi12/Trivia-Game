package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.Entity.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PvPGameRules implements GameRules {

    private static final Logger logger = LoggerFactory.getLogger(PvPGameRules.class);

    @Override
    public void processAnswer(GameSession session, AnswerDTO answerDTO) {
        // Retrieve the current question from the session.
        Question currentQuestion = session.getCurrentQuestion();
        if (currentQuestion == null) {
            logger.warn("No current question available in session.");
            return;
        }

        int currentTurn = session.getCurrentPlayerTurn();

        boolean isCorrect = (currentQuestion.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());
        if (isCorrect) {
            if (currentTurn == 1) {
                session.setPlayer1Score(session.getPlayer1Score() + 1);
                logger.info("Player 1 answered correctly. New score: {}", session.getPlayer1Score());
            } else {
                session.setPlayer2Score(session.getPlayer2Score() + 1);
                logger.info("Player 2 answered correctly. New score: {}", session.getPlayer2Score());
            }
        } else {
            session.setFailureCount(session.getFailureCount() + 1);
            logger.info("Player {} answered incorrectly. Failure count: {}", currentTurn, session.getFailureCount());
        }

        int nextTurn = (currentTurn == 1) ? 2 : 1;
        session.setCurrentPlayerTurn(nextTurn);
        logger.info("Next turn set to Player {}", nextTurn);

        session.getNextQuestion();  // This should update the current question index and reset any timing if needed.
        logger.info("Advanced to next question. Current question index: {}", session.getCurrentQuestionIndex());
    }

    @Override
    public boolean isGameOver(GameSession session) {
        boolean gameOver = session.getFailureCount() >= session.getMaxFailures();
        if (gameOver) {
            logger.info("Game over: failure count {} has reached the maximum of {}.",
                    session.getFailureCount(), session.getMaxFailures());
        }
        return gameOver;
    }
}
