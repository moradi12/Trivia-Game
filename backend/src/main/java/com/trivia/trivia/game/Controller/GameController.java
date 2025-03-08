package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.GameLogic.GameSession;
import com.trivia.trivia.game.Service.GameService;
import com.trivia.trivia.game.Service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    private final GameSessionService gameSessionService;
    private final GameService gameService;

    public GameController(GameSessionService gameSessionService, GameService gameService) {
        this.gameSessionService = gameSessionService;
        this.gameService = gameService;
    }

    /**
     * Starts a new game session and returns the sessionId along with the first question.
     * For PvP mode, an optional roomPassword can be provided to secure the room.
     *
     * @param category     the category for the game
     * @param player1Name  name of player 1 (required)
     * @param player2Name  name of player 2 (if provided, a PvP session is created)
     * @param roomPassword (optional) password to secure the PvP room
     * @return GameResponse containing session details and the first question
     */
    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(
            @RequestParam Category category,
            @RequestParam String player1Name,
            @RequestParam(required = false) String player2Name,
            @RequestParam(required = false) String roomPassword) {

        if (player1Name == null || player1Name.trim().isEmpty()) {
            logger.warn("Player 1 name is missing or empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GameResponse(false, "Player 1 name cannot be empty.", null, 0));
        }

        String sessionId = gameSessionService.createSession(category, player1Name, player2Name, roomPassword);
        GameSession session = gameSessionService.getSession(sessionId);
        logger.info("Game session {} started. Category: {}, Player1: {}, Player2: {}",
                sessionId, category, player1Name, player2Name);

        QuestionDTO firstQuestion = gameService.serveNextQuestion(session);
        if (firstQuestion == null) {
            return ResponseEntity.ok(new GameResponse(
                    false,
                    "No questions available for category " + category,
                    null,
                    session.getFailureCount(),
                    sessionId
            ));
        }

        GameResponse response = new GameResponse(
                true,
                "Game started successfully!",
                firstQuestion,
                session.getFailureCount(),
                sessionId
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Allows a second player to join an existing game session using the gameId and their name.
     * If the session is secured with a password, the correct roomPassword must be provided.
     *
     * @param gameId       the ID of the game session to join
     * @param player2Name  name of player 2
     * @param roomPassword (optional) password required to join the room if set
     * @return GameResponse confirming the join or detailing an error.
     */
    @PostMapping("/join")
    public ResponseEntity<GameResponse> joinGame(
            @RequestParam String gameId,
            @RequestParam String player2Name,
            @RequestParam(required = false) String roomPassword) {

        try {
            GameSession session = gameSessionService.getSession(gameId);

            // Validate room password if required.
            if (session.getRoomPassword() != null && !session.getRoomPassword().isEmpty()) {
                if (roomPassword == null || !session.getRoomPassword().equals(roomPassword)) {
                    logger.warn("Invalid room password provided for session {}", gameId);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new GameResponse(
                                    false,
                                    "Invalid room password.",
                                    null,
                                    0,
                                    gameId
                            ));
                }
            }

            session.joinGame(player2Name);
            logger.info("Player '{}' joined session {}", player2Name, gameId);

            QuestionDTO currentQuestion = gameService.serveNextQuestion(session);
            GameResponse response = new GameResponse(
                    true,
                    "Player 2 joined successfully!",
                    currentQuestion,
                    session.getFailureCount(),
                    gameId
            );
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            logger.error("Join failed for session {}: {}", gameId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GameResponse(
                            false,
                            "Join failed: " + e.getMessage(),
                            null,
                            0,
                            gameId
                    ));
        }
    }

    /**
     * Submit an answer for the current question.
     * The sessionId parameter identifies which session to update.
     *
     * @param sessionId the current game session's ID
     * @param answerDTO the answer details
     * @return GameResponse with the result and the next question (if available)
     */
    @PostMapping("/answer")
    public ResponseEntity<GameResponse> submitAnswer(
            @RequestParam String sessionId,
            @RequestBody AnswerDTO answerDTO) {

        GameSession session = gameSessionService.getSession(sessionId);
        GameResponse response = gameService.processAnswer(session, answerDTO);
        response.setSessionId(sessionId);
        return ResponseEntity.ok(response);
    }

    /**
     * (Optional) Endpoint to fetch the current game state (scores, failures, etc.).
     *
     * @param sessionId the current game session's ID
     * @return a summary string of the game state
     */
    @GetMapping("/state")
    public ResponseEntity<String> getState(@RequestParam String sessionId) {
        GameSession session = gameSessionService.getSession(sessionId);

        String summary;
        if (session.getPlayer2() != null) { // PvP mode
            summary = "Category: " + session.getSelectedCategory()
                    + ", " + session.getPlayer1().getName() + " Score: " + session.getPlayer1CorrectCount()
                    + ", " + session.getPlayer2().getName() + " Score: " + session.getPlayer2CorrectCount()
                    + ", Failures: " + session.getFailureCount() + "/" + session.getMaxFailures();
        } else { // PVC mode
            summary = "Category: " + session.getSelectedCategory()
                    + ", Failures: " + session.getFailureCount() + "/" + session.getMaxFailures()
                    + ", Correct: " + session.getCorrectCount();
        }
        logger.debug("Game state for session {}: {}", sessionId, summary);
        return ResponseEntity.ok(summary);
    }
}
