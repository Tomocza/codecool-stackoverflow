import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import "../../App.css";
import Question from './Question';
function QuestionList(){
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
      async function fetchQuestions(){
        const response = await fetch('/questions/all');
        const newQuestions = await response.json();
        // console.log(newQuestions);
        setQuestions(newQuestions);
      }
      fetchQuestions();
        // addQuestions();
      }, []);

    return (
        <div className="questionList">
            <div>
                <h2>Top Questions</h2>
                <Link to="/question/ask">
                  <button className='askQuestionButton'>Ask Question</button>
                </Link>
            </div>
            {questions.map((question) => (
                <Question question={question} key={question.id}/>
            ))}
        </div>
    )
}
export default QuestionList;