package com.trivia.trivia.game.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
    @Table(name = "question_options")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class QuestionOption {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "question_id", nullable = false)
        private Question question;

    }

