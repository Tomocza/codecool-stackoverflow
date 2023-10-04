import {Link} from 'react-router-dom';
import { useState } from 'react';
import "../../App.css"
import DateFormatter from '../Utilities/DateFormatter';
function Question({question}){
    return(
        <div className="question">
            {/* {console.log(question)} */}
            <div className="questionData">
                <div className="questionVotes">{question?.rating} votes</div>
                <div className="questionAnswers">{question.answerCount} answers</div>
            </div>
            <div className="questionTextContainer">
                <Link to={`/answers/${question.id}`}>
                    <div className="questionText">{question.title}</div>
                </Link>
                <span className="questionDate">
                    <span className="questionUser">{question.userName}</span> 
                    <DateFormatter date={question.createdAt}/>
                </span>
            </div>
        </div>
    ) 
}
export default Question;