import { useEffect, useState } from "react";

function SubmissionHistory() {

  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
  const userEmail = localStorage.getItem("userEmail");

  fetch(
    `http://localhost:8080/api/submissions/user/${userEmail}`
  )
      .then((response) => response.json())
      .then((data) => {
        setSubmissions(data);
      })
      .catch((error) => {
        console.error("Error loading submissions:", error);
      });

  }, []);

 const getProblemName = (problemId) => {

  const problems = {
    1: "Two Sum",
    2: "Reverse a String",
    3: "Binary Search",
    4: "Palindrome Check",
    5: "Find Maximum Element",
    6: "Count Vowels",
    7: "Find Duplicate Elements",
  };

  return problems[problemId] || `Problem ${problemId}`;
};

  const formatDate = (date) => {

    if (!date) {
      return "Date not available";
    }

    return new Date(date).toLocaleString();
  };

  return (
    <div className="submission-page">

      <div className="submission-container">

        <h1>Submission History</h1>

        <p>
          View your previous coding submissions.
        </p>

        {submissions.length > 0 ? (

          submissions.map((submission) => (

            <div
              className="submission-card"
              key={submission.id}
            >

              <h2>
                {getProblemName(submission.problemId)}
              </h2>

              <p>
                Language: {submission.language}
              </p>

              <p>
                Status:{" "}
                <strong>
                  {submission.status}
                </strong>
              </p>

              <p>
                Score: {submission.score}
              </p>

              <p>
                Submitted: {formatDate(submission.submittedAt)}
              </p>

            </div>

          ))

        ) : (

          <p>
            No submissions yet.
          </p>

        )}

      </div>

    </div>
  );
}

export default SubmissionHistory;