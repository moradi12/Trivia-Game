package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.GameMode;
import com.trivia.trivia.game.GameLogic.GameSession;
import com.trivia.trivia.game.Service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startGame(
            @RequestParam GameMode mode,
            @RequestParam Category category,
            @RequestParam String player1Name,
            @RequestParam(required = false) String player2Name) {

        logger.info("Starting game with mode: {}, category: {}, player1: {}, player2: {}", mode, category, player1Name, player2Name);

        if (player1Name == null || player1Name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player 1 name cannot be empty");
        }
        GameSession gameSession = new GameSession(mode, player1Name, player2Name, null, 3);
        gameService.startGame(mode, category, gameSession.getPlayer1(), gameSession.getPlayer2());
        return ResponseEntity.ok("Game started successfully");
    }

    @PostMapping("/answer")
    public ResponseEntity<GameResponse> submitAnswer(@RequestBody AnswerDTO answerDTO) {
        if (answerDTO == null) {
            throw new IllegalArgumentException("Answer cannot be null");
        }

        GameResponse response = gameService.processAnswer(answerDTO);
        return ResponseEntity.ok(response);
    }
}
