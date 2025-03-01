package com.trivia.trivia.game.Repo;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ScienceQuestionRepository {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "מהי היחידה הבסיסית של חומרים?",
                Arrays.asList("אטום", "מולקולה", "תא", "אלקטרון"),
                0,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה מהבאים הוא אלמנט כימי?",
                Arrays.asList("מים", "מלח", "זהב", "סוכר"),
                2,
                Category.SCIENCE
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
