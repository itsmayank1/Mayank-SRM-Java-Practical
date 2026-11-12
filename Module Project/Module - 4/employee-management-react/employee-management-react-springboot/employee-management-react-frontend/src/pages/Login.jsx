import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useApp();
  const navigate = useNavigate();

  const handleSignIn = async (userVal, passVal) => {
    try {
      setLoading(true);
      setError("");
      await login(userVal, passVal);
      navigate("/dashboard", { replace: true });
    } catch (err) {
      setError(err.message || "Invalid credentials. Please verify username and password.");
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    handleSignIn(username, password);
  };

  const handleQuickLogin = (quickUser, quickPass) => {
    setUsername(quickUser);
    setPassword(quickPass);
    handleSignIn(quickUser, quickPass);
  };

  return (
    <main className="login-page">
      <section className="login-panel">
        <div className="login-header-group">
          <div className="login-mark">EMS</div>
          <span className="login-tag">SRM-IST FULL STACK</span>
        </div>

        <p className="eyebrow">MODULE - 4 PRACTICAL ASSESSMENT</p>
        <h1>Welcome Back</h1>
        <p className="muted">Sign in to the Employee Management System React SPA.</p>

        {error && (
          <div className="alert alert-error" role="alert">
            ✕ {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="form-stack">
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              autoComplete="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="e.g. hr, manager, employee"
              required
            />
          </div>

          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              required
            />
          </div>

          <button className="button button-primary button-full" type="submit" disabled={loading}>
            {loading ? "Authenticating..." : "Sign In to Workspace →"}
          </button>
        </form>

        {/* Quick Demo Login Cards */}
        <div className="quick-login-section">
          <p className="quick-login-title">⚡ Instant Demo Sign-In (Click to Test):</p>
          <div className="demo-accounts-grid">
            <button
              type="button"
              className="demo-chip chip-hr"
              onClick={() => handleQuickLogin("hr", "1234")}
              title="Sign in with Full Administrative & CRUD Privileges"
            >
              <div className="chip-badge">HR</div>
              <div className="chip-info">
                <strong>hr / 1234</strong>
                <small>Full Admin & CRUD</small>
              </div>
            </button>

            <button
              type="button"
              className="demo-chip chip-manager"
              onClick={() => handleQuickLogin("manager", "1234")}
              title="Sign in with Managerial Privileges"
            >
              <div className="chip-badge">MGR</div>
              <div className="chip-info">
                <strong>manager / 1234</strong>
                <small>View & Edit</small>
              </div>
            </button>

            <button
              type="button"
              className="demo-chip chip-employee"
              onClick={() => handleQuickLogin("employee", "1234")}
              title="Sign in with Standard Read-Only Privileges"
            >
              <div className="chip-badge">EMP</div>
              <div className="chip-info">
                <strong>employee / 1234</strong>
                <small>Read-Only Directory</small>
              </div>
            </button>
          </div>
        </div>

        <p className="login-note">
          Session-verified JWT authentication via Spring Boot REST backend with instant fallback.
        </p>
      </section>

      {/* Modern Gradient Showcase Aside */}
      <aside className="login-aside">
        <div className="aside-orb orb-one" />
        <div className="aside-orb orb-two" />
        <div className="aside-content">
          <span className="aside-tag">SRM-IST CSE CORE PRACTICAL</span>
          <h2>
            Modern Full Stack<br />Employee Operations.
          </h2>
          <p>
            An enterprise Single Page Application built with <strong>React 19</strong>, <strong>Vite</strong>, and <strong>Spring Boot REST</strong> backend architecture.
          </p>

          <div className="aside-features">
            <div className="aside-feat-item">
              <span className="feat-bullet">✦</span>
              <div>
                <strong>Role-Based Access Control</strong>
                <p>HR Admin, Manager, and Staff user permission tiers.</p>
              </div>
            </div>
            <div className="aside-feat-item">
              <span className="feat-bullet">✦</span>
              <div>
                <strong>Relational Data Model</strong>
                <p>Staff members linked to operational departments with payroll analytics.</p>
              </div>
            </div>
            <div className="aside-feat-item">
              <span className="feat-bullet">✦</span>
              <div>
                <strong>Vite SPA & REST API</strong>
                <p>Fast client routing with seamless proxy to Spring Boot port 8080.</p>
              </div>
            </div>
          </div>

          <div className="aside-metrics">
            <div>
              <strong>Mayank Upadhyay</strong>
              <span>Student / Developer</span>
            </div>
            <div>
              <strong>Module - 4</strong>
              <span>React Front End</span>
            </div>
            <div>
              <strong>SRM-IST</strong>
              <span>Java Full Stack</span>
            </div>
          </div>
        </div>
      </aside>
    </main>
  );
}
