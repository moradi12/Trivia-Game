package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class HistoryQuestionRepository {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "מי היה המלך הראשון של ישראל?",
                Arrays.asList("שאול", "דוד", "משה", "סולימאן"),
                0,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "באיזה שנה נחתם הסכם שלום בין ישראל למצרים?",
                Arrays.asList("1978", "1979", "1980", "1981"),
                1,
                Category.HISTORY
        ));


        addQuestion(new Question(
                null,
                "באיזו שנה פרצה מלחמת העצמאות של ישראל?",
                Arrays.asList("1946", "1947", "1948", "1950"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזו מלחמה התרחשה ב-1967?",
                Arrays.asList("מלחמת יום הכיפורים", "מלחמת סיני", "מלחמת ששת הימים", "מלחמת לבנון הראשונה"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מי היה ראש ממשלת ישראל בזמן מלחמת יום הכיפורים?",
                Arrays.asList("גולדה מאיר", "דוד בן גוריון", "מנחם בגין", "יצחק רבין"),
                0,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מה היה שם המבצע שבו חיילי צה\"ל שחררו חטופים באנטבה?",
                Arrays.asList("מבצע שלמה", "מבצע אנטבה", "מבצע יונתן", "מבצע שלום הגליל"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזו מלחמה התרחשה ב-1982?",
                Arrays.asList("מלחמת יום הכיפורים", "מלחמת סיני", "מלחמת לבנון הראשונה", "מלחמת המפרץ הראשונה"),
                2,
                Category.HISTORY
        ));

        // 🔹 מלחמות עולם
        addQuestion(new Question(
                null,
                "באיזו שנה החלה מלחמת העולם הראשונה?",
                Arrays.asList("1912", "1914", "1916", "1918"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזה אירוע גרם לפרוץ מלחמת העולם השנייה?",
                Arrays.asList("הפלישה של גרמניה לפולין", "המהפכה הרוסית", "הסכם ורסאי", "התקפת פרל הארבור"),
                0,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה הייתה הראשונה להטיל פצצת אטום במלחמה?",
                Arrays.asList("גרמניה", "ארצות הברית", "רוסיה", "יפן"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מי היה הפיקוד העליון של צבא גרמניה הנאצית?",
                Arrays.asList("אדולף היטלר", "אריך פון מאנשטיין", "היינריך הימלר", "הרמן גרינג"),
                0,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "באיזו שנה הסתיימה מלחמת העולם השנייה?",
                Arrays.asList("1943", "1944", "1945", "1946"),
                2,
                Category.HISTORY
        ));

        // 🔹 אירועים היסטוריים חשובים
        addQuestion(new Question(
                null,
                "באיזו שנה נחתם הצהרת העצמאות של ארצות הברית?",
                Arrays.asList("1765", "1776", "1783", "1791"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מי היה נשיא ארה\"ב בזמן מלחמת האזרחים?",
                Arrays.asList("ג'ורג' וושינגטון", "אברהם לינקולן", "תיאודור רוזוולט", "תומאס ג'פרסון"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזו אימפריה קרסה בשנת 476 לספירה?",
                Arrays.asList("האימפריה הביזנטית", "האימפריה הרומית המערבית", "האימפריה הפרסית", "האימפריה המונגולית"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מה היה האירוע שהתחיל את המהפכה הצרפתית?",
                Arrays.asList("הכרזת העצמאות", "התקפת הבסטיליה", "מלחמת נפוליאון", "הצהרת זכויות האדם והאזרח"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזו מהפכה הפכה את רוסיה למדינה קומוניסטית?",
                Arrays.asList("המהפכה הצרפתית", "המהפכה הבולשביקית", "המהפכה התעשייתית", "מהפכת אוקטובר"),
                3,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "באיזו שנה נחתו בני אדם לראשונה על הירח?",
                Arrays.asList("1965", "1967", "1969", "1971"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזה אירוע סימן את נפילת מסך הברזל באירופה?",
                Arrays.asList("נפילת חומת ברלין", "קריסת ברית המועצות", "סיום המלחמה הקרה", "חתימת הסכם מלחמה"),
                0,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "מי היה המנהיג של ברית המועצות בזמן מלחמת העולם השנייה?",
                Arrays.asList("ולדימיר לנין", "יוסיף סטלין", "ניקיטה חרושצ'וב", "בוריס ילצין"),
                1,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזה נשיא אמריקאי התפטר מתפקידו בעקבות פרשת ווטרגייט?",
                Arrays.asList("ג'ון קנדי", "רונלד רייגן", "ריצ'רד ניקסון", "ג'ימי קרטר"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "באיזו שנה נפלה ברית המועצות?",
                Arrays.asList("1989", "1990", "1991", "1992"),
                2,
                Category.HISTORY
        ));
        addQuestion(new Question(
                null,
                "איזה אירוע הוביל למלחמת המפרץ הראשונה?",
                Arrays.asList("פלישת עיראק לכווית", "מהפכת איראן", "הפלת סדאם חוסיין", "מתקפת ה-11 בספטמבר"),
                0,
                Category.HISTORY
        ));
    }

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

    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) return Optional.empty();
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }
}
