
import { useState } from "react";
import { Link, useParams } from "react-router-dom";

const problemData = {
  1: {
    id: 1,
    title: "Two Sum",
    topic: "Arrays",
    difficulty: "Easy",
    description:
      "Given an array of integers and a target value, find two numbers whose sum is equal to the target.",
    input:
      "An array of integers and an integer target.",
    output:
      "Return the indices of the two numbers whose sum equals the target.",
    exampleInput: "[2, 7, 11, 15], target = 9",
    exampleOutput: "[0, 1]",
    constraints:
      "2 ≤ array length ≤ 10⁴",
  },

  2: {
    id: 2,
    title: "Reverse a String",
    topic: "Strings",
    difficulty: "Easy",
    description:
      "Given a string, reverse the characters in the string and return the reversed string.",
    input:
      "A string containing characters.",
    output:
      "Return the reversed string.",
    exampleInput: "hello",
    exampleOutput: "olleh",
    constraints:
      "1 ≤ string length ≤ 10⁴",
  },

  3: {
    id: 3,
    title: "Binary Search",
    topic: "Searching",
    difficulty: "Medium",
    description:
      "Given a sorted array and a target value, find the position of the target using binary search.",
    input:
      "A sorted array of integers and a target value.",
    output:
      "Return the index of the target. Return -1 if it is not found.",
    exampleInput: "[1, 3, 5, 7, 9], target = 7",
    exampleOutput: "3",
    constraints:
      "1 ≤ array length ≤ 10⁵",
  },

  4: {
    id: 4,
    title: "Palindrome Check",
    topic: "Strings",
    difficulty: "Easy",
    description:
      "Given a string, check whether the string reads the same forward and backward.",
    input:
      "A string containing characters.",
    output:
      "Return true if the string is a palindrome, otherwise return false.",
    exampleInput: "madam",
    exampleOutput: "true",
    constraints:
      "1 ≤ string length ≤ 10⁴",
  },
  5: {
  title: "Find Maximum Element",
  topic: "Arrays",
  difficulty: "Easy",
  description:
    "Given an array of integers, find the maximum element in the array.",
  input:
    "An array of integers.",
  output:
    "Return the maximum element in the array.",
  exampleInput: "10 25 7 40 15",
  exampleOutput: "40",
  constraints:
    "1 ≤ array length ≤ 10⁴",
  },
  6: {
  title: "Count Vowels",
  topic: "Strings",
  difficulty: "Easy",
  description:
    "Given a string, count the number of vowels present in the string.",
  input:
    "A string containing characters.",
  output:
    "Print the number of vowels in the string.",
  exampleInput: "hello",
  exampleOutput: "2",
  constraints:
    "1 ≤ string length ≤ 10⁴",
  },
  7: {
  title: "Find Duplicate Elements",
  topic: "Arrays",
  difficulty: "Medium",
  description:
    "Given an array of integers containing one duplicate element, find and print the duplicate element.",
  input:
    "An array of integers.",
  output:
    "Print the duplicate element.",
  exampleInput: "1 3 4 2 2",
  exampleOutput: "2",
  constraints:
    "2 ≤ array length ≤ 10⁴",
  },
};
function ProblemDetails() {

  const { id } = useParams();

  const problem = problemData[id] || problemData[1];
  console.log("URL ID =", id);
  console.log("Problem ID =", problem.id);

  const [language, setLanguage] = useState("Java");

  const [code, setCode] = useState(
`public class Solution {

    public static void main(String[] args) {

        // Write your solution here

    }
}`
  );

  const [result, setResult] = useState("");

  // Run Code
const runCode = async () => {
  setResult("Running code...");

  try {
    const response = await fetch(
      "https://codequest-backend-0ayl.onrender.com/api/code/run",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
      body: JSON.stringify({
      problemId: Number(id),
      language: language,
      code: code,
      userEmail: localStorage.getItem("userEmail")
    })
      }
    );

    const data = await response.text();

    setResult(data);

  } catch (error) {
    console.error(error);

    setResult(
      "❌ Unable to connect to backend. Make sure Spring Boot is running."
    );
  }
};
  // Submit Code
 const submitCode = async () => {
  setResult("Submitting code...");

  try {
    const response = await fetch(
      "https://codequest-backend-0ayl.onrender.com/api/code/submit",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          problemId: Number(id),
          language: language,
          code: code,
          userEmail: localStorage.getItem("userEmail")
        }),
      }
    );

    const data = await response.text();

    setResult(data);

  } catch (error) {
    setResult(
      "❌ Unable to connect to backend. Make sure Spring Boot is running."
    );
  }
};

  return (
    <div className="problem-details-page">

      {/* Top Navigation */}

      <div className="problem-details-top">

        <Link
          to="/problems"
          className="back-problems"
        >
          ← Back to Problems
        </Link>

        <span
          className={`difficulty ${problem.difficulty.toLowerCase()}`}
        >
          {problem.difficulty}
        </span>

      </div>


      {/* Problem Information */}

      <div className="problem-details-container">

        <div className="problem-description">

          <h1>{problem.title}</h1>

          <span className="problem-topic">
            {problem.topic}
          </span>


          <h2>Problem Description</h2>

          <p>
            {problem.description}
          </p>


          <h2>Input</h2>

          <p>
            {problem.input}
          </p>


          <h2>Output</h2>

          <p>
            {problem.output}
          </p>


          <h2>Example</h2>

          <div className="example-box">

            <p>
              <strong>Input:</strong>
            </p>

            <code>
              {problem.exampleInput}
            </code>

            <p>
              <strong>Output:</strong>
            </p>

            <code>
              {problem.exampleOutput}
            </code>

          </div>


          <h2>Constraints</h2>

          <p>
            {problem.constraints}
          </p>

        </div>


        {/* Code Editor */}

        <div className="editor-section">

          <div className="editor-header">

            <select
              value={language}
              onChange={(e) => setLanguage(e.target.value)}
            >

              <option>Java</option>
              <option>Python</option>
              <option>JavaScript</option>
              <option>C++</option>

            </select>

          </div>


          <textarea
            className="code-editor"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            spellCheck="false"
          />


          <div className="editor-buttons">

            <button
              className="run-btn"
              onClick={runCode}
            >
              ▶ Run Code
            </button>


            <button
              className="submit-btn"
              onClick={submitCode}
            >
              Submit
            </button>

          </div>


          {result && (
            <div className="result-box">
              {result}
            </div>
          )}

        </div>

      </div>

    </div>
  );
}

export default ProblemDetails;

