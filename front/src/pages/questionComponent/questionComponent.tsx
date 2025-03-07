import React, { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { fetchRandomQuestion, submitAnswer } from "../../utiles/api";
import { Question } from "../interface/Question";

const QuestionComponent: React.FC = () => {
  const [searchParams] = useSearchParams();
  const gameMode = searchParams.get("mode") || "PvC";
  const category = searchParams.get("category") || "SCIENCE";

  const [question, setQuestion] = useState<Question | null>(null);
  const [selectedOption, setSelectedOption] = useState<number | null>(null);
  const [message, setMessage] = useState<string>("");

  const loadQuestion = useCallback(async () => {
    try {
      console.log("Fetching question for category:", category);
      const data: Question = await fetchRandomQuestion(category);
      setQuestion(data);
      setSelectedOption(null);
      setMessage("");
    } catch (error) {
      console.error("Error fetching question:", error);
      setMessage("Error loading question. Please try again.");
    }
  }, [category]);

  useEffect(() => {
    loadQuestion();
  }, [loadQuestion]);

  const handleAnswerSubmit = async () => {
    if (selectedOption === null || !question) return;
    try {
      console.log("Submitting answer for category:", category);
      const response = await submitAnswer(question.id, selectedOption, category);
      setMessage(response.message);
      if (response.correct) {
        loadQuestion();
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
          <button onClick={handleAnswerSubmit} disabled={selectedOption === null}>
            Submit Answer
          </button>
          {message && <p>{message}</p>}
        </>
      ) : (
        <p>Loading question...</p>
      )}
    </div>
  );
};

export default QuestionComponent;
