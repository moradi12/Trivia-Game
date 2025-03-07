package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Category;
import lombok.Data;

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

    public int getQuestionId() {
        return questionId;
    }

    public int getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public Category getCategory() {
        return category;
    }

    // setter methods, if you need them...
}