import React from "react";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import Menu from "./pages/menu/Menu";
import QuestionComponent from "./pages/questionComponent/questionComponent";

const App: React.FC = () => {
    return (
        <Router>
            <div className="app-container">
                <Routes>
                    <Route path="/" element={<Menu />} />
                    <Route path="/game" element={<QuestionComponent />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
