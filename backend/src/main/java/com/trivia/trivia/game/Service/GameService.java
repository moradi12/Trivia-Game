package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.DTO.AnswerDTO;
import com.trivia.trivia.game.DTO.GameResponse;
import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.GameLogic.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
    private static final int TIME_LIMIT_SECONDS = 30;

    private final QuestionService questionService;

    public GameService(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Serve the next question for a given game session.
     */
    public QuestionDTO serveNextQuestion(GameSession session) {
        // fetch random question from the selected category
        Optional<Question> nextQuestionOpt =
                questionService.getRandomQuestionByCategory(session.getSelectedCategory());
        if (!nextQuestionOpt.isPresent()) {
            return null;
        }

        Question nextQ = nextQuestionOpt.get();
        session.getQuestionStartTimeMap()
                .put(nextQ.getId(), (int) (System.currentTimeMillis() / 1000));

        logger.info("Serving question ID {} for session {}", nextQ.getId(), session.getSessionName());

        return toQuestionDTO(nextQ);
    }

    /**
     * Process the answer for the current question in a session.
     */
    public GameResponse processAnswer(GameSession session, AnswerDTO answerDTO) {
        // If game is already over
        if (session.getFailureCount() >= session.getMaxFailures()) {
            return new GameResponse(
                    false,
                    "Game is already over!",
                    null,
                    session.getFailureCount()
            );
        }

        // Retrieve question start time
        Integer startTime = session.getQuestionStartTimeMap().remove(answerDTO.getQuestionId());
        if (startTime == null) {
            return new GameResponse(
                    false,
                    "Invalid or repeated question answer attempt.",
                    null,
                    session.getFailureCount()
            );
        }

        // Check time limit
        int elapsed = (int) (System.currentTimeMillis() / 1000) - startTime;
        if (elapsed > TIME_LIMIT_SECONDS) {
            session.setFailureCount(session.getFailureCount() + 1);

            if (session.getFailureCount() >= session.getMaxFailures()) {
                return new GameResponse(
                        false,
                        "Time's up! Game over (max failures).",
                        null,
                        session.getFailureCount()
                );
            }
            return new GameResponse(
                    false,
                    "Time's up! You took too long.",
                    null,
                    session.getFailureCount()
            );
        }

        // Find question in DB
        Optional<Question> questionOpt = questionService.getQuestionById(answerDTO.getQuestionId());
        if (!questionOpt.isPresent()) {
            return new GameResponse(
                    false,
                    "Question not found in DB.",
                    null,
                    session.getFailureCount()
            );
        }

        Question question = questionOpt.get();
        boolean isCorrect =
                (question.getCorrectIndex() == answerDTO.getSelectedAnswerIndex());

        // Single-player scoring logic
        String message;
        if (isCorrect) {
            session.setCorrectCount(session.getCorrectCount() + 1);
            message = "Correct answer! Total correct: " + session.getCorrectCount();
        } else {
            session.setFailureCount(session.getFailureCount() + 1);
            message = "Wrong answer. Total correct: " + session.getCorrectCount();
        }

        // Check if game ended
        if (session.getFailureCount() >= session.getMaxFailures()) {
            return new GameResponse(
                    false,
                    "Game over! Failures: " + session.getFailureCount(),
                    null,
                    session.getFailureCount()
            );
        }

        // Serve next question
        QuestionDTO nextQuestion = serveNextQuestion(session);
        if (nextQuestion == null) {
            return new GameResponse(
                    isCorrect,
                    message + " No more questions available.",
                    null,
                    session.getFailureCount()
            );
        }

        return new GameResponse(
                isCorrect,
                message,
                nextQuestion,
                session.getFailureCount()
        );
    }

    /**
     * Helper method to convert a Question entity to QuestionDTO.
     */
    private QuestionDTO toQuestionDTO(Question q) {
        return new QuestionDTO(
                q.getId(),
                q.getText(),
                q.getOptions(),
                q.getCorrectIndex(),
                q.getCategory(),
                q.getDifficulty()
        );
    }
}
