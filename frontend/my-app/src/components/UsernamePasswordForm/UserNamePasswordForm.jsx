import React, { useEffect, useState } from "react";

export default function UserNamePasswordForm({ submitText, onSubmit, disabled }) {
  const [username, setUsername] = useState("");
  const [pwd, setPwd] = useState("");
  function handleSubmit(e) {
    e.preventDefault();
    onSubmit(username, pwd);
  }
  return (
    <form className="loginForm" onSubmit={handleSubmit}>
      <div>
        <h2>Log in!</h2>
        <label htmlFor="username">Username:</label>
        <input type="text" id="username" name="username" onChange={(e) => setUsername(e.target.value)} />
        <label htmlFor="pwd">Password:</label>
        <input type="password" id="pwd" name="pwd" onChange={(e) => setPwd(e.target.value)} />
        <button type="submit" disabled={disabled}>
          {submitText}
        </button>
      </div>
    </form>
  );
}
