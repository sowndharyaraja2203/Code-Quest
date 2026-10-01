import { useState } from "react";
import { Link } from "react-router-dom";

function Problems() {

  const [search, setSearch] = useState("");
  const [difficulty, setDifficulty] = useState("All");
  const [topic, setTopic] = useState("All");

  const problems = [
    {
      id: 1,
      title: "Two Sum",
      topic: "Arrays",
      difficulty: "Easy",
      description: "Find two numbers in an array whose sum equals the target."
    },
    {
      id: 2,
      title: "Reverse a String",
      topic: "Strings",
      difficulty: "Easy",
      description: "Reverse the given string and print the result."
    },
    {
      id: 3,
      title: "Binary Search",
      topic: "Searching",
      difficulty: "Medium",
      description: "Find the position of a target element using binary search."
    },
    {
      id: 4,
      title: "Palindrome Check",
      topic: "Strings",
      difficulty: "Easy",
      description: "Check whether a given string reads the same forward and backward."
    },
    {
    id: 5,
    title: "Find Maximum Element",
    topic: "Arrays",
    difficulty: "Easy",
    description: "Find the maximum element in an array."
    },
    {
    id: 6,
    title: "Count Vowels",
    topic: "Strings",
    difficulty: "Easy",
    description: "Count the number of vowels in the given string."
    },
    {
    id: 7,
    title: "Find Duplicate Elements",
    topic: "Arrays",
    difficulty: "Medium",
    description: "Find the duplicate element in the given array."
  },
  ];

  const filteredProblems = problems.filter((problem) => {

    const matchesSearch =
      problem.title
        .toLowerCase()
        .includes(search.toLowerCase());

    const matchesDifficulty =
      difficulty === "All" ||
      problem.difficulty === difficulty;

    const matchesTopic =
      topic === "All" ||
      problem.topic === topic;

    return matchesSearch && matchesDifficulty && matchesTopic;
  });

  return (
    <div className="problems-page">

      <div className="problems-container">

        <div className="problems-header">

          <h1>Coding Problems</h1>

          <p>
            Practice coding problems and improve your programming skills.
          </p>

        </div>

        <div className="problem-filters">

          <input
            type="text"
            placeholder="Search problems..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />

          <select
            value={difficulty}
            onChange={(e) => setDifficulty(e.target.value)}
          >
            <option value="All">All Difficulties</option>
            <option value="Easy">Easy</option>
            <option value="Medium">Medium</option>
            <option value="Hard">Hard</option>
          </select>

          <select
            value={topic}
            onChange={(e) => setTopic(e.target.value)}
          >
            <option value="All">All Topics</option>
            <option value="Arrays">Arrays</option>
            <option value="Strings">Strings</option>
            <option value="Searching">Searching</option>
          </select>

        </div>

        <div className="problems-list">

          {filteredProblems.length > 0 ? (

            filteredProblems.map((problem) => (

              <div
                className="problem-card"
                key={problem.id}
              >

                <div className="problem-info">

                  <h2>{problem.title}</h2>

                  <p>
                    {problem.description}
                  </p>

                  <span>
                    Topic: {problem.topic}
                  </span>

                </div>

                <span
                  className={`difficulty ${problem.difficulty.toLowerCase()}`}
                >
                  {problem.difficulty}
                </span>

                <Link
                  to={`/problems/${problem.id}`}
                  className="solve-button"
                >
                  Solve →
                </Link>

              </div>

            ))

          ) : (

            <p className="no-problems">
              No problems found.
            </p>

          )}

        </div>

      </div>

    </div>
  );
}

export default Problems;

