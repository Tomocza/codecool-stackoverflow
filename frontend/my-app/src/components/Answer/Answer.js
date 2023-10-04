import "./Answer.css"
function Answer({answer}){
    return(
        <div className="answer">
            <div className="answerData">
                <div className="answerVotes">{answer?.numberOfVotes} votes</div>
            </div>
            <div className="answerTextContainer">
                <div className="answerText">{answer.body}</div>
                <span className="answerDate"><span className="answerUser">{answer.userName}</span> {answer.createdAt}</span>
            </div>
        </div>
    ) 
}
export default Answer;