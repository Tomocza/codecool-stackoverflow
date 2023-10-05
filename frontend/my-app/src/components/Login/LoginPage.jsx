import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useOutletContext } from "react-router-dom";
import "./Login.css";
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";
import { BACKEND_ROOT } from '../../constants';

export default function LoginPage() {
  const SUBMIT_TEXT = "Log in";
  const [loginLoading, setLoginLoading] = useState(false);
  const navigate = useNavigate();
  const [userId, setUserId] = useOutletContext();

  useEffect(() => {
    document.body.classList.add("background");
    return function cleanup() {
      document.body.classList.remove("background");
    };
  }, []);

  async function login(username, password) {
    setLoginLoading(true);
    try {
      const httpRawRes = await fetch(`${BACKEND_ROOT}/users/login`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ username, password }),
      });
      console.log("login" + httpRawRes);
      if (httpRawRes.status === 200) {
        const res = await httpRawRes.json();
        console.log(res);
        if (res !== -1) {
          setUserId(res);
          navigate("/questions");
        }
      }
    } catch (error) {
      return console.error(error);
    } finally {
      setLoginLoading(false);
    }
  }
  return <UserNamePasswordForm onSubmit={login} submitText={SUBMIT_TEXT} disabled={loginLoading} />;
}
