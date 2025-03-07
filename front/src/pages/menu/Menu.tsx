import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Menu: React.FC = () => {
  const navigate = useNavigate();
  const [gameMode, setGameMode] = useState<string>("PVC");
  const [category, setCategory] = useState<string>("SCIENCE"); 

  const handleStartGame = () => {
    // Go to /game, passing in the query params for mode and category
    navigate(`/game?mode=${gameMode}&category=${category}`);
  };

  return (
    <div className="menu-container">
      <h1>Trivia Game</h1>
      <label>Choose Game Mode:</label>
      <select value={gameMode} onChange={(e) => setGameMode(e.target.value)}>
        <option value="PVC">Play vs Computer</option>
        <option value="PVP">Play vs Player</option>
      </select>

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
