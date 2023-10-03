import {Link} from 'react-router-dom';
import "../../App.css"
function Question({question}){
    return(
        <div className="question">
            <div className="questionData">
                <div className="questionVotes">{question.numberOfVotes} votes</div>
                <div className="questionAnswers">{question.numberOfAnswers} answers</div>
            </div>
            <div className="questionTextContainer">
                <Link to={`/answers/${question.id}`}>
                    <div className="questionText">{question.question}</div>
                </Link>
                <span className="questionDate"><span className="questionUser">{question.user}</span> {question.date}</span>
            </div>
        </div>
    ) 
}
export default Question;