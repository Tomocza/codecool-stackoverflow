import { Outlet, Link, useNavigate, useOutletContext } from "react-router-dom";
import React, { createContext } from "react";
import "./NavBar.css";
import logoImg from "./stack-icon.svg";
import { useEffect, useState } from "react";
import { BACKEND_ROOT } from '../../constants';

export const QuestionContext = createContext("");
function NavBar() {
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);
  const [searcher, setSearcher] = useState("");

  useEffect(() => {
    try {
      setLoading(true);
      async function getUser() {
        const httpRawRes = await fetch(`${BACKEND_ROOT}/users/session`);
        const res = await httpRawRes.json();
        if (Number.isInteger(res) && res != -1){
          setUserId(res);
          navigate("/questions");
        } else {
          setUserId(null);
        }
      }
      getUser();
    } catch (error) {
      return console.error(error);
    } finally {
      setLoading(false);
    }

  }, []);

  async function logout() {
    setLoading(true);
    try {
      const httpRawRes = await fetch(`${BACKEND_ROOT}/users/logout`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      // const res = await httpRawRes.json();
      setUserId(null);
      navigate("/login");
    } catch (error) {
      return console.error(error);
    } finally {
      setLoading(false);
    }
  }

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
          {userId == null ?
            <>
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
            </>
            : <li>
              <button type="button" className="buttonLogin" onClick={logout}>
                Logout
              </button>
            </li>
          }
        </ul>
      </nav>
      <QuestionContext.Provider value={searcher}>
        <Outlet context={[userId, setUserId]} />
      </QuestionContext.Provider>
    </div>
  );
}
export default NavBar;
