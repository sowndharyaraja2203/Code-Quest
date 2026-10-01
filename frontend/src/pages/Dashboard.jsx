import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Dashboard() {

  const [stats, setStats] = useState({
    problemsSolved: 0,
    totalScore: 0,
    currentStreak: 0,
    easy: 0,
    medium: 0,
    hard: 0
  });

  const userName =
    localStorage.getItem("userName") || "User";

  useEffect(() => {

    const userEmail =
      localStorage.getItem("userEmail");

    fetch(
      `http://localhost:8080/api/dashboard/${userEmail}`
    )
      .then((response) => response.json())
      .then((data) => {
        setStats(data);
      })
      .catch((error) => {
        console.error(
          "Error loading dashboard:",
          error
        );
      });

  }, []);

  return (

    <div className="dashboard-page">

      <div className="dashboard-container">

        {/* Welcome Section */}

        <div className="dashboard-welcome">

          <div>
            <p className="dashboard-small-title">
              CODEQUEST DASHBOARD
            </p>

            <h1>
              Welcome, {userName} 👋
            </h1>

            <p>
              Keep learning, keep coding,
              and build your skills.
            </p>
          </div>

          <Link
            to="/problems"
            className="dashboard-start-btn"
          >
            Start Coding →
          </Link>

        </div>


        {/* Main Statistics */}

        <div className="dashboard-stats">

          <div className="dashboard-card">

            <div className="dashboard-icon">
              💻
            </div>

            <div>
              <h3>{stats.problemsSolved}</h3>
              <p>Problems Solved</p>
            </div>

          </div>


          <div className="dashboard-card">

            <div className="dashboard-icon">
              🏆
            </div>

            <div>
              <h3>{stats.totalScore}</h3>
              <p>Total Score</p>
            </div>

          </div>


          <div className="dashboard-card">

            <div className="dashboard-icon">
              🔥
            </div>

            <div>
              <h3>{stats.currentStreak}</h3>
              <p>Current Streak</p>
            </div>

          </div>

        </div>


        {/* Difficulty Section */}

        <div className="difficulty-section">

          <h2>Problem Progress</h2>

          <div className="difficulty-grid">

            <div className="difficulty-card easy-card">

              <div className="difficulty-icon">
                🟢
              </div>

              <h3>{stats.easy}</h3>

              <p>Easy Problems</p>

            </div>


            <div className="difficulty-card medium-card">

              <div className="difficulty-icon">
                🟡
              </div>

              <h3>{stats.medium}</h3>

              <p>Medium Problems</p>

            </div>


            <div className="difficulty-card hard-card">

              <div className="difficulty-icon">
                🔴
              </div>

              <h3>{stats.hard}</h3>

              <p>Hard Problems</p>

            </div>

          </div>

        </div>


        {/* Quick Actions */}

        <div className="quick-section">

          <h2>Quick Actions</h2>

          <div className="quick-grid">

            <Link
              to="/problems"
              className="quick-card"
            >
              <span>💻</span>
              <h3>Solve Problems</h3>
              <p>
                Practice coding challenges
              </p>
            </Link>


            <Link
              to="/submissions"
              className="quick-card"
            >
              <span>📜</span>
              <h3>Submission History</h3>
              <p>
                View your previous submissions
              </p>
            </Link>


            <Link
              to="/leaderboard"
              className="quick-card"
            >
              <span>🏆</span>
              <h3>Leaderboard</h3>
              <p>
                View coding scores
              </p>
            </Link>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Dashboard;