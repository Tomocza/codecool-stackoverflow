import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import "../../App.css";
import Question from './Question';
function QuestionList(){
    const [questions, setQuestions] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
      try{
        setLoading(true);
        async function fetchQuestions(){
          const response = await fetch('/questions/all');
          const newQuestions = await response.json();
          setQuestions(newQuestions);
        }
        fetchQuestions();
      } catch(error){
        console.error(error)
      } finally{
        setLoading(false)
      }
      }, []);

    return (
        <div className="questionList">
            <div>
                <h2>Top Questions</h2>
                <Link to="/question/ask">
                  <button className='askQuestionButton' disabled={loading}>Ask Question</button>
                </Link>
            </div>
            {questions.map((question) => (
                <Question question={question} key={question.id}/>
            ))}
        </div>
    )
}
export default QuestionList;