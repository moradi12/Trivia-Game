package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.GameLogic.GameSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameSessionService {

    // sessionId -> GameSession
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    /**
     * Create a new GameSession and return its sessionId.
     */
    public String createSession( Category category, String player1Name, String player2Name) {
        String sessionId = UUID.randomUUID().toString();
        // Use the four-argument constructor
        GameSession session = new GameSession(
                player1Name,
                player2Name,
                3  // <-- maxFailures
        );
        session.setSelectedCategory(category);
        sessions.put(sessionId, session);
        return sessionId;
    }
    /**
     * Retrieve an existing session by ID, or throw an exception if not found.
     */
    public GameSession getSession(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Session not found: " + sessionId);
        }
        return session;
    }

    /**
     * Delete a session (e.g., after game over).
     */
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
