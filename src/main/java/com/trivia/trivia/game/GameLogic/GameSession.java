package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.Entity.GameMode;
import com.trivia.trivia.game.Entity.Player;
import com.trivia.trivia.game.Entity.Question;

import java.util.List;

public class GameSession {
    private GameMode mode;
    private Player player1;
    private Player player2; // For PvP mode, can be null in PvC mode.
    private int currentPlayerTurn; // 1 for player1, 2 for player2 (only used in PvP mode)
    private List<Question> questions;
    private int currentQuestionIndex;
    private int correctCount;
    private int failureCount;
    private int maxFailures;
    private long questionStartTime; // Timestamp for when the current question was presented

    private int player1Score;
    private int player2Score;

    // Constructor
    public GameSession(GameMode mode, Player player1, Player player2, List<Question> questions, int maxFailures) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        this.questions = questions;
        this.maxFailures = maxFailures;
        this.currentPlayerTurn = 1; // start with player1
        this.currentQuestionIndex = 0;
        this.correctCount = 0;
        this.failureCount = 0;
        this.questionStartTime = System.currentTimeMillis();
        this.player1Score = 0;
        this.player2Score = 0;
    }

    // Get the current question from the list.
    public Question getCurrentQuestion() {
        if (questions != null && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    // Advances to the next question, resets the start time, and returns it.
    // Returns null if no more questions are available.
    public Question getNextQuestion() {
        currentQuestionIndex++;
        if (questions != null && currentQuestionIndex < questions.size()) {
            questionStartTime = System.currentTimeMillis();
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    // Getters and setters for all fields.
    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public void setCurrentPlayerTurn(int currentPlayerTurn) {
        this.currentPlayerTurn = currentPlayerTurn;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0; // reset index when new list is set
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public int getMaxFailures() {
        return maxFailures;
    }

    public void setMaxFailures(int maxFailures) {
        this.maxFailures = maxFailures;
    }

    public long getQuestionStartTime() {
        return questionStartTime;
    }

    public void setQuestionStartTime(long questionStartTime) {
        this.questionStartTime = questionStartTime;
    }

    // Getters and setters for player scores.
    public int getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }
}
