package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Player;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class GameSession {

    private Player player1;
    private Player player2;

    private Category selectedCategory;

    // Track question start times (questionId -> startTime in seconds)
    private final Map<Integer, Integer> questionStartTimeMap = new ConcurrentHashMap<>();

    // Score / counters
    private int failureCount;
    private int maxFailures;
    private int correctCount;      // used if PVC
    private int player1Score;      // used if PVP
    private int player2Score;      // used if PVP

    // Track which player's turn in PVP
    private int currentPlayerTurn;

    // Optional field for logging or debugging
    private String sessionName;

    /**
     * Main constructor used when starting a new session.
     */
    public GameSession( String player1Name, String player2Name, int maxFailures) {
        this.player1 = new Player(0, player1Name, 0);
        this.player2 = (player2Name != null && !player2Name.isEmpty())
                ? new Player(0, player2Name, 0)
                : null;

        this.maxFailures = maxFailures;
        this.currentPlayerTurn = 1;
        this.sessionName = "Session for " + player1Name
                + (player2 != null ? " vs " + player2.getName() : "");
    }

    /**
     * Default constructor (required by some frameworks).
     */
    public GameSession() {
    }
}
