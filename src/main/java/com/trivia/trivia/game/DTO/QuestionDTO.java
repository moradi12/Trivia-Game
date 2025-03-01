package com.trivia.trivia.game.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Data
@Getter
public class QuestionDTO {
    private int id;
    private String text;
    private List<String> options;

}