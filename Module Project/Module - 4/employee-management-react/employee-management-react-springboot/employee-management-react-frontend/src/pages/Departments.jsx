import { useState } from "react";
import { Link } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";

export default function Departments() {
  const { departments, employees, user, addDepartment } = useApp();
  const [name, setName] = useState("");
  const [message, setMessage] = useState("");
  const [isError, setIsError] = useState(false);
  const [expandedDeptId, setExpandedDeptId] = useState(null);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setMessage("");
    if (!name.trim()) return;

    try {
      await addDepartment(name);
      setIsError(false);
      setMessage(`Department "${name.trim()}" added successfully.`);
      setName("");
      setTimeout(() => setMessage(""), 4000);
    } catch (err) {
      setIsError(true);
      setMessage(err.message || "Could not add department.");
    }
  };

  const toggleExpand = (deptId) => {
    setExpandedDeptId((current) => (current === deptId ? null : deptId));
  };

  const totalEmployees = employees.length;
  const avgTeamSize =
    departments.length > 0 ? (totalEmployees / departments.length).toFixed(1) : "0";

  return (
    <div className="page-wrap departments-page">
      {/* Title Row */}
      <div className="page-title-row">
        <div>
          <p className="eyebrow">ORGANIZATION STRUCTURE</p>
          <h1>Departments & Teams</h1>
          <p className="muted">
            Departmental hierarchy, resource allocation, and team membership rosters.
          </p>
        </div>
        <div className="dept-summary-badges">
          <span className="count-chip"><strong>{departments.length}</strong> Departments</span>
          <span className="count-chip"><strong>{avgTeamSize}</strong> Avg Members / Dept</span>
        </div>
      </div>

      {/* Add Department Panel (HR only) */}
      {user?.role === "HR" && (
        <section className="panel add-department-panel">
          <div className="add-dept-header">
            <div>
              <h2>Create New Department</h2>
              <p className="muted">Add a functional unit to organize employees and assign budgets.</p>
            </div>
          </div>
          <form className="inline-add-form" onSubmit={handleSubmit}>
            <input
              aria-label="Department name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="e.g. Quality Assurance, Cloud Engineering, Research & Development"
              required
            />
            <button className="button button-primary" type="submit">
              ＋ Add Department
            </button>
          </form>
          {message && (
            <p className={isError ? "form-message error-text" : "form-message success-text"}>
              {isError ? "✕ " : "✓ "} {message}
            </p>
          )}
        </section>
      )}

      {/* Department Cards Grid */}
      <div className="department-grid">
        {departments.map((dept, index) => {
          const deptEmployees = employees.filter(
            (e) => Number(e.departmentId) === dept.id
          );
          const count = deptEmployees.length;
          const deptPayroll = deptEmployees.reduce(
            (sum, e) => sum + Number(e.salary || 0),
            0
          );
          const deptAvgSalary = count > 0 ? Math.round(deptPayroll / count) : 0;
          const isExpanded = expandedDeptId === dept.id;

          const palettes = ["dept-green", "dept-blue", "dept-orange", "dept-purple", "dept-teal"];
          const paletteClass = palettes[index % palettes.length];

          return (
            <article className={`department-card ${isExpanded ? "card-expanded" : ""}`} key={dept.id}>
              <div className="department-card-top">
                <span className={`department-icon ${paletteClass}`}>🏢</span>
                <span className="department-id">DEPT-{String(dept.id).padStart(2, "0")}</span>
              </div>

              <h2>{dept.name}</h2>
              <p className="dept-description">Enterprise Functional Team</p>

              {/* Department Metrics */}
              <div className="dept-stats-strip">
                <div className="dept-metric">
                  <small>Headcount</small>
                  <strong>{count} staff</strong>
                </div>
                <div className="dept-metric">
                  <small>Monthly Payroll</small>
                  <strong>
                    {deptPayroll.toLocaleString("en-IN", {
                      style: "currency",
                      currency: "INR",
                      maximumFractionDigits: 0
                    })}
                  </strong>
                </div>
                <div className="dept-metric">
                  <small>Avg Salary</small>
                  <strong>
                    {deptAvgSalary.toLocaleString("en-IN", {
                      style: "currency",
                      currency: "INR",
                      maximumFractionDigits: 0
                    })}
                  </strong>
                </div>
              </div>

              {/* Card Footer */}
              <div className="department-card-bottom">
                <button
                  type="button"
                  className="dept-expand-btn"
                  onClick={() => toggleExpand(dept.id)}
                >
                  {isExpanded ? "Hide Members ▲" : `View Members (${count}) ▼`}
                </button>
                <Link to={`/employees`} className="text-link">
                  Directory →
                </Link>
              </div>

              {/* Expandable Member List */}
              {isExpanded && (
                <div className="dept-members-drawer">
                  <h4>Team Members ({count})</h4>
                  {deptEmployees.length > 0 ? (
                    <ul className="dept-member-list">
                      {deptEmployees.map((e) => (
                        <li key={e.id} className="dept-member-item">
                          <div>
                            <strong>{e.name}</strong>
                            <small>{e.email}</small>
                          </div>
                          <span className="dept-member-salary">
                            ₹{Number(e.salary).toLocaleString("en-IN")}
                          </span>
                        </li>
                      ))}
                    </ul>
                  ) : (
                    <p className="no-members-hint">No staff currently assigned to this department.</p>
                  )}
                </div>
              )}
            </article>
          );
        })}
      </div>

      {/* Relational Mapping Info Banner */}
      <div className="info-banner">
        <div className="info-symbol">ℹ</div>
        <div>
          <strong>Relational Architecture (Entity Mapping)</strong>
          <p>
            In the Spring Boot backend, each <code>Employee</code> is associated with a <code>Department</code>. The REST API serializes this relationship via <code>departmentId</code> and the nested <code>department</code> object, allowing the React frontend to compute team rosters and budgets in real time.
          </p>
        </div>
      </div>
    </div>
  );
}
