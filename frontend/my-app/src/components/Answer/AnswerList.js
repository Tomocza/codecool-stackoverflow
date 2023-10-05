import { useEffect, useState } from 'react';
import { useParams, useNavigate, useOutletContext } from 'react-router-dom';
import Answer from "./Answer.js";
import "./Answer.css";
import DateFormatter from '../Utilities/DateFormatter.js';
import { BACKEND_ROOT } from '../../constants';

function AnswerList() {
  const [answers, setAnswers] = useState([]);
  const [question, setQuestion] = useState("");
  const [body, setBody] = useState("");
  const [loading, setLoading] = useState(false);
  const [userId, setUserId] = useOutletContext();
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    try {
      setLoading(true);
      const currentId = userId ?? -1;
      async function fetchQuestions() {
        const response = await fetch(`${BACKEND_ROOT}/questions/${id}/${currentId}`);
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
    const currentId = userId ?? -1;
    async function fetchAnswers() {
      const response = await fetch(`${BACKEND_ROOT}/answers/question/${id}/${currentId}`);
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
    const currentId = userId ?? -1;
    const response = await fetch(`${BACKEND_ROOT}/answers/question/${id}/${currentId}`);
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
      userId: userId,
      questionId: question.id
    }
    try{
      setLoading(true);
      const response = await fetch(`${BACKEND_ROOT}/answers/`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newAnswer)
      });
      const x = await response.json();
      console.log(x);
      refreshAnswers();
    } catch(error){
      console.error(error)
    } finally{
      setLoading(false);
    }
  }

function handleSubmit() {
  if (userId != null){
    if (body.length >= 3) {
      submitAnswer();
    } else {
      console.log("That looks like a rather short message!");
    }
  } else{
    navigate("/register");
  }
}

async function updateQuestion() {
  try{
    const currentId = userId ?? -1;
    setLoading(true);
    const response = await fetch(`${BACKEND_ROOT}/questions/${question.id}/${userId}`);
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
    const response = await fetch(`${BACKEND_ROOT}/questions/votes`, {
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
  if (userId != null){
    const newVote = {
      userId: userId,
      questionId: question.id,
      value: 1
    }
    await vote(newVote);
    updateQuestion();
  } else {
    navigate("/register");
  }
}

async function voteDown() {
  if (userId != null){
    const newVote = {
      userId: userId,
      questionId: question.id,
      value: -1
    }
    await vote(newVote);
    updateQuestion();
  } else {
    navigate("/register");
  }
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