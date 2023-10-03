import { useState, useEffect } from 'react';
import "../../App.css";
import Question from './Question';
function QuestionList(){
    const [questions, setQuestions] = useState([]);

    useEffect(() => {
      async function fetchQuestions(){
        const response = await fetch('http://localhost:8080/questions');
        const questions = await response.json();
        setQuestions(questions);
      }
    //   fetchQuestions();
        addQuestions();
      }, []);

  function addQuestions(){
    setQuestions([{
            id: 0,
            question: "How are you?",
            numberOfAnswers: 3,
            numberOfVotes: 5,
            user: "Front Endre",
            date: "2023-10-02"},
        {
            id: 1,
            question: "Is this stack overflow?",
            numberOfAnswers: 5,
            numberOfVotes: 11,
            user: "TheLegend27",
            date: "2023-09-28"}]);
  }


    return (
        <div className="questionList">
            <div>
                <h2>Top Questions</h2>
                <button className='askQuestionButton'>Ask Question</button>
            </div>
            {questions.map((question) => (
                <Question question={question} key={question.id}/>
            ))}
        </div>
    )
}
export default QuestionList;