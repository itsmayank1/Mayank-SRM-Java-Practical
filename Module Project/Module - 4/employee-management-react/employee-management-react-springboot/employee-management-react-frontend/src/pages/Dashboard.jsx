import { useMemo } from "react";
import { Link } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";
import { api } from "../services/api.js";
import StatCard from "../components/StatCard.jsx";

export default function Dashboard() {
  const { user, employees, departments, activityLog, backendOnline } = useApp();

  const metrics = useMemo(() => {
    const totalEmployees = employees.length;
    const totalDepartments = departments.length;
    const totalPayroll = employees.reduce((sum, e) => sum + Number(e.salary || 0), 0);
    const avgSalary = totalEmployees > 0 ? Math.round(totalPayroll / totalEmployees) : 0;

    const salaries = employees.map((e) => Number(e.salary || 0)).filter((s) => s > 0);
    const minSalary = salaries.length > 0 ? Math.min(...salaries) : 0;
    const maxSalary = salaries.length > 0 ? Math.max(...salaries) : 0;

    // Department breakdown
    const deptDistribution = departments.map((dept) => {
      const count = employees.filter((e) => Number(e.departmentId) === dept.id).length;
      const deptPayroll = employees
        .filter((e) => Number(e.departmentId) === dept.id)
        .reduce((sum, e) => sum + Number(e.salary || 0), 0);
      const percentage = totalEmployees > 0 ? Math.round((count / totalEmployees) * 100) : 0;
      return { ...dept, count, deptPayroll, percentage };
    });

    // Salary tiers
    const tiers = {
      entry: employees.filter((e) => Number(e.salary) < 60000).length,
      mid: employees.filter((e) => Number(e.salary) >= 60000 && Number(e.salary) <= 80000).length,
      senior: employees.filter((e) => Number(e.salary) > 80000).length
    };

    return {
      totalEmployees,
      totalDepartments,
      totalPayroll,
      avgSalary,
      minSalary,
      maxSalary,
      deptDistribution,
      tiers
    };
  }, [employees, departments]);

  const handleExport = () => {
    api.exportToCsv(employees, departments);
  };

  const currentDate = new Date().toLocaleDateString("en-IN", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric"
  });

  return (
    <div className="page-wrap dashboard-page">
      {/* Welcome Banner */}
      <div className="welcome-banner">
        <div className="welcome-content">
          <p className="eyebrow">EXECUTIVE OVERVIEW</p>
          <h1>
            Good day, <span className="highlight-name">{user?.username}</span> <span className="greeting-sparkle">✦</span>
          </h1>
          <p className="muted">
            Managing people, departments, and payroll operations across the enterprise.
          </p>
        </div>
        <div className="welcome-meta">
          <div className="date-chip">
            <span className="calendar-icon">📅</span> {currentDate}
          </div>
          <span className={`connection-pill ${backendOnline ? "connected" : "standalone"}`}>
            <span className="dot" /> {backendOnline ? "Spring Boot Active" : "Standalone Mode"}
          </span>
        </div>
      </div>

      {/* KPI Cards Grid */}
      <div className="stats-grid">
        <StatCard
          label="Total Employees"
          value={metrics.totalEmployees}
          hint={`Across ${metrics.totalDepartments} organizational teams`}
          icon="👥"
          badge="+Active"
          variant="green"
        />
        <StatCard
          label="Departments"
          value={metrics.totalDepartments}
          hint="Functional operational units"
          icon="🏢"
          badge="Units"
          variant="blue"
        />
        <StatCard
          label="Monthly Payroll"
          value={metrics.totalPayroll.toLocaleString("en-IN", {
            style: "currency",
            currency: "INR",
            maximumFractionDigits: 0
          })}
          hint={`Annual: ${(metrics.totalPayroll * 12).toLocaleString("en-IN", {
            style: "currency",
            currency: "INR",
            maximumFractionDigits: 0
          })}`}
          icon="💳"
          badge="INR"
          variant="purple"
        />
        <StatCard
          label="Average Salary"
          value={metrics.avgSalary.toLocaleString("en-IN", {
            style: "currency",
            currency: "INR",
            maximumFractionDigits: 0
          })}
          hint={`Min: ₹${metrics.minSalary.toLocaleString("en-IN")} • Max: ₹${metrics.maxSalary.toLocaleString("en-IN")}`}
          icon="📊"
          badge="Mean"
          variant="amber"
        />
      </div>

      {/* Analytics & Quick Access Grid */}
      <div className="analytics-grid">
        {/* Department Workforce Breakdown */}
        <section className="panel card-panel">
          <div className="panel-header">
            <div>
              <p className="eyebrow">TEAMS</p>
              <h2>Workforce Distribution</h2>
            </div>
            <Link to="/departments" className="text-link">Manage →</Link>
          </div>
          <div className="distribution-list">
            {metrics.deptDistribution.map((dept) => (
              <div key={dept.id} className="distribution-item">
                <div className="dist-labels">
                  <span className="dist-name">{dept.name}</span>
                  <span className="dist-stats">
                    <strong>{dept.count}</strong> staff ({dept.percentage}%)
                  </span>
                </div>
                <div className="dist-progress-track">
                  <div
                    className="dist-progress-fill"
                    style={{ width: `${Math.max(dept.percentage, 4)}%` }}
                  />
                </div>
              </div>
            ))}
            {metrics.deptDistribution.length === 0 && (
              <p className="muted empty-hint">No departments configured yet.</p>
            )}
          </div>
        </section>

        {/* Salary Tiers & Compensation */}
        <section className="panel card-panel">
          <div className="panel-header">
            <div>
              <p className="eyebrow">COMPENSATION</p>
              <h2>Salary Tier Breakdown</h2>
            </div>
            <span className="tier-tag">Base Monthly</span>
          </div>
          <div className="tier-cards">
            <div className="tier-box tier-entry">
              <span className="tier-title">Entry Tier (&lt; ₹60K)</span>
              <span className="tier-count">{metrics.tiers.entry}</span>
              <small className="muted">Junior & Support Roles</small>
            </div>
            <div className="tier-box tier-mid">
              <span className="tier-title">Mid Tier (₹60K - ₹80K)</span>
              <span className="tier-count">{metrics.tiers.mid}</span>
              <small className="muted">Specialists & Associates</small>
            </div>
            <div className="tier-box tier-senior">
              <span className="tier-title">Senior Tier (&gt; ₹80K)</span>
              <span className="tier-count">{metrics.tiers.senior}</span>
              <small className="muted">Leads & Managers</small>
            </div>
          </div>
          <div className="tier-footer">
            <span>Overall Spread:</span>
            <strong>
              ₹{metrics.minSalary.toLocaleString("en-IN")} — ₹{metrics.maxSalary.toLocaleString("en-IN")}
            </strong>
          </div>
        </section>
      </div>

      {/* Quick Access & Recent Activity Grid */}
      <div className="bottom-dashboard-grid">
        {/* Quick Launch Shortcuts */}
        <section className="panel card-panel">
          <div className="panel-header">
            <div>
              <p className="eyebrow">QUICK ACTIONS</p>
              <h2>Workspace Controls</h2>
            </div>
          </div>
          <div className="action-button-grid">
            {user?.role === "HR" && (
              <Link to="/employees/new" className="action-tile green">
                <span className="tile-icon">＋</span>
                <div>
                  <strong>Add Employee</strong>
                  <p>Register new staff member</p>
                </div>
              </Link>
            )}
            <Link to="/employees" className="action-tile blue">
              <span className="tile-icon">👥</span>
              <div>
                <strong>Browse Directory</strong>
                <p>Search, sort, and edit</p>
              </div>
            </Link>
            <Link to="/departments" className="action-tile mint">
              <span className="tile-icon">🏢</span>
              <div>
                <strong>View Departments</strong>
                <p>Team structure & wings</p>
              </div>
            </Link>
            <button onClick={handleExport} className="action-tile amber">
              <span className="tile-icon">📥</span>
              <div>
                <strong>Export CSV</strong>
                <p>Download full employee data</p>
              </div>
            </button>
          </div>
        </section>

        {/* Live Activity Feed */}
        <section className="panel card-panel">
          <div className="panel-header">
            <div>
              <p className="eyebrow">AUDIT</p>
              <h2>Recent Actions</h2>
            </div>
            <span className="activity-live-indicator"><span className="pulse-dot" /> Live</span>
          </div>
          <ul className="activity-feed">
            {activityLog.slice(0, 5).map((log) => (
              <li key={log.id} className={`activity-item activity-${log.type}`}>
                <span className="activity-dot" />
                <span className="activity-text">{log.action}</span>
                <span className="activity-time">{log.time}</span>
              </li>
            ))}
          </ul>
        </section>
      </div>

      {/* Architecture Info Banner */}
      <div className="info-banner">
        <div className="info-symbol">ℹ</div>
        <div>
          <strong>Architecture: Module 4 React 19 Single Page Application</strong>
          <p>
            Connects to Spring Boot REST endpoints (<code>/api/auth/**</code>, <code>/api/employees/**</code>, <code>/api/departments/**</code>) with session-based JWT authentication, role guards (HR, Manager, Employee), and automatic fallback for offline assessment review.
          </p>
        </div>
      </div>
    </div>
  );
}
