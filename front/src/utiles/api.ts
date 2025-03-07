import axios from "axios";
import { Question } from "../pages/interface/Question";

const API_URL = "http://localhost:8080/api/questions";

// Fetch a random question by category.
export const fetchRandomQuestion = async (category: string): Promise<Question> => {
    console.log("Fetching question for category:", category);
    const response = await axios.get(`${API_URL}/randomByCategory?category=${category}`);
    return response.data;
};

// Submit an answer with the question id, selected option, and category.
export const submitAnswer = async (
    questionId: number, 
    selectedAnswerIndex: number, 
    category: string
) => {
    console.log("Submitting answer with category:", category);
    const response = await axios.post("http://localhost:8080/api/game/answer", {
        questionId,
        selectedAnswerIndex,
        category, // Ensuring the category is sent
    });
    return response.data;
};
