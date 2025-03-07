package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuestionDTO {
    private Integer id;
    private String text;
    private List<String> options;
    private int correctIndex;
    private Category category;
    private Difficulty difficulty;


}