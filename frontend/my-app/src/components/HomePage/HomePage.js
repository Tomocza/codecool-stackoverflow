import {Link} from 'react-router-dom';
import "./HomePage.css";
function HomePage(){
    return (
        <div className="homePage">
            <div className="homePageOptions">
                <div className="option1">
                    <div className="description">Find the best answer to your technical question, help others answer theirs</div>
                    <Link to="register">
                        <button className="option1Button">Join the Community</button>
                    </Link>
                </div>
                <div className="option2">
                    <div className="description">Browse the biggest collection of tech related questions</div>
                    <Link to="/questions">
                        <button className="option2Button">Questions</button>
                    </Link>
                    
                </div>
            </div>
            <h1 className="main">Every <span style={{color: "rgb(246, 116, 13)"}}>developer</span> has a <br/> tab open to Stack Overflow</h1>
        </div>
    )
}
export default HomePage;
