import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";

import NavBar from "./components/Layout/NavBar";
import QuestionList from "./components/Question/QuestionList";
import HomePage from "./components/HomePage/HomePage";
import QuestionForm from "./components/Question/QuestionForm";

import "./index.css";
import AnswerList from "./components/Answer/AnswerList";
const router = createBrowserRouter([
  {
    path: "/",
    element: <NavBar />,
    // errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <HomePage />,
      },
      {
        path: "/questions",
        element: <QuestionList />,
      },
      {
        path: "/answers/:id",
        element: <AnswerList />,
      },
      {
        path: "/question/ask",
        element: <QuestionForm />,
      }
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
