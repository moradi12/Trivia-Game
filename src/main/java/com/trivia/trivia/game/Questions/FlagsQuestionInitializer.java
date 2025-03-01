package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class FlagsQuestionInitializer {

    private final QuestionRepository questionRepository;

    public FlagsQuestionInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

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

    private void addQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != 4) {
            throw new IllegalArgumentException("כל שאלה חייבת להכיל בדיוק 4 אפשרויות");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= 4) {
            throw new IllegalArgumentException("האינדקס של התשובה הנכונה חייב להיות בין 0 ל-3");
        }
        questionRepository.save(question);
    }
}
