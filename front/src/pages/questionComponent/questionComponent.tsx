import React, { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import ScoreBoard from "./../ScoreBoard/ScoreBoard"; // Adjust import path if needed
import "./questionComponent.css";

import {
  GameResponse,
  startGameSession,
  submitAnswerForSession,
} from "../../utiles/api";

interface Question {
  id: number;
  text: string;
  options: string[];
  correctIndex: number; 
  category: string;
  difficulty?: string;
}

const QuestionComponent: React.FC = () => {
  const [searchParams] = useSearchParams();
  const gameMode = searchParams.get("mode") || "PVC";
  const category = searchParams.get("category") || "SCIENCE";
  const player1Name = searchParams.get("player1Name") || "Player1";
  const player2Name = searchParams.get("player2Name") || "";

  const [sessionId, setSessionId] = useState("");
  const [question, setQuestion] = useState<Question | null>(null);
  const [selectedOption, setSelectedOption] = useState<number | null>(null);
  const [infoMessage, setInfoMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [timeRemaining, setTimeRemaining] = useState(30);

  // Extended state for additional game info
  const [roundNumber, setRoundNumber] = useState<number | undefined>(undefined);
  const [currentPlayerTurn, setCurrentPlayerTurn] = useState<string | undefined>(undefined);
  const [player1Score, setPlayer1Score] = useState<number>(0);
  const [player2Score, setPlayer2Score] = useState<number>(0);

  const updateFromResponse = (response: GameResponse) => {
    if (response.sessionId) {
      setSessionId(response.sessionId);
    }
    setQuestion(response.question || null);
    setInfoMessage(response.message || "");
    // Update additional fields if present
    setRoundNumber(response.roundNumber);
    setCurrentPlayerTurn(response.currentPlayerTurn);
    setPlayer1Score(response.player1Score ?? 0);
    setPlayer2Score(response.player2Score ?? 0);
  };

  /**
   * Starts the game session on mount and fetches the first question.
   */
  const startNewGameSession = useCallback(async () => {
    try {
      setIsLoading(true);
      setInfoMessage("Starting game session...");
      const response: GameResponse = await startGameSession(
        gameMode,
        category,
        player1Name,
        player2Name
      );
      updateFromResponse(response);
    } catch (error) {
      console.error("Error starting session:", error);
      setInfoMessage("Error starting game. Please try again.");
    } finally {
      setIsLoading(false);
    }
  }, [gameMode, category, player1Name, player2Name]);

  useEffect(() => {
    startNewGameSession();
  }, [startNewGameSession]);

  const handleSubmitAnswer = async () => {
    if (!question) return;
    // Use -1 to indicate that no option was selected (timeout).
    const answerIndex = selectedOption !== null ? selectedOption : -1;
    try {
      const response: GameResponse = await submitAnswerForSession(
        sessionId,
        question.id,
        answerIndex
      );
      updateFromResponse(response);
      // Reset the selected option for new question.
      if (response.question) {
        setSelectedOption(null);
      }
    } catch (error) {
      console.error("Error submitting answer:", error);
      setInfoMessage("Error submitting answer. Please try again.");
    }
  };

  useEffect(() => {
    if (!question) return;
    setTimeRemaining(30);
    const intervalId = setInterval(() => {
      setTimeRemaining((prevTime) => {
        if (prevTime <= 1) {
          clearInterval(intervalId);
          handleSubmitAnswer();
          return 0;
        }
        return prevTime - 1;
      });
    }, 1000);
    return () => clearInterval(intervalId);
  }, [question]);

  return (
    <div className="question-container">
      <h2>Game Mode: {gameMode}</h2>
      <h3>Category: {category}</h3>
      {sessionId && <p>Session ID: {sessionId}</p>}

      {/* Display ScoreBoard in PvP mode */}
      {gameMode === "PVP" && (
        <ScoreBoard
          roundNumber={roundNumber}
          currentPlayerTurn={currentPlayerTurn}
          player1Name={player1Name}
          player1Score={player1Score}
          player2Name={player2Name}
          player2Score={player2Score}
        />
      )}

      {isLoading && <p className="loading-message">Loading...</p>}
      {!isLoading && question && (
        <p className="timer">Time Remaining: {timeRemaining} sec</p>
      )}

      {!isLoading && question ? (
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
          <button onClick={handleSubmitAnswer} disabled={selectedOption === null}>
            Submit Answer
          </button>
        </>
      ) : (
        !isLoading && <p>{infoMessage}</p>
      )}

      {infoMessage && !isLoading && <p>{infoMessage}</p>}
    </div>
  );
};

export default QuestionComponent;
