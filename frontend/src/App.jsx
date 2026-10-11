import { HashRouter, Routes, Route, Link } from "react-router-dom";
import { useState } from "react";
import ProtectedRoute from "./ProtectedRoute";
import SubmissionHistory from "./pages/SubmissionHistory";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Problems from "./pages/Problems";
import ProblemDetails from "./pages/ProblemDetails";
import Dashboard from "./pages/Dashboard";
import Leaderboard from "./pages/Leaderboard";
import Profile from "./pages/Profile";

import "./App.css";


function Home() {

  const [isLoggedIn, setIsLoggedIn] = useState(
    !!localStorage.getItem("userName")
  );

  return (
    <>
      {/* Navbar */}
      <nav className="navbar">

        <div className="logo">
          Code<span>Quest</span>
        </div>
      <div className="nav-links">
      <Link to="/">Home</Link>
      <Link to="/problems">Problems</Link>
      <Link to="/profile">Profile</Link>
      {isLoggedIn ? (    <>
      <Link to="/dashboard">Dashboard</Link>
      <Link to="/submissions">Submission History</Link>
      <Link to="/leaderboard">Leaderboard</Link>
      <Link to="/login" onClick={() => {
    localStorage.removeItem("userName");
    localStorage.removeItem("userEmail");
    setIsLoggedIn(false);
  }}
>
  Logout
</Link>
    </>
  ) : (
    <>
      <Link to="/login">Login</Link>
      <Link to="/register">Register</Link>
    </>
  )}
</div>
      </nav>

      {/* Hero Section */}
      <section className="hero">

        <div className="hero-content">

          <p className="welcome">
            WELCOME TO CODEQUEST
          </p>

          <h1>
            Master Coding.
            <br />
            Build Your Skills.
          </h1>

          <p className="hero-description">
            Practice coding problems, improve your programming
            skills and track your progress with CodeQuest.
          </p>

          <div className="hero-buttons">

            <Link to="/problems" className="primary-btn">
              Start Coding
            </Link>

            <Link to="/problems" className="secondary-btn">
              Explore Problems
            </Link>

          </div>

        </div>


        {/* Code Card */}
        <div className="code-card">

          <div className="code-header">
            <span>●</span>
            <span>●</span>
            <span>●</span>
          </div>

          <pre>
{`function solveProblem() {
    let result = 0;

    for(let i = 0; i < n; i++) {
        result += arr[i];
    }

    return result;
}`}
          </pre>

        </div>

      </section>


      {/* Stats */}
      <section className="stats">

        <div>
          <h2>500+</h2>
          <p>Problems</p>
        </div>

        <div>
          <h2>6+</h2>
          <p>Topics</p>
        </div>

        <div>
          <h2>3</h2>
          <p>Difficulty Levels</p>
        </div>

        <div>
          <h2>🏆</h2>
          <p>Badges</p>
        </div>

      </section>


      {/* Features */}
      <section className="features" id="features">

        <h2>Why CodeQuest?</h2>

        <div className="feature-grid">

          <div className="feature-card">
            <h3>💻 Practice Problems</h3>
            <p>
              Solve problems from Arrays, Strings, Searching,
              Sorting, Mathematics and OOP.
            </p>
          </div>

          <div className="feature-card">
            <h3>⚡ Online Code Editor</h3>
            <p>
              Write and test your code directly in your browser.
            </p>
          </div>

          <div className="feature-card">
            <h3>📊 Track Progress</h3>
            <p>
              Track solved problems, scores, streaks and badges.
            </p>
          </div>

        </div>

      </section>


      {/* Footer */}
      <footer>
        <p>© 2026 CodeQuest. Learn. Code. Grow.</p>
      </footer>

    </>
  );
}


function App() {
  return (
    <HashRouter>

      <Routes>

        <Route path="/" element={<Home />} />

        <Route path="/login" element={<Login />} />

        <Route path="/register" element={<Register />} />

        <Route path="/problems" element={<ProtectedRoute><Problems /></ProtectedRoute>}/>
        <Route path="/problems/:id" element={<ProtectedRoute><ProblemDetails /></ProtectedRoute>}/>
        <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>}/>
        <Route path="/submissions" element={<ProtectedRoute><SubmissionHistory /></ProtectedRoute>}/>
        <Route path="/leaderboard" element={<ProtectedRoute><Leaderboard /></ProtectedRoute>}/>
        <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>}/>

      </Routes>

    </HashRouter>
  );
}
export default App;