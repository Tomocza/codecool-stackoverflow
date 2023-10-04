import {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';
import Answer from "./Answer.js";
import "./Answer.css";
import DateFormatter from '../Utilities/DateFormatter.js';

function AnswerList(){
    const [answers, setAnswers] = useState([]);
    const [question, setQuestion] = useState("");
    const {id} = useParams();

    useEffect(() => {
      async function fetchQuestions(){
        const response = await fetch(`/questions/${id}`);
        const newQuestion = await response.json();
        setQuestion(newQuestion);
      }
      fetchQuestions();
      }, []);

    useEffect(() => {
      async function fetchAnswers(){
        const response = await fetch(`/answers/question/${id}`);
        const answers = await response.json();
        setAnswers(answers);
      }
      fetchAnswers();
        // addAnswers();
      }, []);


  function addAnswers(){

    setAnswers([{
        body: "Yes!",
        numberOfVotes: 17,
        userName: "Sanyi",
        createdAt: "2023-10-02"},
        {body: "No..?",
        numberOfVotes: 2,
        userName: "Major Anna",
        createdAt: "2023-09-28"}]);
  }
    return(
        <div className="answerList">
            <div>
                <h2 className="questionTitle">{question.title}</h2>
                <div className="questContainer">
                  <span className="questCreatedAt"><DateFormatter date={question.createdAt}/></span>
                  <span className="questUser">{question.userName}</span>
                </div>
                <div className="questBodyContainer">
                  <div className="questVoteContainer">
                    <button className="voteButton">
                      <span className="material-symbols-outlined">arrow_drop_up</span>
                    </button>
                    <div className="questRating">{question?.rating}</div>
                    <button className="voteButton">
                      <span className="material-symbols-outlined">arrow_drop_down</span>
                    </button>
                  </div>
                  <div className='questBody'>{question.body}</div>
                </div>
                {/* <button className='askQuestionButton'>Ask Question</button> */}
            </div>
            <div className="answerCountTitle">{answers.length} Answers</div>
            {answers.map((answer) => (
                <Answer answer={answer} key={answer.id}/>
            ))}
            <div className='areaLabel'>Your Answer</div>
            <textarea></textarea>
            <button className="postButton">Post Your Answer</button>
        </div>
    )
}
export default AnswerList;