package com.trivia.trivia.game.Controller;

import com.trivia.trivia.game.DTO.QuestionDTO;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalQuestions() {
        logger.debug("Fetching total question count");
        return ResponseEntity.ok(questionService.getTotalQuestionCount());
    }
    @GetMapping("/countByCategory")
    public ResponseEntity<Integer> getQuestionCountByCategory(@RequestParam Category category) {
        if (category == null) {
            logger.warn("Category parameter is missing");
            throw new IllegalArgumentException("Category parameter is required");
        }
        logger.info("Fetching question count for category: {}", category);
        return ResponseEntity.ok(questionService.getQuestionCountByCategory(category));
    }
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable int id) {
        logger.info("Fetching question with ID: {}", id);
        Optional<Question> question = questionService.getQuestionById(id);
        return question.map(q -> ResponseEntity.ok(convertToDTO(q)))
                .orElseGet(() -> {
                    logger.warn("Question with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
    @GetMapping("/random")
    public ResponseEntity<QuestionDTO> getRandomQuestion() {
        Optional<Question> question = questionService.getRandomQuestion();
        return question.map(q -> ResponseEntity.ok(convertToDTO(q)))
                .orElseGet(() -> {
                    logger.warn("No random question found");
                    return ResponseEntity.notFound().build();
                });
    }
    @GetMapping("/randomByCategory")
    public ResponseEntity<QuestionDTO> getRandomQuestionByCategory(@RequestParam Category category) {
        Optional<Question> question = questionService.getRandomQuestionByCategory(category);
        return question.map(q -> ResponseEntity.ok(convertToDTO(q)))
                .orElseGet(() -> {
                    logger.warn("No random question found for category {}", category);
                    return ResponseEntity.notFound().build();
                });
    }
    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<Question>> getQuestionsByDifficulty(@PathVariable Difficulty difficulty) {
        return ResponseEntity.ok(questionService.getQuestionsByDifficulty(difficulty));
    }
    @GetMapping("/categoryCounts")
    public ResponseEntity<Map<Category, Long>> getQuestionCountByCategories() {
        return ResponseEntity.ok(questionService.getQuestionCountByCategories());
    }
    @PostMapping("/add")
    public ResponseEntity<QuestionDTO> addQuestion(@RequestBody QuestionDTO questionDTO) {
        logger.info("Adding new question: {}", questionDTO);
        if (questionDTO.getText() == null || questionDTO.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty");
        }
        Question question = convertToEntity(questionDTO);
        Question savedQuestion = questionService.addQuestion(question);
        return ResponseEntity.ok(convertToDTO(savedQuestion));
    }

    // ✅ 🔟 Delete a question by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestionById(@PathVariable int id) {
        logger.info("Deleting question with ID: {}", id);
        boolean deleted = questionService.deleteQuestionById(id);
        if (!deleted) {
            logger.warn("Question with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Question deleted successfully.");
    }

    // ✅ 1️⃣1️⃣ Delete a question by text
    @DeleteMapping("/deleteByText")
    public ResponseEntity<String> deleteQuestionByText(@RequestParam String text) {
        logger.info("Deleting question with text: {}", text);
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be empty");
        }
        boolean deleted = questionService.deleteQuestionByText(text);
        if (!deleted) {
            logger.warn("Question with text '{}' not found", text);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Question deleted successfully.");
    }
    @PostMapping("/clear")
    public ResponseEntity<String> clearAllQuestions() {
        questionService.clearAllQuestions();
        return ResponseEntity.ok("All questions deleted.");
    }

    @GetMapping("/findSimilar")
    public ResponseEntity<List<Question>> findSimilarQuestions(@RequestParam String text) {
        return ResponseEntity.ok(questionService.findSimilarQuestions(text));
    }

    private QuestionDTO convertToDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getText(),
                question.getOptions(),
                question.getCorrectIndex(),
                question.getCategory(),
                question.getDifficulty()
        );
    }

    private Question convertToEntity(QuestionDTO questionDTO) {
        return new Question(
                questionDTO.getId(),
                questionDTO.getText(),
                questionDTO.getOptions(),
                questionDTO.getCorrectIndex(),
                questionDTO.getCategory(),
                questionDTO.getDifficulty()
        );
    }
}
