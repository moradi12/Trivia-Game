package com.trivia.trivia.game.Repo;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FlagsQuestionRepository {

    // רשימת השאלות עבור קטגוריית דגלים
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    // AtomicInteger להקצאת ID באופן אוטומטי
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "איזה דגל מייצג את מדינת יפן?",
                Arrays.asList("דגל כחול", "דגל אדום", "דגל עם שמש", "דגל לבן עם סמל"),
                3,
                Category.FLAGS
        ));
        addQuestion(new Question(
                null,
                "איזה דגל מייצג את מדינת קנדה?",
                Arrays.asList("דגל עם עלה", "דגל עם קרן", "דגל עם סמל", "דגל עם כוכב"),
                0,
                Category.FLAGS
        ));
    }

    /**
     * מתודה פרטית להוספת שאלה עם בדיקת תקינות.
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
