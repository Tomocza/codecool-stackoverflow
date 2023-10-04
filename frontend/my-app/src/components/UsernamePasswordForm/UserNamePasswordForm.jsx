import React, { useEffect, useState } from "react";
import '../Login/Login.css';
import logoImg from "../Layout/stack-icon.svg";

export default function UserNamePasswordForm({ submitText, onSubmit, disabled }) {
  const [username, setUsername] = useState("");
  const [pwd, setPwd] = useState("");
  function handleSubmit(e) {
    e.preventDefault();
    onSubmit(username, pwd);
  }
  return (
    <div>
      <img className="loginLogo" src={logoImg}></img>
      <div className="loginContainer">
      <form className="loginForm" onSubmit={handleSubmit}>
        <div>
          {/* <h2>Log in!</h2> */}
          <label htmlFor="username">Username:</label><br/>
          <input type="text" id="username" name="username" onChange={(e) => setUsername(e.target.value)} /><br/>
          <label htmlFor="pwd">Password:</label><br/>
          <input type="password" id="pwd" name="pwd" onChange={(e) => setPwd(e.target.value)} /><br/>
          <button type="submit" disabled={disabled}>
            {submitText}
          </button>
        </div>
      </form>
      </div>
    </div>
  );
}
