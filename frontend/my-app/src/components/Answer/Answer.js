import { useState } from 'react';
import { useOutletContext, useNavigate } from 'react-router-dom';
import DateFormatter from "../Utilities/DateFormatter";
import "./Answer.css"
import { BACKEND_ROOT } from '../../constants';
function Answer({currentAnswer}){
    const [answer, setAnswer] = useState(currentAnswer);
    const [rating, setRating] = useState(answer.rating);
    const [loading, setLoading] = useState(false);
    const [userId, setUserId] = useOutletContext();

    const navigate = useNavigate();
      

  // async function refreshAnswer(){
  //   try{
  //     setLoading(true)
  //     const response = await fetch(`/answers/${answer.id}`);
  //   const newAnswer = await response.json();
  //   setAnswer(() => newAnswer);
  //   setRating(() => newAnswer.rating);
  //   } catch(error) {
  //     console.error(error);
  //   } finally{
  //     setLoading(false);
  //   }

  //   }

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
        // await refreshAnswer();
        console.log(newRating);
        } catch(error) {
          console.error(error);
        } finally{
          setLoading(false);
        }
      }

      async function deleteVote(answerId){
        try{
          setLoading(true);
          const response = await fetch(`${BACKEND_ROOT}/answers/votes/${answerId}/${userId}`,{
            method: 'DELETE',
            headers: {'Content-Type': 'application/json'}
        });
        const newRating = await response.json();
        setRating(() => newRating);
        // await refreshAnswer();
        console.log(newRating);
        } catch(error) {
          console.error(error);
        } finally{
          setLoading(false);
        }
      }
    
      async function voteUp() {
        if (userId != null){
          if (answer.hasVoted != 1){
            answer.hasVoted = 1;
            setAnswer(() => answer);
            const newVote = {
              userId: userId,
              answerId: answer.id,
              value: 1
            }
            await vote(newVote);
          } else {
            answer.hasVoted = 0;
            setAnswer(() => answer);
            await deleteVote(answer.id);
          }
        } else {
          navigate("/register");

        }
      }
    
      async function voteDown() {
        if (userId != null){
          if (answer.hasVoted != -1){
            answer.hasVoted = -1;
            setAnswer(() => answer);
            const newVote = {
              userId: userId,
              answerId: answer.id,
              value: -1
            }
            await vote(newVote);
          } else {
            answer.hasVoted = 0;
            setAnswer(() => answer);
            await deleteVote(answer.id);
          }
        } else {
          navigate("/register");
        }
      }
    return(
        <div className="answerContainer">
          {/* {console.log(answer)} */}
               <div className="answerVoteContainer">
                    <button className={`voteButton  ${answer.hasVoted == 1 ? "upVoted" : ""}`} disabled={loading}>
                      <span className="material-symbols-outlined" onClick={voteUp} >arrow_drop_up</span>
                    </button>
                    <div className="questRating">{rating}</div>
                    <button className={`voteButton  ${answer.hasVoted == -1 ? "downVoted" : ""}`} disabled={loading}>
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