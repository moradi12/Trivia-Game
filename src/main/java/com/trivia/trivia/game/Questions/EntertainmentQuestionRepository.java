package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EntertainmentQuestionRepository {

    // רשימת השאלות עבור קטגוריית בידור
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    // הקצאת מזהה אוטומטית לשאלות
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "איזה סרט זכה באוסקר בשנת 2020?",
                Arrays.asList("1917", "Parasite", "Joker", "Once Upon a Time in Hollywood"),
                1,
                Category.ENTERTAINMENT
        ));
        addQuestion(new Question(
                null,
                "מי ניצח בתחרות אירוויזיון 2021?",
                Arrays.asList("אוקראינה", "ישראל", "רומניה", "הולנד"),
                0,
                Category.ENTERTAINMENT
        ));
    }

    private void addQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != 4) {
            throw new IllegalArgumentException("כל שאלה חייבת להכיל בדיוק 4 אפשרויות");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= 4) {
            throw new IllegalArgumentException("האינדקס של התשובה הנכונה חייב להיות בין 0 ל-3");
        }
        if (question.getId() == null) {
            question.setId(questionIdCounter.getAndIncrement());
        }
        questions.add(question);
    }

    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) return Optional.empty();
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }
}