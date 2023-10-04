import { useState } from 'react';
import DateFormatter from "../Utilities/DateFormatter";
import "./Answer.css"
function Answer({answer}){
    const [rating, setRating] = useState(answer.rating);
 
      async function vote(newVote){
        const response = await fetch('/answers/votes',{
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(newVote)
      });
      const newRating = await response.json();
      setRating(() => newRating);
      console.log(newRating);
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
                    <button className="voteButton">
                      <span className="material-symbols-outlined" onClick={voteUp}>arrow_drop_up</span>
                    </button>
                    <div className="questRating">{rating}</div>
                    <button className="voteButton">
                      <span className="material-symbols-outlined" onClick={voteDown}>arrow_drop_down</span>
                    </button>
                  </div>
            <div className="answer">
                {/* <div className="answerData">
                    <div className="answerVotes">{answer?.numberOfVotes} votes</div>
                </div> */}
                <div className="answerTextContainer">
                    <div className="answerText">{answer.body}</div>
                    <span className="answerDate"><span className="answerUser">{answer.userName}</span> <DateFormatter date={answer.createdAt}/></span>
                </div>
            </div>
        </div>
    ) 
}
export default Answer;