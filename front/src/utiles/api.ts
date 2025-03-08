import axios from "axios";

export interface Question {
  id: number;
  text: string;
  options: string[];
  correctIndex: number;
  category: string;
  difficulty?: string;
}

export interface GameResponse {
  correct: boolean;
  message: string;
  question: Question | null;
  failures: number;
  sessionId?: string;
  // Additional fields for extended UI feedback
  roundNumber?: number;
  currentPlayerTurn?: string; // e.g., "Player1" or "Player2"
  player1Score?: number;
  player2Score?: number;
}

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

export const submitAnswerForSession = async (
  sessionId: string,
  questionId: number,
  selectedAnswerIndex: number
): Promise<GameResponse> => {
  const url = `http://localhost:8080/api/game/answer?sessionId=${sessionId}`;
  const body = {
    questionId,
    selectedAnswerIndex,
    // Optionally include other fields if required by the backend.
  };
  const response = await axios.post(url, body);
  return response.data as GameResponse;
};

/**
 * joinGameSession allows a player to join an existing game by providing the gameId and their name.
 */
export const joinGameSession = async (
  gameId: string,
  player2Name: string
): Promise<GameResponse> => {
  const url = `http://localhost:8080/api/game/join?gameId=${gameId}&player2Name=${player2Name}`;
  const response = await axios.post(url);
  return response.data as GameResponse;
};
