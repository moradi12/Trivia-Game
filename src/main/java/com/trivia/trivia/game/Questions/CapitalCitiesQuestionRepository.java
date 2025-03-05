package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CapitalCitiesQuestionRepository {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private int questionIdCounter = 1;

    @PostConstruct
    public void init() {
        // Add each question. For simplicity, we mark them all as EASY difficulty.
        // If your Question entity constructor has the signature:
        //   Question(Integer id, String text, List<String> options, int correctIndex, Category category, Difficulty difficulty)
        // be sure to pass the Difficulty as shown:

        addQuestion(new Question(
                null,
                "מהי עיר הבירה של צרפת?",
                Arrays.asList("פריז", "מרסיי", "ליון", "בורדו"),
                0,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של קנדה?",
                Arrays.asList("טורונטו", "מונטריאול", "אוטווה", "ונקובר"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של יפן?",
                Arrays.asList("אוסקה", "קיוטו", "טוקיו", "הירושימה"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של אוסטרליה?",
                Arrays.asList("סידני", "מלבורן", "קנברה", "בריסביין"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של ברזיל?",
                Arrays.asList("ריו דה ז'ניירו", "ברזיליה", "סאו פאולו", "בלו הוריזונטה"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של דרום אפריקה?",
                Arrays.asList("יוהנסבורג", "דרבן", "קייפטאון", "פרטוריה"),
                3,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של מצרים?",
                Arrays.asList("קהיר", "אלכסנדריה", "גיזה", "אסואן"),
                0,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של גרמניה?",
                Arrays.asList("ברלין", "המבורג", "פרנקפורט", "מינכן"),
                0,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של איטליה?",
                Arrays.asList("מילאנו", "רומא", "ונציה", "נאפולי"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של הודו?",
                Arrays.asList("מומבאי", "ניו דלהי", "בנגלור", "צ'נאי"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של ארצות הברית?",
                Arrays.asList("ניו יורק", "לוס אנג'לס", "וושינגטון די.סי", "שיקגו"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של רוסיה?",
                Arrays.asList("סנט פטרסבורג", "מוסקבה", "יקטרינבורג", "נובוסיבירסק"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של ספרד?",
                Arrays.asList("ברצלונה", "מדריד", "ולנסיה", "סביליה"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של סין?",
                Arrays.asList("שנגחאי", "הונג קונג", "בייג'ינג", "שנזן"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של טורקיה?",
                Arrays.asList("איסטנבול", "אנקרה", "איזמיר", "בורסה"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של פורטוגל?",
                Arrays.asList("פורטו", "ליסבון", "בראגה", "פונשל"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של ארגנטינה?",
                Arrays.asList("קורדובה", "רוסריו", "בואנוס איירס", "מנדוסה"),
                2,
                Category.GEOGRAPHY,
                Difficulty.MEDIUM
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של נורווגיה?",
                Arrays.asList("ברגן", "טרונדהיים", "סטוונגר", "אוסלו"),
                3,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של שוודיה?",
                Arrays.asList("מאלמו", "גטבורג", "שטוקהולם", "ופסטרה"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של שווייץ?",
                Arrays.asList("ציריך", "ברן", "ג'נבה", "בזל"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של פולין?",
                Arrays.asList("קרקוב", "ורשה", "פוזנן", "גדנסק"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של דרום קוריאה?",
                Arrays.asList("בוסאן", "סיאול", "אינצ'ון", "דאגו"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של מקסיקו?",
                Arrays.asList("קנקון", "מקסיקו סיטי", "גואדלחרה", "מונטריי"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של תאילנד?",
                Arrays.asList("צ'יאנג מאי", "פוקט", "בנגקוק", "פטאיה"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של וייטנאם?",
                Arrays.asList("האנוי", "הו צ'י מין סיטי", "דנאנג", "האלונג ביי"),
                0,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של סעודיה?",
                Arrays.asList("מכה", "מדינה", "ריאד", "ג'דה"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של איראן?",
                Arrays.asList("איספהאן", "שיראז", "טהרן", "משהד"),
                2,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של פקיסטן?",
                Arrays.asList("לאהור", "אסלאמאבאד", "קראצ'י", "פישאוור"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של עיראק?",
                Arrays.asList("בגדד", "מוסול", "בצרה", "ארביל"),
                0,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
        addQuestion(new Question(
                null,
                "מהי עיר הבירה של סוריה?",
                Arrays.asList("חאלב", "דמשק", "חומס", "לטקיה"),
                1,
                Category.GEOGRAPHY,
                Difficulty.EASY
        ));
    }

    /**
     * Validates and adds a question to our in-memory list.
     */
    private void addQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != 4) {
            throw new IllegalArgumentException("כל שאלה חייבת להכיל בדיוק 4 אפשרויות");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= 4) {
            throw new IllegalArgumentException("האינדקס של התשובה הנכונה חייב להיות בין 0 ל-3");
        }
        if (question.getId() == null) {
            question.setId(questionIdCounter++);
        }
        questions.add(question);
    }

    /**
     * Returns an unmodifiable list of all capital city questions.
     */
    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Retrieves a random question from the in-memory list.
     */
    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }

    /**
     * Optional: If you want random by difficulty, add something like this:
     */
    public Optional<Question> getRandomQuestionByDifficulty(Difficulty difficulty) {
        List<Question> filtered = new ArrayList<>();
        for (Question q : questions) {
            if (q.getDifficulty() == difficulty) {
                filtered.add(q);
            }
        }
        if (filtered.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(filtered.get(random.nextInt(filtered.size())));
    }
}
