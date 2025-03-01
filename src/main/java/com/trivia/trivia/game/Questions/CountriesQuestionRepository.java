package com.trivia.trivia.game.Questions;


import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CountriesQuestionRepository {

    // רשימת השאלות עבור קטגוריית מדינות
    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    // AtomicInteger להקצאת מזהה אוטומטית לשאלות
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    /**
     * אתחול רשימת השאלות עבור קטגוריית מדינות.
     * אין צורך להזין ידנית את ה-ID – הוא מוקצה אוטומטית.
     */
    @PostConstruct
    public void init() {
        addQuestion(new Question(
                null,
                "איזו מדינה היא הגדולה ביותר בשטח?",
                Arrays.asList("רוסיה", "קנדה", "סין", "ארה\"ב"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה נמצאת במרכז אירופה?",
                Arrays.asList("גרמניה", "אוסטריה", "שוויץ", "הולנד"),
                1,
                Category.COUNTRIES
        ));

        addQuestion(new Question(
                null,
                "איזו מדינה היא הגדולה ביותר בשטח?",
                Arrays.asList("רוסיה", "קנדה", "סין", "ארה\"ב"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה ממוקמת ביבשת אפריקה?",
                Arrays.asList("תאילנד", "מצרים", "פורטוגל", "פיליפינים"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה ידועה בתור 'ארץ השמש העולה'?",
                Arrays.asList("סין", "יפן", "דרום קוריאה", "ווייטנאם"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "באיזו מדינה נמצא מגדל אייפל?",
                Arrays.asList("גרמניה", "איטליה", "צרפת", "ספרד"),
                2,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה גובלת עם ארצות הברית מדרום?",
                Arrays.asList("מקסיקו", "קנדה", "קובה", "ברזיל"),
                0,
                Category.COUNTRIES
        ));

        // 🔹 שאלות בינוניות
        addQuestion(new Question(
                null,
                "באיזו מדינה משתמשים במטבע 'ין'?",
                Arrays.asList("סין", "יפן", "דרום קוריאה", "הודו"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה היא המאוכלסת ביותר בעולם נכון ל-2024?",
                Arrays.asList("הודו", "סין", "ארצות הברית", "ברזיל"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה נחשבת לנייטרלית ולא משתתפת במלחמות?",
                Arrays.asList("שוויץ", "שוודיה", "פורטוגל", "אירלנד"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "באיזו מדינה נמצאת הפירמידה הגדולה של גיזה?",
                Arrays.asList("מצרים", "מקסיקו", "פרו", "סעודיה"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה היא היצרנית הגדולה ביותר של קפה בעולם?",
                Arrays.asList("קולומביה", "ברזיל", "ויאטנם", "אתיופיה"),
                1,
                Category.COUNTRIES
        ));

        // 🔹 שאלות קשות
        addQuestion(new Question(
                null,
                "באיזו מדינה נמצא האי הגדול ביותר בעולם?",
                Arrays.asList("אוסטרליה", "גרינלנד", "קנדה", "אינדונזיה"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "כמה מדינות יש בעולם נכון ל-2024?",
                Arrays.asList("190", "195", "200", "210"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה מחזיקה בשטח האי מדגסקר?",
                Arrays.asList("מדגסקר", "צרפת", "בריטניה", "דרום אפריקה"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "באיזו מדינה התקיימה המהפכה התעשייתית?",
                Arrays.asList("ארה\"ב", "גרמניה", "בריטניה", "צרפת"),
                2,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "באיזו מדינה נמצא ההר הגבוה ביותר בעולם?",
                Arrays.asList("נפאל", "סין", "הודו", "טיבט"),
                0,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "באיזו יבשת נמצאת ונצואלה?",
                Arrays.asList("צפון אמריקה", "דרום אמריקה", "אפריקה", "אירופה"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה באירופה היא בעלת הכי הרבה מדינות שכנות?",
                Arrays.asList("גרמניה", "רוסיה", "צרפת", "אוסטריה"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה מייצרת את רוב הזהב בעולם?",
                Arrays.asList("דרום אפריקה", "סין", "רוסיה", "קנדה"),
                1,
                Category.COUNTRIES
        ));
        addQuestion(new Question(
                null,
                "איזו מדינה חולקת את הגבול הארוך ביותר בעולם?",
                Arrays.asList("ארצות הברית - מקסיקו", "רוסיה - סין", "ארצות הברית - קנדה", "הודו - פקיסטן"),
                2,
                Category.COUNTRIES
        ));
    }

    /**
     * מתודה פרטית להוספת שאלה לאחר בדיקת תקינות.
     * בודקת שלכל שאלה יש בדיוק 4 אפשרויות ושאינדקס התשובה הנכונה בטווח.
     * אם ה-ID הוא null, הוא מוקצה אוטומטית.
     */
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

    /**
     * מחזירה את כל השאלות כרשימה בלתי ניתנת לשינוי.
     */
    public List<Question> getAllQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * מחזירה שאלה אקראית מהרשימה.
     */
    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        int index = random.nextInt(questions.size());
        return Optional.of(questions.get(index));
    }
}
