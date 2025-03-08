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
    public AnswerDTO() { }

    public AnswerDTO(int questionId, int selectedAnswerIndex, Category category) {
        this.questionId = questionId;
        this.selectedAnswerIndex = selectedAnswerIndex;
        this.category = category;
    }

}