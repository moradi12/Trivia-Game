package com.trivia.trivia.game.Repo;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class QuestionRepository {

    private final SportQuestionRepository sportQuestionRepository;
    private final CountriesQuestionRepository countriesQuestionRepository;
    private final PoliticsQuestionRepository politicsQuestionRepository;
    private final FlagsQuestionRepository flagsQuestionRepository;
    private final EntertainmentQuestionRepository entertainmentQuestionRepository;
    private final HistoryQuestionRepository historyQuestionRepository;
    private final ScienceQuestionRepository scienceQuestionRepository;

    @Autowired
    public QuestionRepository(SportQuestionRepository sportQuestionRepository,
                              CountriesQuestionRepository countriesQuestionRepository,
                              PoliticsQuestionRepository politicsQuestionRepository,
                              FlagsQuestionRepository flagsQuestionRepository,
                              EntertainmentQuestionRepository entertainmentQuestionRepository,
                              HistoryQuestionRepository historyQuestionRepository,
                              ScienceQuestionRepository scienceQuestionRepository) {
        this.sportQuestionRepository = sportQuestionRepository;
        this.countriesQuestionRepository = countriesQuestionRepository;
        this.politicsQuestionRepository = politicsQuestionRepository;
        this.flagsQuestionRepository = flagsQuestionRepository;
        this.entertainmentQuestionRepository = entertainmentQuestionRepository;
        this.historyQuestionRepository = historyQuestionRepository;
        this.scienceQuestionRepository = scienceQuestionRepository;
    }

    /**
     * מחזירה את כל השאלות מכל הקטגוריות כרשימה בלתי ניתנת לשינוי.
     */
    public List<Question> getAllQuestions() {
        List<Question> allQuestions = new ArrayList<>();
        allQuestions.addAll(sportQuestionRepository.getAllQuestions());
        allQuestions.addAll(countriesQuestionRepository.getAllQuestions());
        allQuestions.addAll(politicsQuestionRepository.getAllQuestions());
        allQuestions.addAll(flagsQuestionRepository.getAllQuestions());
        allQuestions.addAll(entertainmentQuestionRepository.getAllQuestions());
        allQuestions.addAll(historyQuestionRepository.getAllQuestions());
        allQuestions.addAll(scienceQuestionRepository.getAllQuestions());
        return Collections.unmodifiableList(allQuestions);
    }

    /**
     * מחזירה רשימת שאלות עבור קטגוריה ספציפית.
     *
     * @param category הקטגוריה המבוקשת
     * @return רשימת השאלות של אותה קטגוריה, או רשימה ריקה אם אין שאלות.
     */
    public List<Question> getQuestionsByCategory(Category category) {
        switch (category) {
            case SPORT:
                return sportQuestionRepository.getAllQuestions();
            case COUNTRIES:
                return countriesQuestionRepository.getAllQuestions();
            case POLITICS:
                return politicsQuestionRepository.getAllQuestions();
            case FLAGS:
                return flagsQuestionRepository.getAllQuestions();
            case ENTERTAINMENT:
                return entertainmentQuestionRepository.getAllQuestions();
            case HISTORY:
                return historyQuestionRepository.getAllQuestions();
            case SCIENCE:
                return scienceQuestionRepository.getAllQuestions();
            default:
                return Collections.emptyList();
        }
    }

    /**
     * מחזירה שאלה אקראית מכלל השאלות.
     *
     * @return Optional המכיל שאלה אקראית, או Optional.empty() אם אין שאלות.
     */
    public Optional<Question> getRandomQuestion() {
        List<Question> allQuestions = getAllQuestions();
        if (allQuestions.isEmpty()) {
            return Optional.empty();
        }
        int index = (int) (Math.random() * allQuestions.size());
        return Optional.of(allQuestions.get(index));
    }

    /**
     * מחזירה שאלה אקראית עבור קטגוריה מסוימת.
     * כך מובטח שהמשתמש יקבל שאלות רק מהקטגוריה שבחר.
     *
     * @param category הקטגוריה המבוקשת
     * @return Optional המכיל שאלה אקראית מהקטגוריה, או Optional.empty() אם אין שאלות.
     */
    public Optional<Question> getRandomQuestionByCategory(Category category) {
        switch (category) {
            case SPORT:
                return sportQuestionRepository.getRandomQuestion();
            case COUNTRIES:
                return countriesQuestionRepository.getRandomQuestion();
            case POLITICS:
                return politicsQuestionRepository.getRandomQuestion();
            case FLAGS:
                return flagsQuestionRepository.getRandomQuestion();
            case ENTERTAINMENT:
                return entertainmentQuestionRepository.getRandomQuestion();
            case HISTORY:
                return historyQuestionRepository.getRandomQuestion();
            case SCIENCE:
                return scienceQuestionRepository.getRandomQuestion();
            default:
                return Optional.empty();
        }
    }
}
