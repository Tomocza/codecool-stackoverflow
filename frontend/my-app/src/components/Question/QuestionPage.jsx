import React, { useContext } from "react";
import { useState, useEffect } from "react";
import QuestionList from "./QuestionList";
import { QuestionContext } from "../Layout/NavBar";

export default function QuestionPage() {
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(false);
  const questionContext = useContext(QuestionContext);

  useEffect(() => {
    try {
      setLoading(true);
      async function fetchQuestions() {
        const url = questionContext === "" ? "/questions/all" : `/questions/search/${questionContext}`;
        const response = await fetch(url);
        const newQuestions = await response.json();
        console.log(newQuestions);
        setQuestions(newQuestions);
      }
      fetchQuestions();
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  }, [questionContext]);
  return <QuestionList questions={questions} loading={loading} />;
}
