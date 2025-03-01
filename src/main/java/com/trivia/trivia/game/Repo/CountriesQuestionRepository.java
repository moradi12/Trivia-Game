package com.trivia.trivia.game.Repo;


import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountriesQuestionRepository {

    // רשימת השאלות עבור קטגוריית מדינות
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    // AtomicInteger להקצאת מזהה אוטומטית לשאלות
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    /**
     * אתחול רשימת השאלות עבור קטגוריית מדינות.
     * אין צורך להזין ידנית את ה-ID – הוא מוקצה אוטומטית.
     */
    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "איזו מדינה היא הגדולה ביותר בשטח?",
                Arrays.asList("רוסיה", "קנדה", "סין", "ארה\"ב"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה נמצאת במרכז אירופה?",
                Arrays.asList("גרמניה", "אוסטריה", "שוויץ", "הולנד"),
                1,
                Category.COUNTRIES
        ));
    }

    /**
     * מתודה פרטית להוספת שאלה לאחר בדיקת תקינות.
     * בודקת שלכל שאלה יש בדיוק 4 אפשרויות ושאינדקס התשובה הנכונה בטווח.
     * אם ה-ID הוא null, הוא מוקצה אוטומטית.
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
