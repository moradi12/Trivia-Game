package com.trivia.trivia.game.Repo;

import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(Category category);
    Optional<Question> findByText(String text);
    boolean existsByText(String text);
    int countByCategory(Category category);
    List<Question> findByDifficulty(Difficulty difficulty);
}
