import { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import "./QuestionForm.css";
function QuestionForm(){
    const [title, setTitle] = useState("");
    const [body, setBody] = useState("");
    const navigate = useNavigate();

    useEffect(() =>{
        document.body.classList.add('background');
        return function cleanup() {
          document.body.classList.remove('background');
        };
    }, [])

    function getTitle(e){
        setTitle(() => e.target.value);
    }

    function getBody(e){
        setBody(() => e.target.value);
    }

    async function submitPost(){
        const newPost = {
            title: title,
            body: body,
            userId: 2
        }
        const response = await fetch('/questions/',{
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newPost)
        });
        navigate('/questions');
      }
      function handleSubmit(){
        if (title.length >= 2 && body.length >= 5){
            submitPost();
        } else{
            console.log("That looks like a rather short message!");
        }
      }
    return(
        <div className="questionForm">
            <h2>Ask a public question</h2>
            <div className="titleContainer">
                <div>
                    <div className="">Title</div>
                    <div className="titleDescription">Be specific and imagine you’re asking a question to another person, or to Tomi!</div>
                    <input type="text" placeholder="e.g. Isn't this beautiful site astonishing?" onChange={(e) => getTitle(e)}></input>
                
                </div>
            </div>
            <div className="bodyContainer">
                <div>
                    <div className="">What are the details of your problem?</div>
                    <div className="titleDescription">Introduce the problem and expand on what you put in the title.</div>
                    <textarea onChange={(e) => getBody(e)}></textarea>
                    <button onClick={handleSubmit}>Post Your Question</button>
                </div>
            </div>
        </div>
    )
}

export default QuestionForm;