import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Menu: React.FC = () => {
  const navigate = useNavigate();

  // Local state for game config
  const [gameMode, setGameMode] = useState<string>("PVC");
  const [category, setCategory] = useState<string>("SCIENCE");

  // Player name fields
  const [player1Name, setPlayer1Name] = useState<string>("Alice");
  const [player2Name, setPlayer2Name] = useState<string>("Bob"); // shown only if PVP

  const handleStartGame = () => {
    // Build query params
    // If PVP, we include player2Name. If PVC, we can skip or leave it blank
    let path = `/game?mode=${gameMode}&category=${category}&player1Name=${player1Name}`;
    if (gameMode === "PVP") {
      path += `&player2Name=${player2Name}`;
    }

    navigate(path);
  };

  return (
    <div className="menu-container">
      <h1>Trivia Game</h1>

      <label>Choose Game Mode:</label>
      <select value={gameMode} onChange={(e) => setGameMode(e.target.value)}>
        <option value="PVC">Play vs Computer</option>
        <option value="PVP">Play vs Player</option>
      </select>

      <div className="player-names">
        <label>Player 1 Name:</label>
        <input
          type="text"
          value={player1Name}
          onChange={(e) => setPlayer1Name(e.target.value)}
        />

        {gameMode === "PVP" && (
          <>
            <label>Player 2 Name:</label>
            <input
              type="text"
              value={player2Name}
              onChange={(e) => setPlayer2Name(e.target.value)}
            />
          </>
        )}
      </div>

      <label>Choose a Category:</label>
      <select value={category} onChange={(e) => setCategory(e.target.value)}>
        <option value="SPORT">Sports</option>
        <option value="ACTUALITY">Actuality</option>
        <option value="COUNTRIES">Countries</option>
        <option value="POLITICS">Politics</option>
        <option value="FLAGS">Flags</option>
        <option value="ENTERTAINMENT">Entertainment</option>
        <option value="HISTORY">History</option>
        <option value="SCIENCE">Science</option>
        <option value="GEOGRAPHY">Geography</option>
      </select>

      <button onClick={handleStartGame}>Start Game</button>
    </div>
  );
};

export default Menu;
