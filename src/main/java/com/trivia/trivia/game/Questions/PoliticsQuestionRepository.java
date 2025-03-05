package com.trivia.trivia.game.Questions;

import com.trivia.trivia.game.Entity.Category;
import com.trivia.trivia.game.Entity.Difficulty;
import com.trivia.trivia.game.Entity.Question;
import com.trivia.trivia.game.Repo.QuestionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PoliticsQuestionRepository {

    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();
    private final QuestionRepository questionRepository;

    public PoliticsQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostConstruct
    public void init() {
        addQuestion(new Question(null, "מי מכהן כראש ממשלת ישראל נכון ל-2024?",
                Arrays.asList("בנימין נתניהו", "יאיר לפיד", "בני גנץ", "אביגדור ליברמן"), 0, Category.POLITICS, Difficulty.EASY));

        addQuestion(new Question(null, "איזה נושא מרכזי נמצא במחלוקת פוליטית בינלאומית כיום?",
                Arrays.asList("שינויי אקלים", "ביטחון לאומי", "זכויות אדם", "סחר בינלאומי"), 0, Category.POLITICS, Difficulty.MEDIUM));

        addQuestion(new Question(null, "איזו מדינה מחזיקה בזכות וטו במועצת הביטחון של האו\"ם?",
                Arrays.asList("גרמניה", "בריטניה", "קנדה", "אוסטרליה"), 1, Category.POLITICS, Difficulty.MEDIUM));

        addQuestion(new Question(null, "כמה מדינות חברות באיחוד האירופי (נכון ל-2024)?",
                Arrays.asList("25", "27", "30", "32"), 1, Category.POLITICS, Difficulty.HARD));

        addQuestion(new Question(null, "מי היה נשיא ארה\"ב בעת הכרזת העצמאות של ישראל ב-1948?",
                Arrays.asList("הארי טרומן", "פרנקלין ד. רוזוולט", "דווייט אייזנהאואר", "לינדון ג'ונסון"), 0, Category.POLITICS, Difficulty.HARD));

        addQuestion(new Question(null, "איזו מדינה היא בעלת הכלכלה הגדולה בעולם נכון ל-2024?",
                Arrays.asList("סין", "ארצות הברית", "יפן", "גרמניה"), 1, Category.POLITICS, Difficulty.MEDIUM));

        addQuestion(new Question(null, "איזה גוף אחראי על חקיקת חוקים במדינת ישראל?",
                Arrays.asList("הכנסת", "בית המשפט העליון", "משרד המשפטים", "היועץ המשפטי לממשלה"), 0, Category.POLITICS, Difficulty.EASY));

        addQuestion(new Question(null, "באיזו שנה הוקמה מדינת ישראל?",
                Arrays.asList("1945", "1947", "1948", "1950"), 2, Category.POLITICS, Difficulty.EASY));

        addQuestion(new Question(null, "מי יכול להגיש הצעת חוק בכנסת ישראל?",
                Arrays.asList("רק חברי כנסת", "רק ראש הממשלה", "כל אזרח ישראלי", "חברי כנסת ומשרדי ממשלה"), 3, Category.POLITICS, Difficulty.MEDIUM));

        addQuestion(new Question(null, "מהו שם הפרלמנט של בריטניה?",
                Arrays.asList("הקונגרס", "הסנאט", "הפרלמנט הבריטי", "בית הנבחרים"), 2, Category.POLITICS, Difficulty.EASY));

        addQuestion(new Question(null, "כמה זמן נמשכת כהונת נשיא ארצות הברית?",
                Arrays.asList("3 שנים", "4 שנים", "5 שנים", "6 שנים"), 1, Category.POLITICS, Difficulty.EASY));

        addQuestion(new Question(null, "איזה תפקיד פוליטי נבחר ישירות על ידי אזרחי ישראל?",
                Arrays.asList("ראש ממשלה", "נשיא המדינה", "שר הביטחון", "חבר כנסת"), 3, Category.POLITICS, Difficulty.MEDIUM));

        questionRepository.saveAll(questions);
    }

    private void addQuestion(Question question) {
        if (question.getOptions() == null || question.getOptions().size() != 4) {
            throw new IllegalArgumentException("כל שאלה חייבת להכיל בדיוק 4 אפשרויות");
        }
        if (question.getCorrectIndex() < 0 || question.getCorrectIndex() >= 4) {
            throw new IllegalArgumentException("האינדקס של התשובה הנכונה חייב להיות בין 0 ל-3");
        }
        questions.add(question);
    }

    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    public Optional<Question> getRandomQuestion() {
        if (questions.isEmpty()) {
            return Optional.empty();
        }
        int index = random.nextInt(questions.size());
        return Optional.of(new ArrayList<>(questions).get(index));
    }
}