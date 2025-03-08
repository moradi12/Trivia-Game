package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.GameLogic.GameSession;
import com.trivia.trivia.game.Service.GameService;
import com.trivia.trivia.game.Service.GameSessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin
public class GameController {

    private final GameSessionService gameSessionService;
    private final GameService gameService;

    public GameController(GameSessionService gameSessionService, GameService gameService) {
        this.gameSessionService = gameSessionService;
        this.gameService = gameService;
    }

    /**
     * Starts a new game session, returns sessionId + first question.
     */
    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(
            @RequestParam Category category,
            @RequestParam String player1Name,
            @RequestParam(required = false) String player2Name) {

        if (player1Name == null || player1Name.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GameResponse(false, "Player 1 name cannot be empty.", null, 0));
        }

        // 1) Create a new session
        // FIX: Do not specify the type again. Just pass "category"
        String sessionId = gameSessionService.createSession(category, player1Name, player2Name);

        // 2) Retrieve the session object
        GameSession session = gameSessionService.getSession(sessionId);

        // 3) Serve the first question
        QuestionDTO firstQuestion = gameService.serveNextQuestion(session);
        if (firstQuestion == null) {
            // no questions found
            return ResponseEntity.ok(
                    new GameResponse(
                            false,
                            "No questions available for category " + category,
                            null,
                            session.getFailureCount(),
                            sessionId  // optional: we can include sessionId even if no Q
                    )
            );
        }

        // Return success + first question + sessionId
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
     * Submit an answer for the current question.
     * Must provide 'sessionId' in the request so we know which session to update.
     */
    @PostMapping("/answer")
    public ResponseEntity<GameResponse> submitAnswer(
            @RequestParam String sessionId,
            @RequestBody AnswerDTO answerDTO) {

        // 1) Get the session
        GameSession session = gameSessionService.getSession(sessionId);

        // 2) Process the answer
        GameResponse response = gameService.processAnswer(session, answerDTO);

        // 3) Optionally attach sessionId in the response
        response.setSessionId(sessionId);

        return ResponseEntity.ok(response);
    }

    /**
     * (Optional) Endpoint to fetch the current game state (scores, failures, etc.).
     */
    @GetMapping("/state")
    public ResponseEntity<String> getState(@RequestParam String sessionId) {
        GameSession session = gameSessionService.getSession(sessionId);

        // Example: single-player summary only
        String summary = "Category: " + session.getSelectedCategory()
                + ", Failures: " + session.getFailureCount() + "/" + session.getMaxFailures()
                + ", Correct: " + session.getCorrectCount();

        return ResponseEntity.ok(summary);
    }
}
