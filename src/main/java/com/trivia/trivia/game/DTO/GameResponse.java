package com.trivia.trivia.game.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameResponse {
    private boolean correct;
    private String message;
    private QuestionDTO nextQuestion;
    private int failureCount;


}
