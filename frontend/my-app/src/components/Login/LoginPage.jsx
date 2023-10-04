import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import './Login.css'
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";

export default function LoginPage() {
  const SUBMIT_TEXT = "Log in";
  const [loginLoading, setLoginLoading] = useState(false);
  const navigate = useNavigate();
  
  useEffect(() =>{
    document.body.classList.add('background');
    return function cleanup() {
      document.body.classList.remove('background');
    };
}, [])


  async function login(username, password) {
    setLoginLoading(true);
    try {
      const httpRawRes = await fetch("/users/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      const user = await httpRawRes.json();
      console.log(user);
      navigate('/questions');
    } catch (error) {
      return console.error(error);
    } finally {
      setLoginLoading(false);
    }
  }
  return <UserNamePasswordForm onSubmit={login} submitText={SUBMIT_TEXT} disabled={loginLoading} />;
}
