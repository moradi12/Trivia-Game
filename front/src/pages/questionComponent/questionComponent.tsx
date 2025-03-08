import React, { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import {
  GameResponse,
  startGameSession,
  submitAnswerForSession,
} from "../../utiles/api";

interface Question {
  id: number;
  text: string;
  options: string[];
  correctIndex: number; // Not typically shown to user
  category: string;
  difficulty?: string;
}

const QuestionComponent: React.FC = () => {
  // We read query params to decide game mode / category / players
  const [searchParams] = useSearchParams();
  const gameMode = searchParams.get("mode") || "PVC"; // or "PVP"
  const category = searchParams.get("category") || "SCIENCE";
  const player1Name = searchParams.get("player1Name") || "Player1";
  const player2Name = searchParams.get("player2Name") || ""; // optional

  // Local state
  const [sessionId, setSessionId] = useState<string>("");
  const [question, setQuestion] = useState<Question | null>(null);
  const [selectedOption, setSelectedOption] = useState<number | null>(null);
  const [message, setMessage] = useState<string>("");

  // 1) Start the game session on mount + fetch first question
  const startGame = useCallback(async () => {
    try {
      setMessage("Starting game session...");
      // call back-end: /api/game/start?mode=..&category=..&player1Name=..&player2Name=..
      const response: GameResponse = await startGameSession(gameMode, category, player1Name, player2Name);
      setSessionId(response.sessionId || "");
      if (response.question) {
        setQuestion(response.question);
        setMessage(response.message || "");
      } else {
        // If no question, might be game over or no questions in DB
        setQuestion(null);
        setMessage(response.message || "No questions found.");
      }
    } catch (error) {
      console.error("Error starting session:", error);
      setMessage("Error starting game. Please try again.");
    }
  }, [gameMode, category, player1Name, player2Name]);

  useEffect(() => {
    startGame();
  }, [startGame]);

  // 2) Submit an answer
  const handleAnswerSubmit = async () => {
    if (!question || selectedOption === null) return;
    try {
      // call /api/game/answer?sessionId=... with body: { questionId, selectedAnswerIndex }
      const response: GameResponse = await submitAnswerForSession(
        sessionId,
        question.id,
        selectedOption
      );
      setMessage(response.message);

      // If the response contains a next question, show it
      if (response.question) {
        setQuestion(response.question);
        setSelectedOption(null);
      } else {
        // Possibly game over or no more questions
        setQuestion(null);
      }
    } catch (error) {
      console.error("Error submitting answer:", error);
      setMessage("Error submitting answer. Please try again.");
    }
  };

  return (
    <div className="question-container">
      <h2>Game Mode: {gameMode}</h2>
      <h3>Category: {category}</h3>
      {sessionId && <p>Session ID: {sessionId}</p>}

      {question ? (
        <>
          <h2>{question.text}</h2>
          <ul>
            {question.options.map((option, index) => (
              <li key={index}>
                <button
                  className={selectedOption === index ? "selected" : ""}
                  onClick={() => setSelectedOption(index)}
                >
                  {option}
                </button>
              </li>
            ))}
          </ul>
          <button
            onClick={handleAnswerSubmit}
            disabled={selectedOption === null}
          >
            Submit Answer
          </button>
        </>
      ) : (
        <p>No question loaded. {message}</p>
      )}

      {message && <p>{message}</p>}
    </div>
  );
};

export default QuestionComponent;
