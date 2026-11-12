import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";

export default function Layout() {
  const { user, logout, backendOnline, toast } = useApp();
  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout();
    navigate("/login", { replace: true });
  };

  return (
    <div className="app-shell">
      {/* Toast Notification Container */}
      {toast && (
        <div className={`toast-notification toast-${toast.type}`} role="alert">
          <span className="toast-icon">
            {toast.type === "success" ? "✓" : toast.type === "error" ? "✕" : "ℹ"}
          </span>
          <span className="toast-message">{toast.message}</span>
        </div>
      )}

      <header className="topbar">
        <NavLink className="brand" to="/dashboard">
          <span className="brand-badge">EMS</span>
          <span className="brand-title">Employee Management</span>
          <span className="brand-tag">React SPA</span>
        </NavLink>

        <nav className="nav-links">
          <NavLink to="/dashboard" className={({ isActive }) => (isActive ? "active" : "")}>
            Dashboard
          </NavLink>
          <NavLink to="/employees" className={({ isActive }) => (isActive ? "active" : "")}>
            Employees
          </NavLink>
          <NavLink to="/departments" className={({ isActive }) => (isActive ? "active" : "")}>
            Departments
          </NavLink>
        </nav>

        <div className="header-meta">
          <span className={`status-badge ${backendOnline ? "online" : "offline"}`} title={backendOnline ? "Connected to Spring Boot REST Backend" : "Running with Local Client State"}>
            <span className="status-dot" />
            {backendOnline ? "Spring Boot API" : "Standalone SPA"}
          </span>

          <div className="user-area">
            <div className="user-info">
              <span className="user-name">{user?.username}</span>
              <span className={`role-pill role-${user?.role?.toLowerCase()}`}>{user?.role}</span>
            </div>
            <button className="button button-light button-small" onClick={handleLogout} title="Sign out">
              Logout
            </button>
          </div>
        </div>
      </header>

      <main className="main-content">
        <Outlet />
      </main>

      <footer className="footer">
        <div className="footer-content">
          <span><strong>Employee Management System</strong> — Module 4 React Single Page Application (SPA)</span>
          <span className="footer-divider">•</span>
          <span>SRM Institute of Science and Technology (CSE Core Practical)</span>
          <span className="footer-divider">•</span>
          <span>Mayank Upadhyay</span>
        </div>
      </footer>
    </div>
  );
}
