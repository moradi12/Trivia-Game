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
import java.util.stream.Collectors;

@Component
public class QuestionLoader {

    private static final int EXPECTED_OPTIONS_COUNT = 4;
    private static final List<String> CATEGORIES = Arrays.asList(
            "science_questions.json",
            "sport_questions.json",
            "history_questions.json",
            "entertainment_questions.json",
            "countries_questions.json",
            "capital_cities_question.json"
    );

    private final QuestionRepository questionRepository;
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();

    public QuestionLoader(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        for (String fileName : CATEGORIES) {
            loadQuestionsFromJson(mapper, fileName);
        }
    }

    private void loadQuestionsFromJson(ObjectMapper mapper, String fileName) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("QuestionsFiles/" + fileName);
            if (inputStream == null) {
                System.err.println("File not found: " + fileName);
                return;
            }

            List<Question> loadedQuestions = mapper.readValue(
                    inputStream,
                    new TypeReference<List<Question>>() {}
            );

            if (loadedQuestions.isEmpty()) {
                System.err.println("Warning: " + fileName + " is empty.");
                return;
            }

            // Validate
            for (Question question : loadedQuestions) {
                validateQuestion(question);
            }

            questionRepository.saveAll(loadedQuestions);

            Collections.shuffle(loadedQuestions);
            questions.addAll(loadedQuestions);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load questions from " + fileName, e);
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
        return Optional.of(questions.get(random.nextInt(questions.size())));
    }

    public Optional<Question> getRandomQuestionByDifficulty(Difficulty difficulty) {
        List<Question> filtered = questions.stream()
                .filter(q -> q.getDifficulty() == difficulty)
                .collect(Collectors.toList());
        if (filtered.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(filtered.get(random.nextInt(filtered.size())));
    }
}