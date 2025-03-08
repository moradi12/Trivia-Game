import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { GameResponse, joinGameSession } from "../../utiles/api";
import "./joinGame.css";

const JoinGame: React.FC = () => {
  const [gameId, setGameId] = useState("");
  const [player2Name, setPlayer2Name] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleJoinGame = async () => {
    try {
      const response: GameResponse = await joinGameSession(gameId, player2Name);
      // On successful join, navigate to the game screen, passing the sessionId.
      navigate(`/game?sessionId=${response.sessionId}`);
    } catch (err) {
      console.error("Error joining game:", err);
      setError("Failed to join game. Please check the Game ID and try again.");
    }
  };

  return (
    <div className="join-game-container">
      <h2>Join Game</h2>
      <input
        type="text"
        placeholder="Enter Game ID"
        value={gameId}
        onChange={(e) => setGameId(e.target.value)}
      />
      <input
        type="text"
        placeholder="Enter Your Name"
        value={player2Name}
        onChange={(e) => setPlayer2Name(e.target.value)}
      />
      <button onClick={handleJoinGame}>Join Game</button>
      {error && <p className="error-message">{error}</p>}
    </div>
  );
};

export default JoinGame;
