import axios from "axios";

export interface Question {
  id: number;
  text: string;
  options: string[];
  correctIndex: number; // typically hidden from user
  category: string;
  difficulty?: string;
}

export interface GameResponse {
  correct: boolean;
  message: string;
  question: Question | null;
  failures: number;
  sessionId?: string; // if included
}

/**
 * 1) Start a new game session, retrieving the first question
 */
export const startGameSession = async (
  mode: string,
  category: string,
  player1Name: string,
  player2Name: string
): Promise<GameResponse> => {
  const url = "http://localhost:8080/api/game/start";
  const params = new URLSearchParams();
  params.append("mode", mode);
  params.append("category", category);
  params.append("player1Name", player1Name);
  if (player2Name) {
    params.append("player2Name", player2Name);
  }

  const response = await axios.post(url + "?" + params.toString());
  return response.data as GameResponse;
};

/**
 * 2) Submit an answer for the current question in the session
 */
export const submitAnswerForSession = async (
  sessionId: string,
  questionId: number,
  selectedAnswerIndex: number
): Promise<GameResponse> => {
  const url = `http://localhost:8080/api/game/answer?sessionId=${sessionId}`;

  const body = {
    questionId,
    selectedAnswerIndex,
    // Not including category here unless your server demands it
  };

  const response = await axios.post(url, body);
  return response.data as GameResponse;
};
