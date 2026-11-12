import { useMemo, useState } from "react";
import { Link } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";
import { api } from "../services/api.js";

export default function Employees() {
  const { employees, departments, user, deleteEmployee } = useApp();
  const [search, setSearch] = useState("");
  const [deptFilter, setDeptFilter] = useState("ALL");
  const [salaryFilter, setSalaryFilter] = useState("ALL");
  const [sortBy, setSortBy] = useState("name");
  const [direction, setDirection] = useState("asc");
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(5);
  const [selectedEmployee, setSelectedEmployee] = useState(null);

  // Filter and sort logic
  const filtered = useMemo(() => {
    const term = search.toLowerCase().trim();

    return employees
      .filter((employee) => {
        const dept = departments.find((d) => d.id === Number(employee.departmentId))?.name || "";
        const matchesTerm = [employee.name, employee.email, dept].some((value) =>
          String(value).toLowerCase().includes(term)
        );
        if (!matchesTerm) return false;

        if (deptFilter !== "ALL" && Number(employee.departmentId) !== Number(deptFilter)) {
          return false;
        }

        const sal = Number(employee.salary || 0);
        if (salaryFilter === "LOW" && sal >= 60000) return false;
        if (salaryFilter === "MID" && (sal < 60000 || sal > 80000)) return false;
        if (salaryFilter === "HIGH" && sal <= 80000) return false;

        return true;
      })
      .sort((a, b) => {
        let av = a[sortBy];
        let bv = b[sortBy];

        if (sortBy === "department") {
          av = departments.find((d) => d.id === Number(a.departmentId))?.name || "";
          bv = departments.find((d) => d.id === Number(b.departmentId))?.name || "";
        }

        const comparison =
          typeof av === "number" ? av - bv : String(av || "").localeCompare(String(bv || ""));
        return direction === "asc" ? comparison : -comparison;
      });
  }, [employees, departments, search, deptFilter, salaryFilter, sortBy, direction]);

  const totalPages = Math.max(1, Math.ceil(filtered.length / pageSize));
  const currentPage = Math.min(page, totalPages - 1);
  const visibleEmployees = filtered.slice(currentPage * pageSize, (currentPage + 1) * pageSize);

  const changeSort = (field) => {
    if (sortBy === field) {
      setDirection((old) => (old === "asc" ? "desc" : "asc"));
    } else {
      setSortBy(field);
      setDirection("asc");
    }
    setPage(0);
  };

  const departmentName = (id) =>
    departments.find((d) => d.id === Number(id))?.name || "Unassigned";

  const initials = (name) =>
    name
      .split(" ")
      .map((part) => part[0])
      .slice(0, 2)
      .join("")
      .toUpperCase();

  const handleExport = () => {
    api.exportToCsv(filtered, departments);
  };

  const clearFilters = () => {
    setSearch("");
    setDeptFilter("ALL");
    setSalaryFilter("ALL");
    setPage(0);
  };

  const hasActiveFilters = search || deptFilter !== "ALL" || salaryFilter !== "ALL";

  return (
    <div className="page-wrap employees-page">
      {/* Page Title & Actions */}
      <div className="page-title-row">
        <div>
          <p className="eyebrow">PEOPLE OPERATIONS</p>
          <h1>Employee Directory</h1>
          <p className="muted">
            Manage records, salaries, team affiliations, and access roles.
          </p>
        </div>
        <div className="title-action-buttons">
          <button onClick={handleExport} className="button button-outline" title="Export as CSV">
            📥 Export CSV
          </button>
          {user?.role === "HR" && (
            <Link to="/employees/new" className="button button-primary">
              ＋ Add Employee
            </Link>
          )}
        </div>
      </div>

      {/* Main Directory Panel */}
      <section className="panel directory-panel">
        {/* Toolbar: Search & Multi-Filters */}
        <div className="toolbar-complex">
          <div className="search-box">
            <span className="search-icon">🔍</span>
            <input
              aria-label="Search employees"
              placeholder="Search by name, email, department..."
              value={search}
              onChange={(e) => {
                setSearch(e.target.value);
                setPage(0);
              }}
            />
            {search && (
              <button className="clear-search-btn" onClick={() => setSearch("")}>✕</button>
            )}
          </div>

          <div className="filter-group">
            {/* Department Filter */}
            <select
              className="filter-select"
              value={deptFilter}
              onChange={(e) => {
                setDeptFilter(e.target.value);
                setPage(0);
              }}
              aria-label="Filter by department"
            >
              <option value="ALL">All Departments</option>
              {departments.map((dept) => (
                <option key={dept.id} value={dept.id}>
                  {dept.name}
                </option>
              ))}
            </select>

            {/* Salary Tier Filter */}
            <select
              className="filter-select"
              value={salaryFilter}
              onChange={(e) => {
                setSalaryFilter(e.target.value);
                setPage(0);
              }}
              aria-label="Filter by salary tier"
            >
              <option value="ALL">All Salaries</option>
              <option value="LOW">&lt; ₹60,000</option>
              <option value="MID">₹60,000 – ₹80,000</option>
              <option value="HIGH">&gt; ₹80,000</option>
            </select>

            {hasActiveFilters && (
              <button className="button button-light button-small" onClick={clearFilters}>
                Clear Filters
              </button>
            )}
          </div>

          <div className="meta-counts">
            <span className="result-count">
              <strong>{filtered.length}</strong> matching / {employees.length} total
            </span>
          </div>
        </div>

        {/* Data Table */}
        <div className="table-scroll">
          <table className="data-table">
            <thead>
              <tr>
                <th>
                  <button className="sort-button" onClick={() => changeSort("name")}>
                    EMPLOYEE {sortBy === "name" ? (direction === "asc" ? "▲" : "▼") : "↕"}
                  </button>
                </th>
                <th>
                  <button className="sort-button" onClick={() => changeSort("email")}>
                    EMAIL {sortBy === "email" ? (direction === "asc" ? "▲" : "▼") : "↕"}
                  </button>
                </th>
                <th>
                  <button className="sort-button" onClick={() => changeSort("department")}>
                    DEPARTMENT {sortBy === "department" ? (direction === "asc" ? "▲" : "▼") : "↕"}
                  </button>
                </th>
                <th>
                  <button className="sort-button" onClick={() => changeSort("salary")}>
                    SALARY (INR) {sortBy === "salary" ? (direction === "asc" ? "▲" : "▼") : "↕"}
                  </button>
                </th>
                <th>STATUS</th>
                <th className="th-actions">ACTIONS</th>
              </tr>
            </thead>
            <tbody>
              {visibleEmployees.map((emp) => (
                <tr key={emp.id} className="clickable-row">
                  <td>
                    <div className="employee-cell" onClick={() => setSelectedEmployee(emp)}>
                      <span className="avatar avatar-glow">{initials(emp.name)}</span>
                      <div>
                        <strong className="emp-name-link">{emp.name}</strong>
                        <small className="emp-code">EMP-{String(emp.id).padStart(3, "0")}</small>
                      </div>
                    </div>
                  </td>
                  <td className="email-cell">{emp.email}</td>
                  <td>
                    <span className="department-pill">{departmentName(emp.departmentId)}</span>
                  </td>
                  <td className="salary-cell">
                    {Number(emp.salary).toLocaleString("en-IN", {
                      style: "currency",
                      currency: "INR",
                      maximumFractionDigits: 0
                    })}
                  </td>
                  <td>
                    <span className="status-pill">
                      <span className="status-dot-active" /> Active
                    </span>
                  </td>
                  <td>
                    <div className="actions">
                      <button
                        className="action-view"
                        onClick={() => setSelectedEmployee(emp)}
                        title="View profile details"
                      >
                        View
                      </button>
                      {(user?.role === "HR" || user?.role === "MANAGER") && (
                        <Link className="action-link" to={`/employees/edit/${emp.id}`}>
                          Edit
                        </Link>
                      )}
                      {user?.role === "HR" && (
                        <button
                          className="action-delete"
                          onClick={() => {
                            if (window.confirm(`Are you sure you want to delete "${emp.name}"?`)) {
                              deleteEmployee(emp.id);
                            }
                          }}
                        >
                          Delete
                        </button>
                      )}
                      {user?.role === "EMPLOYEE" && <span className="muted">Read-only</span>}
                    </div>
                  </td>
                </tr>
              ))}

              {visibleEmployees.length === 0 && (
                <tr>
                  <td colSpan="6" className="empty-state">
                    <span className="empty-icon">🔍</span>
                    <strong>No matching employees found</strong>
                    <p>Try adjusting your search terms or clearing department/salary filters.</p>
                    {hasActiveFilters && (
                      <button className="button button-outline button-small" onClick={clearFilters}>
                        Reset All Filters
                      </button>
                    )}
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {/* Table Footer & Pagination */}
        <div className="table-footer">
          <div className="footer-left">
            <span>
              Showing{" "}
              <strong>
                {filtered.length === 0 ? 0 : currentPage * pageSize + 1}–
                {Math.min((currentPage + 1) * pageSize, filtered.length)}
              </strong>{" "}
              of <strong>{filtered.length}</strong> entries
            </span>
            <div className="page-size-selector">
              <label>Per page:</label>
              <select
                value={pageSize}
                onChange={(e) => {
                  setPageSize(Number(e.target.value));
                  setPage(0);
                }}
              >
                <option value={5}>5</option>
                <option value={10}>10</option>
                <option value={20}>20</option>
              </select>
            </div>
          </div>

          <div className="pagination">
            <button
              disabled={currentPage === 0}
              onClick={() => setPage((p) => Math.max(0, p - 1))}
              className="page-nav-btn"
            >
              ← Previous
            </button>
            <span className="page-indicator">
              Page <strong>{currentPage + 1}</strong> of <strong>{totalPages}</strong>
            </span>
            <button
              disabled={currentPage + 1 >= totalPages}
              onClick={() => setPage((p) => Math.min(totalPages - 1, p + 1))}
              className="page-nav-btn"
            >
              Next →
            </button>
          </div>
        </div>
      </section>

      {/* Role Permission Footer Note */}
      <div className="permission-note-box">
        <span>Current Role: <strong className={`role-pill role-${user?.role?.toLowerCase()}`}>{user?.role}</strong></span>
        <span className="perm-desc">
          {user?.role === "HR"
            ? "Full Administrative Access: View, Create, Update, Delete employees and departments."
            : user?.role === "MANAGER"
            ? "Managerial Access: View employee records, update employee details, view departments."
            : "Staff Access: Read-only directory access."}
        </span>
      </div>

      {/* Employee Quick View Modal */}
      {selectedEmployee && (
        <div className="modal-backdrop" onClick={() => setSelectedEmployee(null)}>
          <div className="modal-card" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <div className="modal-profile">
                <span className="modal-avatar">{initials(selectedEmployee.name)}</span>
                <div>
                  <h3>{selectedEmployee.name}</h3>
                  <span className="modal-id">EMP-{String(selectedEmployee.id).padStart(3, "0")}</span>
                </div>
              </div>
              <button className="modal-close" onClick={() => setSelectedEmployee(null)}>✕</button>
            </div>

            <div className="modal-body">
              <div className="modal-field">
                <label>Email Address</label>
                <p>{selectedEmployee.email}</p>
              </div>
              <div className="modal-field">
                <label>Department</label>
                <p><span className="department-pill">{departmentName(selectedEmployee.departmentId)}</span></p>
              </div>
              <div className="modal-field">
                <label>Monthly Salary</label>
                <p className="modal-salary">
                  {Number(selectedEmployee.salary).toLocaleString("en-IN", {
                    style: "currency",
                    currency: "INR",
                    maximumFractionDigits: 0
                  })}
                </p>
              </div>
              <div className="modal-field">
                <label>Estimated Annual CTC</label>
                <p>
                  {(Number(selectedEmployee.salary) * 12).toLocaleString("en-IN", {
                    style: "currency",
                    currency: "INR",
                    maximumFractionDigits: 0
                  })}
                </p>
              </div>
              <div className="modal-field">
                <label>Employment Status</label>
                <p><span className="status-pill"><span className="status-dot-active" /> Active & Verified</span></p>
              </div>
            </div>

            <div className="modal-footer">
              {(user?.role === "HR" || user?.role === "MANAGER") && (
                <Link
                  to={`/employees/edit/${selectedEmployee.id}`}
                  className="button button-primary button-small"
                >
                  Edit Profile
                </Link>
              )}
              <button
                className="button button-light button-small"
                onClick={() => setSelectedEmployee(null)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
