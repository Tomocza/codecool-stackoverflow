import React, { useState } from "react";
import UserNamePasswordForm from "../UsernamePasswordForm/UserNamePasswordForm";

export default function RegisterPage() {
  const SUBMITTEXT = "Register";
  const [registerLoading, setRegisterLoading] = useState(false);

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
    } catch (error) {
      console.error(error);
    } finally {
      setRegisterLoading(false);
    }
  }
  return <UserNamePasswordForm onSubmit={register} submitText={SUBMITTEXT} disabled={registerLoading} />;
}
