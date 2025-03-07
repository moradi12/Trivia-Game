package com.trivia.trivia.game.Questions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class CapitalCitiesQuestionRepository {

    private static final int EXPECTED_OPTIONS_COUNT = 4;

    private final QuestionRepository questionRepository;
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public CapitalCitiesQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        try {
            // Load JSON file using ClassLoader
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("QuestionsFiles/capital_cities_question.json");
            if (inputStream == null) {
                throw new RuntimeException("File not found: QuestionsFiles/capital_cities_question.json");
            }

            List<Question> loadedQuestions = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Question>>() {}
            );

            if (loadedQuestions.isEmpty()) {
                System.err.println("Warning: Capital Cities questions JSON file is empty.");
                return;
            }

            // Validate
            for (Question question : loadedQuestions) {
                validateQuestion(question);
            }

            // Persist to MySQL
            questionRepository.saveAll(loadedQuestions);

            // Store in-memory for quick retrieval
            Collections.shuffle(loadedQuestions);
            questions.addAll(loadedQuestions);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load capital cities questions from JSON file", e);
        }
    }

    private void validateQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != EXPECTED_OPTIONS_COUNT) {
            throw new IllegalArgumentException("Each question must have exactly " + EXPECTED_OPTIONS_COUNT + " options");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= EXPECTED_OPTIONS_COUNT) {
            throw new IllegalArgumentException("The correct answer index must be between 0 and " + (EXPECTED_OPTIONS_COUNT - 1));
        }
    }

    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) return Optional.empty();
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }

    public Optional<Question> getRandomQuestionByDifficulty(Difficulty difficulty) {
        List<Question> filtered = new ArrayList<>();
        for (Question q : questions) {
            if (q.getDifficulty() == difficulty) {
                filtered.add(q);
            }
        }
        if (filtered.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(filtered.get(random.nextInt(filtered.size())));
    }
}
