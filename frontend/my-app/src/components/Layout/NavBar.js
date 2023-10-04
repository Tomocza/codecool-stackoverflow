import { Outlet, Link } from "react-router-dom";
import "./NavBar.css";
import logoImg from "./stack-icon.svg";

function NavBar() {
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
          {/* <li>
        <button type="button">Products</button>
        </li> */}
          <li>
            <input type="text" placeholder="Search"></input>
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
      <Outlet />
    </div>
  );
}
export default NavBar;
