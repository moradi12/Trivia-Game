package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final SportQuestionRepository sportRepository;
    private final CountriesQuestionRepository countriesRepository;
    private final PoliticsQuestionRepository politicsRepository;
    private final FlagsQuestionRepository flagsRepository;
    private final EntertainmentQuestionRepository entertainmentRepository;
    private final HistoryQuestionRepository historyRepository;
    private final ScienceQuestionRepository scienceRepository;

    // Simple game state (for demo purposes only)
    private int failureCount = 0;
    private static final int MAX_FAILURES = 3;

    @Autowired
    public QuestionService(SportQuestionRepository sportRepository,
                           CountriesQuestionRepository countriesRepository,
                           PoliticsQuestionRepository politicsRepository,
                           FlagsQuestionRepository flagsRepository,
                           EntertainmentQuestionRepository entertainmentRepository,
                           HistoryQuestionRepository historyRepository,
                           ScienceQuestionRepository scienceRepository) {
        this.sportRepository = sportRepository;
        this.countriesRepository = countriesRepository;
        this.politicsRepository = politicsRepository;
        this.flagsRepository = flagsRepository;
        this.entertainmentRepository = entertainmentRepository;
        this.historyRepository = historyRepository;
        this.scienceRepository = scienceRepository;
    }

    /**
     * Returns a random question from the specified category.
     */
    public Optional<Question> getRandomQuestionByCategory(Category category) {
        switch (category) {
            case SPORT:
                return sportRepository.getRandomQuestion();
            case COUNTRIES:
                return countriesRepository.getRandomQuestion();
            case POLITICS:
                return politicsRepository.getRandomQuestion();
            case FLAGS:
                return flagsRepository.getRandomQuestion();
            case ENTERTAINMENT:
                return entertainmentRepository.getRandomQuestion();
            case HISTORY:
                return historyRepository.getRandomQuestion();
            case SCIENCE:
                return scienceRepository.getRandomQuestion();
            default:
                return Optional.empty();
        }
    }

    /**
     * Retrieves a question by its ID by searching through all category repositories.
     */
    public Optional<Question> getQuestionById(Integer id) {
        Optional<Question> question = sportRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = countriesRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = politicsRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = flagsRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = entertainmentRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = historyRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        if (question.isPresent()) return question;
        question = scienceRepository.getAllQuestions().stream()
                .filter(q -> q.getId().equals(id)).findFirst();
        return question;
    }

    /**
     * Processes the player's answer.
     * If the answer is correct, returns the next random question from the same category.
     * If the answer is incorrect, increments the failure count.
     * The game ends if failureCount reaches MAX_FAILURES.
     */
    public Optional<Question> processAnswer(Question question, int selectedAnswerIndex) {
        if (question.getCorrectIndex() == selectedAnswerIndex) {
            // Correct answer: do not increment failure count, return next question.
            return getRandomQuestionByCategory(question.getCategory());
        } else {
            // Incorrect answer: increment failure count.
            failureCount++;
            if (failureCount >= MAX_FAILURES) {
                // Game over – return empty.
                return Optional.empty();
            } else {
                return getRandomQuestionByCategory(question.getCategory());
            }
        }
    }

    /**
     * Resets the game state.
     */
    public void resetGame() {
        failureCount = 0;
    }

    /**
     * Returns the current number of failures.
     */
    public int getFailureCount() {
        return failureCount;
    }
}
