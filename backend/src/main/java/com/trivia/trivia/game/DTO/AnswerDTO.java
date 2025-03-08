package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Category;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AnswerDTO {
    private int questionId;
    private int selectedAnswerIndex;
    private Category category;
    private String playerId;
    public AnswerDTO() { }

    public AnswerDTO(int questionId, int selectedAnswerIndex, Category category, String playerId) {
        this.questionId = questionId;
        this.selectedAnswerIndex = selectedAnswerIndex;
        this.category = category;
        this.playerId = playerId;
    }
}
