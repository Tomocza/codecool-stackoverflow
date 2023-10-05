import { useState, useEffect } from 'react';
import { Link, useNavigate, useOutletContext } from 'react-router-dom';

import "../../App.css";
import Question from "./Question";
function QuestionList({ questions, loading }) {
  const [userId, setUserId] = useOutletContext();

  return (
    <div className="questionList">
      <div>
        <h2>Top Questions</h2>
        {userId == null
        ? <Link to="/register">
            <button className='askQuestionButton' disabled={loading}>Ask Question</button>
        </Link>
        : <Link to="/question/ask">
            <button className='askQuestionButton' disabled={loading}>Ask Question</button>
        </Link>
        }
        {/* <Link to="/question/ask">
          <button className="askQuestionButton" disabled={loading}>
            Ask Question
          </button>
        </Link> */}
      </div>
      {questions.map((question) => (
        <Question question={question} key={question.id} />
      ))}
    </div>
  );
}
export default QuestionList;
