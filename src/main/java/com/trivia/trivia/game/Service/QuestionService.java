package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
    private static final int MAX_FAILURES = 3;

    private final QuestionRepository questionRepository;
    private final Random random = new Random();
    private int failureCount = 0;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> getQuestionById(Integer id) {
        return questionRepository.findById(id);
    }

    public Optional<Question> getRandomQuestion() {
        List<Question> allQuestions = questionRepository.findAll();
        return allQuestions.isEmpty() ? Optional.empty() : Optional.of(allQuestions.get(random.nextInt(allQuestions.size())));
    }

    public Optional<Question> getRandomQuestionByCategory(Category category) {
        List<Question> questions = questionRepository.findByCategory(category);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(random.nextInt(questions.size())));
    }
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    public List<Question> getQuestionsByCategory(Category category) {
        return questionRepository.findByCategory(category);
    }


    public void resetGame() {
        failureCount = 0;
        logger.info("Game reset. Failure count set to 0.");
    }

    public int getFailureCount() {
        return failureCount;
    }
}
