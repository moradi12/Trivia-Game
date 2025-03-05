package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.DTO.AnswerDTO;

public interface GameRules {
    void processAnswer(GameSession session, AnswerDTO answerDTO);
    boolean isGameOver(GameSession session);
    // Additional methods to handle game flow
}
