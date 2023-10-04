import React, { useState } from "react";
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";

export default function LoginPage() {
  const SUBMIT_TEXT = "Login";
  const [loginLoading, setLoginLoading] = useState(false);

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
    } catch (error) {
      return console.error(error);
    } finally {
      setLoginLoading(false);
    }
  }
  return <UserNamePasswordForm onSubmit={login} submitText={SUBMIT_TEXT} disabled={loginLoading} />;
}
