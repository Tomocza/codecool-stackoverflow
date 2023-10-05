import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Answer from "./Answer.js";
import "./Answer.css";
import DateFormatter from '../Utilities/DateFormatter.js';

function AnswerList() {
  const [answers, setAnswers] = useState([]);
  const [question, setQuestion] = useState("");
  const [body, setBody] = useState("");
  const [loading, setLoading] = useState(false);
  const { id } = useParams();

  useEffect(() => {
    try {
      setLoading(true);
      async function fetchQuestions() {
        const response = await fetch(`/questions/${id}`);
        const newQuestion = await response.json();
        setQuestion(newQuestion);
      }
      fetchQuestions();
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
}, []);

useEffect(() => {
  try{
    setLoading(true);
    async function fetchAnswers() {
      const response = await fetch(`/answers/question/${id}`);
      const answers = await response.json();
      setAnswers(answers);
    }
    fetchAnswers();
  } catch(error){
    console.error(error);
  } finally{
    setLoading(false);
  }
}, []);

function getBody(e) {
  setBody(() => e.target.value);
}

async function refreshAnswers() {
  try{
    setLoading(true);
    const response = await fetch(`/answers/question/${id}`);
    const answers = await response.json();
    setAnswers(answers);
  } catch(error){
    console.error(error)
  } finally{
    setLoading(false);
  }
}

async function submitAnswer() {
  const newAnswer = {
    body: body,
    userId: 2,
    questionId: question.id
  }
  try{
    setLoading(true);
    const response = await fetch(`/answers/`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newAnswer)
    });
    refreshAnswers();
  } catch(error){
    console.error(error)
  } finally{
    setLoading(false);
  }
}

function handleSubmit() {
  if (body.length >= 5) {
    submitAnswer();
  } else {
    console.log("That looks like a rather short message!");
  }
}

async function updateQuestion() {
  try{
    setLoading(true);
    const response = await fetch(`/questions/${question.id}`);
    const newQuestion = await response.json();
    setQuestion(newQuestion);
  } catch(error){
    console.error(error);
  } finally{
    setLoading(false);
  }
}

async function vote(newVote) {
  try{
    setLoading(true);
    const response = await fetch('/questions/votes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newVote)
    });
  } catch(error){
    console.error(error);
  } finally {
    setLoading(false);
  }
}

async function voteUp() {
  const newVote = {
    userId: 2,
    questionId: question.id,
    value: 1
  }
  await vote(newVote);
  updateQuestion();
}

async function voteDown() {
  const newVote = {
    userId: 2,
    questionId: question.id,
    value: -1
  }
  await vote(newVote);
  updateQuestion();
}

return (
  <div className="answerList">
    <div>
      <h2 className="questionTitle">{question.title}</h2>
      <div className="questContainer">
        <span className="questCreatedAt"><DateFormatter date={question.createdAt} /></span>
        <span className="questUser">{question.userName}</span>
      </div>
      <div className="questBodyContainer">
        <div className="questVoteContainer">
          <button className="voteButton" onClick={voteUp} disabled={loading}>
            <span className="material-symbols-outlined">arrow_drop_up</span>
          </button>
          <div className="questRating">{question?.rating}</div>
          <button className="voteButton" onClick={voteDown} disabled={loading}>
            <span className="material-symbols-outlined">arrow_drop_down</span>
          </button>
        </div>
        <div className='questBody'>{question.body}</div>
      </div>
    </div>
    <div className="answerCountTitle">{answers.length} Answers</div>
    {answers.map((answer) => (
      <Answer answer={answer} key={answer.id} />
    ))}
    <div className='areaLabel'>Your Answer</div>
    <textarea onChange={(e) => getBody(e)}></textarea>
    <button className="postButton" onClick={handleSubmit} disabled={loading}>Post Your Answer</button>
  </div>
)
}
export default AnswerList;