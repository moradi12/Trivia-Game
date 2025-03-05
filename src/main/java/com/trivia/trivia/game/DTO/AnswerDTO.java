package com.trivia.trivia.game.DTO;
import com.trivia.trivia.game.Entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerDTO {
    private int questionId;
    private int selectedAnswerIndex;
    private Category category;

    public int getQuestionId() {
        return questionId;
    }
    public int getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

}
