package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Question;
import lombok.Data;

@Data
public class GameResponse {
    private boolean correct;         // true if the submitted answer was correct
    private String message;          // feedback message for the player
    private Question nextQuestion;   // the next question from the same category (if available)
    private int failureCount;        // current number of failures

    public GameResponse(boolean correct, String message, Question nextQuestion, int failureCount) {
        this.correct = correct;
        this.message = message;
        this.nextQuestion = nextQuestion;
        this.failureCount = failureCount;
    }
}
