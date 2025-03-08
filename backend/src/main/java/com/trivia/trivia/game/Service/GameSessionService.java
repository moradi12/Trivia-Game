package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.GameLogic.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameSessionService {

    private static final Logger logger = LoggerFactory.getLogger(GameSessionService.class);

    // Map of gameId -> GameSession
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    /**
     * Create a new GameSession.
     * If player2Name is provided (non-empty), a PvP session is created with an optional roomPassword.
     *
     * @param category    the selected category for the game
     * @param player1Name the name of player 1
     * @param player2Name the name of player 2 (optional for PvP mode)
     * @param roomPassword the password for the room (optional)
     * @return the gameId of the created session
     */
    public String createSession(Category category, String player1Name, String player2Name, String roomPassword) {
        GameSession session;
        if (player2Name != null && !player2Name.trim().isEmpty()) {
            session = new GameSession(player1Name, player2Name, 3, roomPassword);  // 3 is maxFailures
            logger.info("Created PvP session: {} vs {} with gameId {}", player1Name, player2Name, session.getGameId());
        } else {
            session = new GameSession(player1Name, 3);
            // Optionally set a password even in PVC mode if needed
            session.setRoomPassword(roomPassword);
            logger.info("Created PVC session for {} with gameId {}", player1Name, session.getGameId());
        }
        session.setSelectedCategory(category);
        sessions.put(session.getGameId(), session);
        return session.getGameId();
    }

    /**
     * Retrieve an existing session by its gameId.
     *
     * @param gameId the ID of the game session
     * @return the GameSession
     */
    public GameSession getSession(String gameId) {
        GameSession session = sessions.get(gameId);
        if (session == null) {
            logger.error("Session not found for gameId: {}", gameId);
            throw new IllegalStateException("Session not found: " + gameId);
        }
        return session;
    }

    /**
     * Remove a session, e.g. after game over.
     *
     * @param gameId the ID of the game session to remove
     */
    public void removeSession(String gameId) {
        sessions.remove(gameId);
        logger.info("Removed session with gameId: {}", gameId);
    }

    /**
     * List all active sessions.
     *
     * @return a collection of active GameSessions
     */
    public Collection<GameSession> listSessions() {
        return sessions.values();
    }
}
