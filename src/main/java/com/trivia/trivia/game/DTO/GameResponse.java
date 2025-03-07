package com.trivia.trivia.game.DTO;

public class GameResponse {
    private boolean correct;
    private String message;
    private QuestionDTO nextQuestion;
    private int failureCount;

    // No-args constructor
    public GameResponse() {}

    // Constructor with all fields
    public GameResponse(boolean correct, String message, QuestionDTO nextQuestion, int failureCount) {
        this.correct = correct;
        this.message = message;
        this.nextQuestion = nextQuestion;
        this.failureCount = failureCount;
    }

    // Getters and Setters
    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public QuestionDTO getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(QuestionDTO nextQuestion) {
        this.nextQuestion = nextQuestion;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }
}
