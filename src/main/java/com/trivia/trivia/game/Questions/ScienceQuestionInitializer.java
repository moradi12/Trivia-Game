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
public class ScienceQuestionInitializer {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "מהי היחידה הבסיסית של חומרים?",
                Arrays.asList("אטום", "מולקולה", "תא", "אלקטרון"),
                0,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה מהבאים הוא אלמנט כימי?",
                Arrays.asList("מים", "מלח", "זהב", "סוכר"),
                2,
                Category.SCIENCE
        ));

        addQuestion(new Question(
                null,
                "איזו פלנטה היא הקרובה ביותר לשמש?",
                Arrays.asList("ונוס", "מאדים", "מרקורי", "צדק"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה גז חיוני לנשימת בני אדם?",
                Arrays.asList("חמצן", "חנקן", "מימן", "פחמן דו-חמצני"),
                0,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "כמה כרומוזומים יש בתא אנושי רגיל?",
                Arrays.asList("23", "32", "46", "64"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהו המרכיב העיקרי של השמש?",
                Arrays.asList("הליום", "מימן", "חמצן", "ברזל"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהי יחידת המידה של זרם חשמלי?",
                Arrays.asList("וואט", "וולט", "אמפר", "אוהם"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מי גילה את כוח הכבידה?",
                Arrays.asList("גלילאו גליליי", "אייזיק ניוטון", "אלברט איינשטיין", "ניקולה טסלה"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהי הצורה הנפוצה ביותר של מים בכדור הארץ?",
                Arrays.asList("קרח", "מים נוזליים", "אדים", "שלג"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "באיזה איבר בגוף האדם מיוצר אינסולין?",
                Arrays.asList("כבד", "לבלב", "לב", "ריאות"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה כוכב לכת מכונה 'כוכב הלכת האדום'?",
                Arrays.asList("צדק", "מאדים", "שבתאי", "נפטון"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה צבע מופיע כאשר אור לבן עובר דרך מנסרה ונשבר?",
                Arrays.asList("כחול", "ירוק", "סגול", "כל צבעי הקשת"),
                3,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהו היסוד הכימי הנפוץ ביותר ביקום?",
                Arrays.asList("חמצן", "פחמן", "מימן", "הליום"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזו בלוטה אחראית על ייצור הורמוני הגדילה?",
                Arrays.asList("בלוטת התריס", "הלבלב", "בלוטת יותרת המוח", "הכבד"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהו שמו של החלקיק הנושא מטען חשמלי שלילי?",
                Arrays.asList("פרוטון", "ניוטרון", "אלקטרון", "יונון"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה סוג גל הוא גל קול?",
                Arrays.asList("אורכי", "רוחבי", "אלקטרומגנטי", "מכני"),
                0,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזו שכבה של האטמוספירה מגנה על כדור הארץ מקרינה אולטרה-סגולה?",
                Arrays.asList("טרופוספירה", "מזוספירה", "סטרטוספירה", "תרמוספירה"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה איבר בגוף האדם הוא הגדול ביותר?",
                Arrays.asList("הלב", "הכבד", "העור", "המוח"),
                2,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהו החומר שמעניק לצמחים את צבעם הירוק?",
                Arrays.asList("חנקן", "כלורופיל", "פחמן דו-חמצני", "אורגנין"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "איזה יסוד מסומן בטבלה המחזורית באות O?",
                Arrays.asList("זהב", "חמצן", "כספית", "אורניום"),
                1,
                Category.SCIENCE
        ));
        addQuestion(new Question(
                null,
                "מהי נקודת הרתיחה של מים בלחץ אטמוספרי רגיל?",
                Arrays.asList("50°C", "75°C", "100°C", "125°C"),
                2,
                Category.SCIENCE
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
