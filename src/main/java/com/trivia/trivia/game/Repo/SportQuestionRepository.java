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
public class SportQuestionRepository {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    /**
     * אתחול רשימת השאלות עבור קטגוריית ספורט.
     * אין צורך להזין ידנית את ה-ID – הוא מוקצה אוטומטית.
     */
    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "מי זכה במונדיאל 2018?",
                Arrays.asList("ברזיל", "גרמניה", "צרפת", "ארגנטינה"),
                2,
                Category.SPORT
        ));
        addQuestion(new Question(
                null,
                "איזו קבוצה זכתה בליגת האלופות 2021?",
                Arrays.asList("ברצלונה", "מנצ'סטר סיטי", "באיירן", "צ'לסי"),
                0,
                Category.SPORT
        ));
    }

    /**
     * הוספת שאלה עם בדיקת תקינות.
     * במידה וה-ID של השאלה הוא null, הוא מוקצה אוטומטית.
     */
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

    /**
     * מחזירה את כל השאלות כרשימה בלתי ניתנת לשינוי.
     */
    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * מחזירה שאלה אקראית מהרשימה.
     */
    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }
}
