import { useState, useEffect } from 'react';
import { Link, useNavigate, useOutletContext } from 'react-router-dom';

import "../../App.css";
import Question from './Question';
<<<<<<< HEAD
import { BACKEND_ROOT } from '../../constants';
function QuestionList(){
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
      async function fetchQuestions(){
        const response = await fetch(`${BACKEND_ROOT}/questions/all`);
=======
function QuestionList() {
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [userId, setUserId] = useOutletContext();
  const navigate = useNavigate();

  useEffect(() => {
    try {
      setLoading(true);
      async function fetchQuestions() {
        const response = await fetch('/questions/all');
>>>>>>> f29a96d334f05d5cc4bc07a1a71086c867217530
        const newQuestions = await response.json();
        setQuestions(newQuestions);
      }
      fetchQuestions();
    } catch (error) {
      console.error(error)
    } finally {
      setLoading(false)
    }
  }, []);

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
      </div>
      {questions.map((question) => (
        <Question question={question} key={question.id} />
      ))}
    </div>
  )
}
export default QuestionList;