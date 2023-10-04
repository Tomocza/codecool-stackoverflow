import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";
import '../Login/Login.css';

export default function RegisterPage() {
  const SUBMIT_TEXT = "Register";
  const [registerLoading, setRegisterLoading] = useState(false);
  const navigate = useNavigate();

  
  useEffect(() =>{
    document.body.classList.add('background');
    return function cleanup() {
      document.body.classList.remove('background');
    };
}, [])


  async function register(username, password) {
    setRegisterLoading(true);
    try {
      const httpRawRes = await fetch("/users/", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      const user = await httpRawRes.json();
      console.log(user);
      login(username, password);
    } catch (error) {
      console.error(error);
    } finally {
      setRegisterLoading(false);
    }
  }
    
  async function login(username, password) {
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
    }
  }
  return <UserNamePasswordForm onSubmit={register} submitText={SUBMIT_TEXT} disabled={registerLoading} />;
}
