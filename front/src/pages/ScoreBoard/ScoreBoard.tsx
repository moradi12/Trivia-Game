import React from "react";
import "./scoreBoard.css";

interface ScoreBoardProps {
  roundNumber?: number;
  currentPlayerTurn?: string;
  player1Name?: string;
  player1Score?: number;
  player2Name?: string;
  player2Score?: number;
}

const ScoreBoard: React.FC<ScoreBoardProps> = ({
  roundNumber,
  currentPlayerTurn,
  player1Name,
  player1Score,
  player2Name,
  player2Score,
}) => {
  return (
    <div className="score-board">
      {roundNumber !== undefined && <p>Round: {roundNumber}</p>}
      {currentPlayerTurn && <p>Current Turn: {currentPlayerTurn}</p>}
      <div className="score-details">
        {player1Name && (
          <p>
            {player1Name}: {player1Score ?? 0}
          </p>
        )}
        {player2Name && (
          <p>
            {player2Name}: {player2Score ?? 0}
          </p>
        )}
      </div>
    </div>
  );
};

export default ScoreBoard;
