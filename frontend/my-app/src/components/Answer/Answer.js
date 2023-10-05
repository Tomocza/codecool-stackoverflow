import { useState } from 'react';
import { useOutletContext, useNavigate } from 'react-router-dom';
import DateFormatter from "../Utilities/DateFormatter";
import "./Answer.css"
import { BACKEND_ROOT } from '../../constants';
function Answer({answer}){
    const [rating, setRating] = useState(answer.rating);
    const [loading, setLoading] = useState(false);
    const [userId, setUserId] = useOutletContext();
    const navigate = useNavigate();
      
    async function vote(newVote){
        try{
          setLoading(true);
          const response = await fetch(`${BACKEND_ROOT}/answers/votes`,{
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newVote)
        });
        const newRating = await response.json();
        setRating(() => newRating);
        console.log(newRating);
        } catch(error) {
          console.error(error);
        } finally{
          setLoading(false);
        }
      }
    
      async function voteUp() {
        if (userId != null){
          const newVote = {
            userId: userId,
            answerId: answer.id,
            value: 1
          }
          await vote(newVote);
        } else{
          navigate("/register");

        }
      }
    
      async function voteDown() {
        if (userId != null){
          const newVote = {
            userId: userId,
            answerId: answer.id,
            value: -1
          }
          await vote(newVote);
        } else {
          navigate("/register");
        }
      }
    return(
        <div className="answerContainer">
               <div className="answerVoteContainer">
                    <button className="voteButton" disabled={loading}>
                      <span className="material-symbols-outlined" onClick={voteUp}>arrow_drop_up</span>
                    </button>
                    <div className="questRating">{rating}</div>
                    <button className="voteButton" disabled={loading}>
                      <span className="material-symbols-outlined" onClick={voteDown}>arrow_drop_down</span>
                    </button>
                  </div>
            <div className="answer">
                <div className="answerTextContainer">
                    <div className="answerText">{answer.body}</div>
                    <span className="answerDate"><span className="answerUser">{answer.userName}</span> <DateFormatter date={answer.createdAt}/></span>
                </div>
            </div>
        </div>
    ) 
}
export default Answer;