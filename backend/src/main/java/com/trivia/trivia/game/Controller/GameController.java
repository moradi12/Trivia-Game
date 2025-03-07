package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.GameMode;
import com.trivia.trivia.game.GameLogic.GameSession;
import com.trivia.trivia.game.Service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/game")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Starts a new game session and returns the first question.
     */
    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(
            @RequestParam GameMode mode,
            @RequestParam Category category,
            @RequestParam String player1Name,
            @RequestParam(required = false) String player2Name) {

        logger.info("Starting game with mode: {}, category: {}, player1: {}, player2: {}",
                mode, category, player1Name, player2Name);

        if (player1Name == null || player1Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player 1 name cannot be empty.");
        }

        // Create a new GameSession object (if you need it)
        GameSession gameSession = new GameSession(mode, player1Name, player2Name, null, 3);

        // Start the game in the service
        gameService.startGame(mode, category, gameSession.getPlayer1(), gameSession.getPlayer2());

        // Immediately serve the first question
        QuestionDTO firstQuestion = gameService.serveNextQuestion();
        if (firstQuestion == null) {
            // No questions found in the category or DB is empty
            return ResponseEntity.ok(new GameResponse(
                    false, "No questions available.", null, 0)
            );
        }

        // Return success + first question
        return ResponseEntity.ok(new GameResponse(
                true, "Game started successfully.", firstQuestion, 0)
        );
    }

    /**
     * Processes an answer submission and returns the next question (if available).
     */
    @PostMapping("/answer")
    public ResponseEntity<GameResponse> submitAnswer(@RequestBody AnswerDTO answerDTO) {
        if (answerDTO == null) {
            return ResponseEntity.badRequest()
                    .body(new GameResponse(false, "Invalid answer data.", null, 0));
        }

        GameResponse response = gameService.processAnswer(answerDTO);
        return ResponseEntity.ok(response);
    }

    // Optionally, you can add other endpoints here
    // for resetting the game or retrieving current game state if needed.
}
