package com.trivia.trivia.game.Repo;

import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(Category category);
    boolean existsByText(String text);
}
