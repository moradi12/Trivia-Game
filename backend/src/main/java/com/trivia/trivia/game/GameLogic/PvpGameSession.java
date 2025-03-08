package com.trivia.trivia.game.GameLogic;

import com.trivia.trivia.game.Entity.Question;
import java.util.*;

/**
 * Represents a PvP game session that manages players, the current question,
 * their scores, and tracks which players have answered.
 */
public class PvpGameSession {

    private final String sessionId;
    private final List<String> players;
    private Question currentQuestion;
    private final Map<String, Integer> scores;
    private final Set<String> answeredPlayers;

    /**
     * Constructs a new PvP game session.
     *
     * @param sessionId       Unique identifier for the session.
     * @param players         List of player names participating in the session.
     * @param currentQuestion The current question for the session.
     */
    public PvpGameSession(String sessionId, List<String> players, Question currentQuestion) {
        this.sessionId = sessionId;
        this.players = new ArrayList<>(players);
        this.currentQuestion = currentQuestion;
        this.scores = new HashMap<>();
        this.answeredPlayers = new HashSet<>();
        // Initialize each player's score to zero.
        for (String player : players) {
            scores.put(player, 0);
        }
    }

    /**
     * Returns the session's unique identifier.
     *
     * @return the sessionId.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Returns the list of players in the session.
     *
     * @return a list of player names.
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Returns the current question in the session.
     *
     * @return the current question.
     */
    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    /**
     * Sets a new current question for the session and resets the answered players.
     *
     * @param currentQuestion the new question.
     */
    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
        resetAnswers();
    }

    /**
     * Returns the map of player scores.
     *
     * @return a map with player names as keys and their scores as values.
     */
    public Map<String, Integer> getScores() {
        return scores;
    }

    /**
     * Checks if a player has already answered the current question.
     *
     * @param player the player's name.
     * @return true if the player has answered; false otherwise.
     */
    public boolean hasPlayerAnswered(String player) {
        return answeredPlayers.contains(player);
    }

    /**
     * Marks that a player has answered the current question.
     * If the answer is correct, the player's score is incremented.
     *
     * @param player  the player's name.
     * @param correct true if the answer is correct, false otherwise.
     * @throws IllegalArgumentException if the player is not part of this session.
     */
    public void markPlayerAnswered(String player, boolean correct) {
        if (!players.contains(player)) {
            throw new IllegalArgumentException("Player " + player + " is not part of this session.");
        }
        answeredPlayers.add(player);
        if (correct) {
            scores.put(player, scores.getOrDefault(player, 0) + 1);
        }
    }

    /**
     * Resets the answered players list, allowing all players to answer the new question.
     */
    public void resetAnswers() {
        answeredPlayers.clear();
    }
}
