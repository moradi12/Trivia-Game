package com.trivia.trivia.game.DTO;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import java.util.List;

public class QuestionDTO {
    private Integer id;
    private String text;
    private List<String> options;
    private int correctIndex;
    private Category category;
    private Difficulty difficulty;

    // No-args constructor
    public QuestionDTO() {}

    // All-args constructor
    public QuestionDTO(Integer id, String text, List<String> options, int correctIndex, Category category, Difficulty difficulty) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}