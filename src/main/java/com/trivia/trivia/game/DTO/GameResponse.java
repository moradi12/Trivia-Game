package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Question;
import lombok.Data;

@Data
public class GameResponse {
    private boolean correct;
    private String message;
    private Question nextQuestion;
    private int failureCount;

    public GameResponse(boolean correct, String message, Question nextQuestion, int failureCount) {
        this.correct = correct;
        this.message = message;
        this.nextQuestion = nextQuestion;
        this.failureCount = failureCount;
    }
}
