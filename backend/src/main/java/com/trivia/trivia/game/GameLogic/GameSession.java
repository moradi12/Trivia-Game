package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.Entity.GameMode;
import com.trivia.trivia.game.Entity.Player;
import com.trivia.trivia.game.Entity.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GameSession {
    private GameMode mode;
    private Player player1;
    private Player player2;
    private int currentPlayerTurn;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int correctCount;
    private int failureCount;
    private int maxFailures;
    private long questionStartTime;
    private int player1Score;
    private int player2Score;

    public GameSession(GameMode mode, String player1Name, String player2Name, List<Question> questions, int maxFailures) {
        this.mode = mode;
        this.player1 = new Player(0, player1Name, 0);  // Initialize Player 1
        this.player2 = (player2Name != null && !player2Name.trim().isEmpty()) ? new Player(0, player2Name, 0) : null;
        this.questions = questions;
        this.maxFailures = maxFailures;
        this.currentPlayerTurn = 1;
        this.currentQuestionIndex = 0;
        this.correctCount = 0;
        this.failureCount = 0;
        this.questionStartTime = System.currentTimeMillis();
        this.player1Score = 0;
        this.player2Score = 0;
    }

    public Question getCurrentQuestion() {
        if (questions != null && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public Question getNextQuestion() {
        currentQuestionIndex++;
        if (questions != null && currentQuestionIndex < questions.size()) {
            questionStartTime = System.currentTimeMillis(); // Reset timer
            return questions.get(currentQuestionIndex);
        }
        return null; // No more questions
    }
}
