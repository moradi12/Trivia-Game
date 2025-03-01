package com.trivia.trivia.game.Questions;
import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Question;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component

public class SportQuestionRepository {

    private final List<Question> questions = new ArrayList<>();
    private final Random random = new Random();
    private final AtomicInteger questionIdCounter = new AtomicInteger(1);

    /**
     * אתחול רשימת השאלות עבור קטגוריית ספורט.
     */
    @PostConstruct
    public void init() {
        List<Question> initialQuestions = List.of(
                new Question(null, "מי זכה במונדיאל 2018?", List.of("ברזיל", "גרמניה", "צרפת", "ארגנטינה"), 2, Category.SPORT),
                new Question(null, "איזו קבוצה זכתה בליגת האלופות 2021?", List.of("ברצלונה", "מנצ'סטר סיטי", "באיירן", "צ'לסי"), 3, Category.SPORT),
                new Question(null, "איזה שחקן כדורגל זכה הכי הרבה פעמים בכדור הזהב?", List.of("ליאו מסי", "כריסטיאנו רונאלדו", "פלה", "זידאן"), 0, Category.SPORT),
                new Question(null, "כמה שחקנים יש בכל קבוצה על המגרש במשחק כדורגל?", List.of("9", "10", "11", "12"), 2, Category.SPORT),
                new Question(null, "באיזו עיר נערכה האולימפיאדה ב-2008?", List.of("אתונה", "סידני", "בייג'ינג", "לונדון"), 2, Category.SPORT),
                new Question(null, "איזו מדינה זכתה הכי הרבה פעמים בגביע העולם בכדורגל?", List.of("גרמניה", "ברזיל", "איטליה", "ארגנטינה"), 1, Category.SPORT),
                new Question(null, "איזה כדורסלן קלע הכי הרבה נקודות בהיסטוריה של ה-NBA?", List.of("לברון ג'יימס", "קארים עבדול-ג'באר", "מייקל ג'ורדן", "קווין דוראנט"), 1, Category.SPORT),
                new Question(null, "כמה טבעות אליפות יש למייקל ג'ורדן?", List.of("4", "5", "6", "7"), 2, Category.SPORT),
                new Question(null, "באיזו מדינה נערך טורניר ווימבלדון?", List.of("ארצות הברית", "צרפת", "ספרד", "אנגליה"), 3, Category.SPORT),
                new Question(null, "איזה מועדון כדורגל מכונה 'השדים האדומים'?", List.of("מנצ'סטר יונייטד", "ליברפול", "באיירן מינכן", "מילאן"), 0, Category.SPORT),
                new Question(null, "כמה זמן נמשך משחק כדורגל סטנדרטי (ללא הארכה)?", List.of("80 דקות", "90 דקות", "100 דקות", "120 דקות"), 1, Category.SPORT),
                new Question(null, "איזו מדינה הומצא משחק הבייסבול?", List.of("ארה\"ב", "קובה", "יפן", "קנדה"), 0, Category.SPORT),
                new Question(null, "איזה נהג זכה הכי הרבה פעמים באליפות הפורמולה 1?", List.of("לואיס המילטון", "מיכאל שומאכר", "איירטון סנה", "ניקו רוזברג"), 1, Category.SPORT),
                new Question(null, "איזו מדינה אירחה את מונדיאל 2022?", List.of("רוסיה", "ברזיל", "קטאר", "ארצות הברית"), 2, Category.SPORT),
                new Question(null, "מי היה השחקן הראשון שהגיע ל-1000 שערים בקריירת הכדורגל שלו?", List.of("פלה", "מרדונה", "כריסטיאנו רונאלדו", "ליאו מסי"), 0, Category.SPORT),
                new Question(null, "מי זכה בטור דה פראנס הכי הרבה פעמים?", List.of("לאנס ארמסטרונג", "אדי מרקס", "כריס פרום", "מיגל אינדוראין"), 1, Category.SPORT),
                new Question(null, "איזו קבוצה מחזיקה בשיא הזכיות בגביע המדינה בכדורגל בישראל?", List.of("מכבי תל אביב", "הפועל תל אביב", "בית\"ר ירושלים", "מכבי חיפה"), 0, Category.SPORT),
                new Question(null, "מי היה הקפטן של נבחרת צרפת במונדיאל 1998?", List.of("דידייה דשאן", "זינדין זידאן", "לורן בלאן", "פטריק ויירה"), 0, Category.SPORT),
                new Question(null, "איזה מועדון כדורגל זכה הכי הרבה פעמים באליפות הליגה הספרדית?", List.of("ריאל מדריד", "ברצלונה", "אתלטיקו מדריד", "סביליה"), 0, Category.SPORT),
                new Question(null, "מי זכה במרתון בוסטון בשנת 2023?", List.of("אליוד קיפצ'וגה", "אבבה ביקילה", "אוונס צ'בט", "מרטין קיפרוטו"), 2, Category.SPORT),
                new Question(null, "מי היה השחקן הראשון שהגיע ל-1000 שערים בקריירת הכדורגל שלו?", List.of("פלה", "מרדונה", "כריסטיאנו רונאלדו", "ליאו מסי"), 0, Category.SPORT),
                new Question(null, "מי נחשב לשחיין הגדול ביותר בכל הזמנים?", List.of("מייקל פלפס", "יאן תורפ", "ראיין לוכטה", "מארק ספיץ"), 0, Category.SPORT),
                new Question(null, "מי היה השחקן הראשון שנבחר בדראפט ה-NBA ב-2003?", List.of("כרמלו אנתוני", "דווין וויד", "כריס בוש", "לברון ג'יימס"), 3, Category.SPORT),
                new Question(null, "איזה שחקן קלע הכי הרבה נקודות למשחק ב-NBA?", List.of("מייקל ג'ורדן", "וילט צ'מברלין", "לברון ג'יימס", "קווין דוראנט"), 1, Category.SPORT),
                new Question(null, "איזה מאמן זכה בהכי הרבה אליפויות NBA?", List.of("פיל ג'קסון", "גרג פופוביץ'", "רד אאורבך", "פאט ריילי"), 0, Category.SPORT),
                new Question(null, "מי השחקן עם הכי הרבה אסיסטים בתולדות ה-NBA?", List.of("סטיב נאש", "ג'ון סטוקטון", "ג'ייסון קיד", "מג'יק ג'ונסון"), 1, Category.SPORT),
                new Question(null, "מי היה ה-MVP הכי צעיר בתולדות ה-NBA?", List.of("לברון ג'יימס", "קווין דוראנט", "דריק רוז", "יאניס אנטטוקומפו"), 2, Category.SPORT),
                new Question(null, "איזה טניסאי זכה בהכי הרבה תארי גראנד סלאם?", List.of("רפאל נדאל", "רוג'ר פדרר", "נובאק ג'וקוביץ'", "פיט סמפרס"), 2, Category.SPORT),
                new Question(null, "כמה תארי רולאן גארוס יש לרפאל נדאל?", List.of("10", "12", "14", "16"), 2, Category.SPORT),
                new Question(null, "מי זכתה בהכי הרבה תארי גראנד סלאם בטניס נשים?", List.of("סרינה וויליאמס", "מרגרט קורט", "מרטינה נברטילובה", "כריס אוורט"), 1, Category.SPORT),
                new Question(null, "מי היה השחקן הראשון לזכות ב'גולדן סלאם' בטניס?", List.of("נובאק ג'וקוביץ'", "רוג'ר פדרר", "רפאל נדאל", "שטפי גראף"), 3, Category.SPORT),
                new Question(null, "איזה טורניר טניס נחשב לטורניר הוותיק ביותר?", List.of("ווימבלדון", "הרולאן גארוס", "ה-US Open", "ה-Australian Open"), 0, Category.SPORT),
                new Question(null, "באיזו שנה נערכו המשחקים האולימפיים הראשונים בעת החדשה?", List.of("1896", "1904", "1912", "1920"), 0, Category.SPORT),
                new Question(null, "איזו מדינה זכתה בהכי הרבה מדליות אולימפיות בהיסטוריה?", List.of("סין", "רוסיה", "ארה\"ב", "גרמניה"), 2, Category.SPORT),
                new Question(null, "מי זכה בהכי הרבה מדליות זהב אולימפיות?", List.of("יוסיין בולט", "מייקל פלפס", "לאריסה לטינינה", "קארל לואיס"), 1, Category.SPORT),
                new Question(null, "באיזו מדינה נערכה אולימפיאדת 2016?", List.of("ברזיל", "רוסיה", "סין", "בריטניה"), 0, Category.SPORT),
                new Question(null, "כמה מדליות זהב יש ליוסיין בולט?", List.of("6", "7", "8", "9"), 2, Category.SPORT),
                new Question(null, "איזו קבוצה זכתה באליפות ליגת העל בכדורגל הכי הרבה פעמים?", List.of("הפועל תל אביב", "מכבי חיפה", "מכבי תל אביב", "בית\"ר ירושלים"), 2, Category.SPORT),
                new Question(null, "איזו קבוצה זכתה בגביע המדינה בכדורגל הכי הרבה פעמים?", List.of("הפועל תל אביב", "מכבי תל אביב", "מכבי חיפה", "בית\"ר ירושלים"), 1, Category.SPORT),
                new Question(null, "מי מחזיק בשיא השערים בליגת העל בכל הזמנים?", List.of("אלון מזרחי", "רוני רוזנטל", "יוסי בניון", "עודד מכנס"), 0, Category.SPORT),
                new Question(null, "מי היה המאמן שהוביל את נבחרת ישראל ליורו 2024?", List.of("אלון חזן", "אברהם גרנט", "רוני לוי", "ברק בכר"), 0, Category.SPORT),
                new Question(null, "באיזו שנה נבחרת ישראל השתתפה בגביע העולם בכדורגל?", List.of("1958", "1962", "1970", "1982"), 2, Category.SPORT),
                new Question(null, "מי הוא שיאן ההופעות בנבחרת ישראל?", List.of("יוסי בניון", "ערן זהבי", "חיים רביבו", "אייל ברקוביץ'"), 0, Category.SPORT),
                new Question(null, "איזו קבוצה ישראלית הייתה הראשונה להשתתף בליגת האלופות?", List.of("מכבי תל אביב", "הפועל תל אביב", "מכבי חיפה", "בית\"ר ירושלים"), 2, Category.SPORT),
                new Question(null, "מי השחקן שכבש הכי הרבה שערים במדי נבחרת ישראל?", List.of("אלון מזרחי", "רוני קלדרון", "ערן זהבי", "אבי נמני"), 2, Category.SPORT),
                new Question(null, "איזו קבוצה זכתה בהכי הרבה אליפויות ליגת העל בכדורסל?", List.of("הפועל ירושלים", "מכבי תל אביב", "גליל עליון", "הפועל תל אביב"), 1, Category.SPORT),
                new Question(null, "מי הוא מלך הסלים בכל הזמנים של ליגת העל בכדורסל?", List.of("דורון ג'מצ'י", "טל ברודי", "עודד קטש", "ליאור אליהו"), 0, Category.SPORT),
                new Question(null, "באיזו שנה זכתה נבחרת ישראל במדליית כסף באליפות אירופה בכדורסל?", List.of("1953", "1979", "1983", "1997"), 1, Category.SPORT),
                new Question(null, "איזו קבוצה זכתה בגביע המדינה בכדורסל הכי הרבה פעמים?", List.of("הפועל ירושלים", "מכבי תל אביב", "הפועל תל אביב", "גלבוע/גליל"), 1, Category.SPORT),
                new Question(null, "מי המאמן שהוביל את נבחרת ישראל לזכייה במדליה באליפות אירופה?", List.of("רלף קליין", "אריק שיבק", "דייויד בלאט", "עודד קטש"), 0, Category.SPORT),
                new Question(null, "מי היה הזר הראשון ששיחק בליגת העל בכדורסל?", List.of("ארל וויליאמס", "שאראס יאסיקביצ'יוס", "דני שייז", "נייט האפמן"), 0, Category.SPORT),
                new Question(null, "כמה פעמים זכתה מכבי תל אביב בליגת העל בכדורגל?", List.of("20", "30", "40", "25"), 1, Category.SPORT),
                new Question(null, "מי הוא השחקן עם הכי הרבה הופעות במכבי תל אביב בכדורגל?", List.of("ערן זהבי", "אלי דריקס", "אבי נמני", "מאור בוזגלו"), 2, Category.SPORT),
                new Question(null, "מי המאמן שהוביל את מכבי תל אביב לשלב הבתים של ליגת האלופות בפעם הראשונה?", List.of("אוסקר גרסיה", "ניר לוין", "אבנר קופל", "פאולו סוזה"), 3, Category.SPORT),
                new Question(null, "כמה פעמים זכתה מכבי תל אביב ביורוליג?", List.of("4", "5", "6", "7"), 2, Category.SPORT),
                new Question(null, "מי הוא השחקן עם הכי הרבה נקודות במדי מכבי תל אביב בכדורסל?", List.of("דורון ג'מצ'י", "טאל ברודי", "שאראס יאסיקביצ'יוס", "אנתוני פארקר"), 0, Category.SPORT),
                new Question(null, "מי היה המאמן שהוביל את מכבי תל אביב לזכייה ביורוליג ב-2004 ו-2005?", List.of("עודד קטש", "פיני גרשון", "רלף קליין", "דייויד בלאט"), 1, Category.SPORT),
                new Question(null, "מי קלע את סל הניצחון של מכבי תל אביב בגמר היורוליג 2014?", List.of("שון ג'יימס", "יוגב אוחיון", "טייריס רייס", "דווין סמית'"), 2, Category.SPORT),
                new Question(null, "מי נחשב לזר הגדול ביותר ששיחק במכבי תל אביב בכדורסל?", List.of("אנתוני פארקר", "נייט האפמן", "שאראס יאסיקביצ'יוס", "דוויין וייד"), 0, Category.SPORT),
                new Question(null, "מי היה הקפטן של מכבי תל אביב בכדורסל בתקופת האליפויות ביורוליג 2004-2005?", List.of("שאראס יאסיקביצ'יוס", "טל בורשטיין", "דריק שארפ", "ניקולה וויצ'יץ'"), 2, Category.SPORT),
                new Question(null, "באיזו עונה זכתה מכבי תל אביב באליפות אירופה בכדורסל בפעם הראשונה?", List.of("1967", "1977", "1981", "1991"), 1, Category.SPORT)


        );

        // הוספת השאלות לרשימה
        initialQuestions.forEach(this::addQuestion);

        // ערבוב השאלות כדי ליצור גיוון
        Collections.shuffle(questions);
    }

    /**
     * הוספת שאלה עם בדיקת תקינות.
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
     * מחזירה את מספר השאלות במערכת.
     */
    public int getTotalQuestions() {
        return questions.size();
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
        if (questions.isEmpty()) return Optional.empty();
        return Optional.of(questions.get(random.nextInt(questions.size())));
    }
}
