package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.DTO.QuestionDTO;
import lombok.Data; // optional, if using Lombok

@Data  // optional: auto-generate getters/setters
public class GameResponse {
    private boolean correct;
    private String message;
    private QuestionDTO question;
    private int failures;
    private String sessionId; // <- add this field

    // Existing 4-arg constructor:
    public GameResponse(boolean correct, String message, QuestionDTO question, int failures) {
        this.correct = correct;
        this.message = message;
        this.question = question;
        this.failures = failures;
    }

    // New 5-arg constructor
    public GameResponse(boolean correct, String message, QuestionDTO question, int failures, String sessionId) {
        this.correct = correct;
        this.message = message;
        this.question = question;
        this.failures = failures;
        this.sessionId = sessionId;
    }
}
