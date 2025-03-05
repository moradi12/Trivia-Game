package com.trivia.trivia.game.Service;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    private final QuestionRepository questionRepository;
    private final Random random = new Random();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // 1️⃣ Get total question count
    public int getTotalQuestionCount() {
        return (int) questionRepository.count();
    }

    // 2️⃣ Get question count by category
    public int getQuestionCountByCategory(Category category) {
        return questionRepository.countByCategory(category);
    }

    // 3️⃣ Get a question by ID
    public Optional<Question> getQuestionById(int id) {
        return questionRepository.findById(id);
    }

    // 4️⃣ Get a random question
    public Optional<Question> getRandomQuestion() {
        List<Question> allQuestions = questionRepository.findAll();
        return allQuestions.isEmpty() ? Optional.empty() : Optional.of(allQuestions.get(random.nextInt(allQuestions.size())));
    }

    // 5️⃣ Get multiple random questions
    public List<Question> getRandomQuestions(int count) {
        List<Question> allQuestions = questionRepository.findAll();
        if (allQuestions.size() <= count) {
            return allQuestions; // Return all if fewer than requested
        }
        Collections.shuffle(allQuestions);
        return allQuestions.subList(0, count);
    }

    // 6️⃣ Get random question by category
    public Optional<Question> getRandomQuestionByCategory(Category category) {
        List<Question> questions = questionRepository.findByCategory(category);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(random.nextInt(questions.size())));
    }

    // 7️⃣ Get all questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // 8️⃣ Get paginated questions
    public Page<Question> getQuestionsPaged(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size));
    }

    // 9️⃣ Get questions by category
    public List<Question> getQuestionsByCategory(Category category) {
        return questionRepository.findByCategory(category);
    }

    // 10️⃣ Check if a question exists
    public boolean questionExists(String text) {
        return questionRepository.existsByText(text);
    }

    // 11️⃣ Add a new question (with duplicate check)
    public Question addQuestion(Question question) {
        if (!questionExists(question.getText())) {
            return questionRepository.save(question);
        } else {
            throw new IllegalArgumentException("Question already exists!");
        }
    }

    // 12️⃣ Delete a question by ID
    public boolean deleteQuestionById(int id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 13️⃣ Delete a question by text
    public boolean deleteQuestionByText(String text) {
        Optional<Question> question = questionRepository.findByText(text);
        if (question.isPresent()) {
            questionRepository.delete(question.get());
            return true;
        }
        return false;
    }

    // 14️⃣ Get question count by categories
    public Map<Category, Long> getQuestionCountByCategories() {
        return questionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Question::getCategory, Collectors.counting()));
    }

    // 15️⃣ Find similar questions (using Levenshtein Distance)
    public List<Question> findSimilarQuestions(String text) {
        return questionRepository.findAll().stream()
                .filter(q -> calculateLevenshteinDistance(q.getText(), text) < 5)
                .collect(Collectors.toList());
    }

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(
                            Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                            dp[i - 1][j - 1] + cost);
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    // 16️⃣ Reset game failure count (for game tracking)
    public void resetGame() {
        logger.info("Game reset. Failure count set to 0.");
        // (If needed, add additional reset logic here)
    }

    // 17️⃣ Get random question by difficulty
    public Optional<Question> getRandomQuestionByDifficulty(Difficulty difficulty) {
        List<Question> questions = questionRepository.findByDifficulty(difficulty);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(random.nextInt(questions.size())));
    }

    // 18️⃣ Get questions by difficulty
    public List<Question> getQuestionsByDifficulty(Difficulty difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    // 19️⃣ Clear all questions from the database
    public void clearAllQuestions() {
        questionRepository.deleteAll();
    }
}
