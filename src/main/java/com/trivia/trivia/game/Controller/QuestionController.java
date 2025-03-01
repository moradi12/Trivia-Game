package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * מקבל קטגוריה (למשל SPORT, COUNTRIES וכו') ומחזיר שאלה אקראית מאותה קטגוריה.
     */
    @GetMapping("/{category}")
    public Optional<Question> getRandomQuestionByCategory(@PathVariable("category") Category category) {
        return questionService.getRandomQuestionByCategory(category);
    }
}
