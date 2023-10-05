import { Outlet, Link, useNavigate, useOutletContext } from "react-router-dom";
import "./NavBar.css";
import logoImg from "./stack-icon.svg";
import { useState } from "react";

function NavBar() {
  const [loading, setLoading] = useState();
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);

  async function logout(){   
    setLoading(true);
    try {
      const httpRawRes = await fetch("/users/logout", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      // const res = await httpRawRes.json();
      // console.log(res);
      setUserId(null);
      navigate("/login");
    } catch (error) {
      return console.error(error);
    } finally {
      setLoading(false);
    }
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
            <input type="text" placeholder="Search"></input>
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
      <Outlet context={[userId, setUserId]} />
    </div>
  );
}
export default NavBar;
