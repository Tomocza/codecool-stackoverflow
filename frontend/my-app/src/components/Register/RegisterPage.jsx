import React, { useState, useEffect } from "react";
import { useNavigate, useOutletContext } from 'react-router-dom';
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";
import '../Login/Login.css';

export default function RegisterPage() {
  const SUBMIT_TEXT = "Register";
  const [registerLoading, setRegisterLoading] = useState(false);
  const [userId, setUserId] = useOutletContext();
  const [contentError, setContentError] = useState([]);
  const navigate = useNavigate();

  
  useEffect(() =>{
    document.body.classList.add('background');
    return function cleanup() {
      document.body.classList.remove('background');
    };
}, [])


  async function register(username, password) {
    const newContentError = [];
    if (!/.{1,}/.test(username)) {
      newContentError.push("Your username should be at least 1 character long");
    } else if (!/(?![^a-zA-Z\d]).{1,}/.test(username)) {
      newContentError.push("Your username should contain only alphanumeric characters");
    }
    if (!/[a-z]/.test(password)) {
      newContentError.push("You should add lowercase letter to your password");
    }
    if (!/[A-Z]/.test(password)) {
      newContentError.push("You should add uppercase letter to your password");
    }
    if (!/\d/.test(password)) {
      newContentError.push("You should add number to your password");
    }
    if (!/[$&@!?.:_#*+/\\€%ß^~,;-]/.test(password)) {
      newContentError.push("You should add a special character to your password");
    }
    if (!/.{8}/.test(password)) {
      newContentError.push("Your password should be at least 8 character long");
    }
    if (newContentError.length > 0) {
      setContentError(newContentError);
    } else {
      setContentError(newContentError);
      setRegisterLoading(true);
      try {
        const httpRawRes = await fetch("/users/", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ username, password }),
        });
        if (httpRawRes.ok) {
          const user = await httpRawRes.json();
          console.log(user);
          login(username, password);
        } else {
          setContentError(["You should choose a different username, because it's already in use"]);
        }
      } catch (error) {
        console.error(error);
      } finally {
        setRegisterLoading(false);
      }
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
      setUserId(user);
      navigate('/questions');
    } catch (error) {
      return console.error(error);
    } finally {
    }
  }
  return <UserNamePasswordForm contentError={contentError} onSubmit={register} submitText={SUBMIT_TEXT} disabled={registerLoading} />;
}
