import "./Answer.css"
function Answer({answer}){
    return(
        <div className="answerContainer">
               <div className="answerVoteContainer">
                    <button className="voteButton">
                      <span class="material-symbols-outlined">arrow_drop_up</span>
                    </button>
                    <div className="questRating">100</div>
                    <button className="voteButton">
                      <span class="material-symbols-outlined">arrow_drop_down</span>
                    </button>
                  </div>
            <div className="answer">
                {/* <div className="answerData">
                    <div className="answerVotes">{answer?.numberOfVotes} votes</div>
                </div> */}
                <div className="answerTextContainer">
                    <div className="answerText">{answer.body}</div>
                    <span className="answerDate"><span className="answerUser">{answer.userName}</span> {answer.createdAt}</span>
                </div>
            </div>
        </div>
    ) 
}
export default Answer;