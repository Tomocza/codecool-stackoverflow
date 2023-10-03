import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import Answer from "./Answer.js";
import "./Answer.css";

function AnswerList(){
    const [answers, setAnswers] = useState([]);
    const [question, setQuestion] = useState("");
    const {id} = useParams();

    useEffect(() => {
      async function fetchAnswers(){
        const response = await fetch('http://localhost:8080/answers');
        const answers = await response.json();
        setAnswers(answers);
      }
    //   fetchAnswers();
        addAnswers();
        addQuestion();
      }, []);

    function addQuestion(){
        const questions = ["How are you?", "Is this stack overflow?"];
        setQuestion(questions[id]);
    }

  function addAnswers(){

    setAnswers([{
        answer: "Yes!",
        numberOfVotes: 17,
        user: "Sanyi",
        date: "2023-10-02"},
        {answer: "No..?",
        numberOfVotes: 2,
        user: "Major Anna",
        date: "2023-09-28"}]);
  }
    return(
        <div className="answerList">
            <div>
                <h2 className="questionTitle">{question}</h2>
                {/* <button className='askQuestionButton'>Ask Question</button> */}
            </div>
            {answers.map((answer) => (
                <Answer answer={answer} key={answer.numberOfVotes}/>
            ))}
            <div className='areaLabel'>Your Answer</div>
            <textarea></textarea>
            <button className="postButton">Post Your Answer</button>
        </div>
    )
}
export default AnswerList;