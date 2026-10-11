import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function Profile() {
  const [stats, setStats] = useState({
    problemsSolved: 0,
    totalScore: 0,
    currentStreak: 0,
    easy: 0,
    medium: 0,
    hard: 0,
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const userName =
    localStorage.getItem("userName") || "User";

  const userEmail =
    localStorage.getItem("userEmail") || "";

  useEffect(() => {
    if (!userEmail) {
      setError("Please log in to view your profile.");
      setLoading(false);
      return;
    }

    fetch(
      `https://codequest-backend-0ayl.onrender.com/api/dashboard/${encodeURIComponent(userEmail)}`
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Unable to load profile statistics.");
        }

        return response.json();
      })
      .then((data) => {
        setStats((previousStats) => ({
          ...previousStats,
          ...data,
        }));
      })
      .catch((error) => {
        console.error("Error loading profile:", error);
        setError(
          "Unable to load your statistics. Please try again later."
        );
      })
      .finally(() => {
        setLoading(false);
      });
  }, [userEmail]);

  return (
    <div className="profile-page">
      <div className="profile-container">
        {/* Profile Header */}
        <div className="profile-header">
          <div className="profile-avatar">
            {userName.charAt(0).toUpperCase()}
          </div>

          <div>
            <p className="profile-label">
              CODEQUEST PROFILE
            </p>

            <h1>{userName}</h1>
            <p>{userEmail}</p>
          </div>
        </div>

        {loading && (
          <p>Loading your profile...</p>
        )}

        {error && (
          <p role="alert">{error}</p>
        )}

        {/* Statistics */}
        <div className="profile-stats">
          <div className="profile-stat-card">
            <span>💻</span>
            <h2>{stats.problemsSolved}</h2>
            <p>Problems Solved</p>
          </div>

          <div className="profile-stat-card">
            <span>🏆</span>
            <h2>{stats.totalScore}</h2>
            <p>Total Score</p>
          </div>

          <div className="profile-stat-card">
            <span>🔥</span>
            <h2>{stats.currentStreak}</h2>
            <p>Current Streak</p>
          </div>
        </div>

        {/* Difficulty Progress */}
        <div className="profile-progress">
          <h2>Your Progress</h2>

          <div className="progress-item">
            <div>
              <span>Easy</span>
              <strong>{stats.easy}</strong>
            </div>

            <div className="progress-bar">
              <div
                className="progress-easy"
                style={{
                  width: `${Math.min(stats.easy * 10, 100)}%`,
                }}
              />
            </div>
          </div>

          <div className="progress-item">
            <div>
              <span>Medium</span>
              <strong>{stats.medium}</strong>
            </div>

            <div className="progress-bar">
              <div
                className="progress-medium"
                style={{
                  width: `${Math.min(stats.medium * 10, 100)}%`,
                }}
              />
            </div>
          </div>

          <div className="progress-item">
            <div>
              <span>Hard</span>
              <strong>{stats.hard}</strong>
            </div>

            <div className="progress-bar">
              <div
                className="progress-hard"
                style={{
                  width: `${Math.min(stats.hard * 10, 100)}%`,
                }}
              />
            </div>
          </div>
        </div>

        {/* Action Buttons */}
        <div className="profile-actions">
          <Link
            to="/problems"
            className="profile-btn primary-profile-btn"
          >
            Start Coding →
          </Link>

          <Link
            to="/submissions"
            className="profile-btn secondary-profile-btn"
          >
            View Submissions
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Profile;