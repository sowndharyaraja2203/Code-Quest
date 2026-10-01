import { useEffect, useState } from "react";

function Leaderboard() {

  const [leaderboard, setLeaderboard] = useState([]);

  useEffect(() => {

    fetch("http://localhost:8080/api/leaderboard")
      .then((response) => response.json())
      .then((data) => {

        setLeaderboard(data);

      })
      .catch((error) => {

        console.error(
          "Error loading leaderboard:",
          error
        );

      });

  }, []);

  return (
    <div className="leaderboard-page">

      <div className="leaderboard-container">

        <h1>🏆 Leaderboard</h1>

        <div className="leaderboard-card">

          <div className="leaderboard-row header">

            <span>Rank</span>

            <span>User</span>

            <span>Problems Solved</span>

            <span>Score</span>

          </div>

          {leaderboard.map((user, index) => (

            <div
              className="leaderboard-row"
              key={index}
            >

              <span>
                {index + 1}
              </span>

              <span>
                {user.name}
              </span>

              <span>
                {user.problemsSolved}
              </span>

              <span>
                {user.score}
              </span>

            </div>

          ))}

        </div>

      </div>

    </div>
  );
}

export default Leaderboard;