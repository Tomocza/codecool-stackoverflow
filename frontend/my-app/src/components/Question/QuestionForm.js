import { useEffect } from "react";
import "./QuestionForm.css";
function QuestionForm(){
    useEffect(() =>{
        document.body.classList.add('background');
        return function cleanup() {
          document.body.classList.remove('background');
        };
    }, [])
    return(
        <div className="questionForm">
            <h2>Ask a public question</h2>
            <div className="titleContainer">
                <div>
                    <div className="">Title</div>
                    <div className="titleDescription">Be specific and imagine you’re asking a question to another person, or to Tomi!</div>
                    <input type="text" placeholder="e.g. Isn't this beautiful site astonishing?"></input>
                
                </div>
            </div>
            <div className="bodyContainer">
                <div>
                    <div className="">What are the details of your problem?</div>
                    <div className="titleDescription">Introduce the problem and expand on what you put in the title.</div>
                    <textarea></textarea>
                    <button>Post Your Question</button>
                </div>
            </div>
        </div>
    )
}

export default QuestionForm;