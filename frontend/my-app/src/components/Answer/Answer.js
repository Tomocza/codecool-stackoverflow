import "./Answer.css"
function Answer({answer}){
    return(
        <div className="answer">
            <div className="answerData">
                <div className="answerVotes">{answer.numberOfVotes} votes</div>
            </div>
            <div className="answerTextContainer">
                <div className="answerText">{answer.answer}</div>
                <span className="answerDate"><span className="answerUser">{answer.user}</span> {answer.date}</span>
            </div>
        </div>
    ) 
}
export default Answer;