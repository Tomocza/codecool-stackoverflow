import { Outlet, Link, useNavigate } from "react-router-dom";
import React, { createContext } from "react";
import "./NavBar.css";
import logoImg from "./stack-icon.svg";
import { useState } from "react";

export const QuestionContext = createContext("");
function NavBar() {
  const [userId, setUserId] = useState(null);
  const [searcher, setSearcher] = useState("");
  const navigate = useNavigate();

  function changeQuery(message) {
    setSearcher(message);
    navigate("/questions");
  }
  return (
    <div className="navbar">
      <nav>
        <ul>
          <li>
            <Link to="/">
              <button className="homePageButton">
                <img className="logo" src={logoImg}></img>
                <span className="mainLogoText">
                  stack <b>overflow</b>
                </span>
              </button>
            </Link>
          </li>
          <li>
            <Link to="/questions">
              <button type="button">Questions</button>
            </Link>
          </li>
          <li>
            <input
              value={searcher}
              type="text"
              placeholder="Search"
              onChange={(e) => changeQuery(e.target.value)}
            ></input>
          </li>
          <li>
            <Link to="/login">
              <button type="button" className="buttonLogin">
                Log in
              </button>
            </Link>
          </li>
          <li>
            <Link to="/register">
              <button type="button" className="buttonSignup">
                Sign up
              </button>
            </Link>
          </li>
        </ul>
      </nav>
      <QuestionContext.Provider value={searcher}>
        <Outlet context={[userId, setUserId]} />
      </QuestionContext.Provider>
    </div>
  );
}
export default NavBar;
