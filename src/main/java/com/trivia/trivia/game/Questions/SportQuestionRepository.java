package com.trivia.trivia.game.Questions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository; // <-- Import your JPA repository
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SportQuestionRepository {

    private static final int EXPECTED_OPTIONS_COUNT = 4;

    private final QuestionRepository questionRepository;  // JPA repository
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    // Constructor injection
    public SportQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Initializes the questions by loading them from a JSON file and saving them to the DB.
     */
    @PostConstruct
    public void init() {
        try {
            // 1) Load JSON
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("sport_questions.json");
            List<Question> loadedQuestions = mapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Question>>() {}
            );

            // 2) Validate
            for (Question question : loadedQuestions) {
                validateQuestion(question);
            }

            // 3) Persist to MySQL
            //    The DB will generate IDs automatically because of @GeneratedValue in Question
            questionRepository.saveAll(loadedQuestions);

            // 4) (Optional) Keep an in-memory list if you still want random picks
            //    Shuffle for variety
            Collections.shuffle(loadedQuestions);
            questions.addAll(loadedQuestions);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load sport questions from JSON file", e);
        }
    }

    /**
     * Validates that the question has exactly 4 options and that the correct index is valid.
     */
    private void validateQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != EXPECTED_OPTIONS_COUNT) {
            throw new IllegalArgumentException("Each question must have exactly " + EXPECTED_OPTIONS_COUNT + " options");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= EXPECTED_OPTIONS_COUNT) {
            throw new IllegalArgumentException("The correct answer index must be between 0 and " + (EXPECTED_OPTIONS_COUNT - 1));
        }
    }

    /**
     * Returns the total number of questions loaded in memory (not necessarily in DB).
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Returns an unmodifiable list of all in-memory questions.
     */
    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Returns a random question from the in-memory list.
     */
    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(questions.get(random.nextInt(questions.size())));
    }
}
