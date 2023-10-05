import { useState } from 'react';
import DateFormatter from "../Utilities/DateFormatter";
import "./Answer.css"
function Answer({answer}){
    const [rating, setRating] = useState(answer.rating);
    const [loading, setLoading] = useState(false);
      
    async function vote(newVote){
        try{
          setLoading(true);
          const response = await fetch('/answers/votes',{
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
        const newVote = {
          userId: 2,
          answerId: answer.id,
          value: 1
        }
        await vote(newVote);
      }
    
      async function voteDown() {
        const newVote = {
          userId: 2,
          answerId: answer.id,
          value: -1
        }
        await vote(newVote);
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