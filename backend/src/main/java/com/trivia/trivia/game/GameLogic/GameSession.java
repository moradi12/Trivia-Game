package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Player;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class GameSession {

    private final String gameId;
    private Player player1;
    private Player player2;
    private Category selectedCategory;

    // New field for room password (optional for PvP mode)
    private String roomPassword;

    // Track question start times (questionId -> startTime in seconds)
    private final Map<Integer, Integer> questionStartTimeMap = new ConcurrentHashMap<>();

    // Global counters for PVC mode
    private int failureCount;
    private int correctCount;
    private int maxFailures;

    // Separate counters for PvP mode
    private int player1CorrectCount;
    private int player1Failures;
    private int player2CorrectCount;
    private int player2Failures;

    // Track which player's turn in PvP (1 or 2)
    private int currentPlayerTurn;

    // Current round number
    private int roundNumber;

    // Keep track of already asked question IDs to avoid repetition
    private final Set<Integer> askedQuestionIds = new HashSet<>();

    // Optional field for logging or debugging
    private String sessionName;

    // Constructor for single-player (PVC) mode
    public GameSession(String player1Name, int maxFailures) {
        this.gameId = UUID.randomUUID().toString();
        this.player1 = new Player(0, player1Name, 0);
        this.maxFailures = maxFailures;
        this.currentPlayerTurn = 1;
        this.roundNumber = 1;
        this.sessionName = "Session for " + player1Name;
    }

    // Overloaded constructor for PvP mode
    public GameSession(String player1Name, String player2Name, int maxFailures, String roomPassword) {
        this(player1Name, maxFailures);
        this.roomPassword = roomPassword;  // set room password
        joinGame(player2Name);  // Automatically join player 2 if provided
    }

    /**
     * Allows player 2 to join the session using the game ID.
     *
     * @param player2Name the name of the second player
     * @throws IllegalStateException if player2 has already joined
     */
    public void joinGame(String player2Name) {
        if (this.player2 != null) {
            throw new IllegalStateException("Player 2 has already joined this session.");
        }
        this.player2 = new Player(0, player2Name, 0);
        this.sessionName += " vs " + player2Name;
    }

    // Increment methods for tracking scores and failures

    public void incrementPlayer1Correct() {
        player1CorrectCount++;
    }

    public void incrementPlayer2Correct() {
        player2CorrectCount++;
    }

    public void incrementPlayer1Failures() {
        player1Failures++;
    }

    public void incrementPlayer2Failures() {
        player2Failures++;
    }

    public void incrementCorrectCount() {
        correctCount++;
    }

    public void incrementFailureCount() {
        failureCount++;
    }

    // Advances the game to the next round.
    public void nextRound() {
        roundNumber++;
    }

    // Game-over checks for each mode.
    public boolean isGameOverPVC() {
        return failureCount >= maxFailures;
    }

    public boolean isGameOverPvPForPlayer1() {
        return player1Failures >= maxFailures;
    }

    public boolean isGameOverPvPForPlayer2() {
        return player2Failures >= maxFailures;
    }

    // Track asked questions to avoid repetition.
    public void addAskedQuestion(int questionId) {
        askedQuestionIds.add(questionId);
    }

    public boolean hasQuestionBeenAsked(int questionId) {
        return askedQuestionIds.contains(questionId);
    }
}
