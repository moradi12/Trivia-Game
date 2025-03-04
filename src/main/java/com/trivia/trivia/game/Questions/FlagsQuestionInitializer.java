package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FlagsQuestionInitializer {

    private final QuestionRepository questionRepository;
    private static final Logger logger = LoggerFactory.getLogger(FlagsQuestionInitializer.class);

    public FlagsQuestionInitializer(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("Starting flags questions initialization.");

        List<Question> questions = Arrays.asList(
                new Question(
                        null,
                        "איזה דגל מייצג את מדינת יפן?",
                        Arrays.asList("דגל כחול", "דגל אדום", "דגל עם שמש", "דגל לבן עם סמל"),
                        3,
                        Category.FLAGS
                ),
                new Question(
                        null,
                        "איזה דגל מייצג את מדינת קנדה?",
                        Arrays.asList("דגל עם עלה", "דגל עם קרן", "דגל עם סמל", "דגל עם כוכב"),
                        0,
                        Category.FLAGS
                )
        );

        for (Question question : questions) {
            addQuestion(question);
        }

        logger.info("Finished flags questions initialization.");
    }

    private void addQuestion(Question question) {
        // Basic validations
        if (question.getOptions() == null || question.getOptions().size() != 4) {
            throw new IllegalArgumentException("כל שאלה חייבת להכיל בדיוק 4 אפשרויות");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= 4) {
            throw new IllegalArgumentException("האינדקס של התשובה הנכונה חייב להיות בין 0 ל-3");
        }

        // Check if a question with the same text already exists to avoid duplicates
        if (questionRepository.existsByText(question.getText())) {
            logger.info("Skipping insertion. Question already exists: {}", question.getText());
        } else {
            logger.info("Inserting new question: {}", question.getText());
            questionRepository.save(question);
        }
    }
}
