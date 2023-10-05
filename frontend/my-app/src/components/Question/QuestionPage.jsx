import React, { useContext } from "react";
import { useState, useEffect } from "react";
import QuestionList from "./QuestionList";
import { QuestionContext } from "../Layout/NavBar";
import { BACKEND_ROOT } from '../../constants';

export default function QuestionPage() {
  const [questions, setQuestions] = useState([]);
  const [loading, setLoading] = useState(false);
  const questionContext = useContext(QuestionContext);

  useEffect(() => {
    try {
      setLoading(true);
      async function fetchQuestions() {
        const url = questionContext === "" ? `${BACKEND_ROOT}/questions/all` : `${BACKEND_ROOT}/questions/search/${questionContext}`;
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
  if (questions.length > 0) return <QuestionList questions={questions} loading={loading} />;
}
