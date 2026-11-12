import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useApp } from "../context/AppContext.jsx";

export default function EmployeeForm() {
  const { id } = useParams();
  const { employees, departments, user, saveEmployee } = useApp();
  const navigate = useNavigate();

  const existing = id ? employees.find((emp) => emp.id === Number(id)) : null;
  const [form, setForm] = useState({ name: "", email: "", salary: "", departmentId: "" });
  const [error, setError] = useState("");
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (existing) {
      setForm({
        name: existing.name || "",
        email: existing.email || "",
        salary: String(existing.salary || ""),
        departmentId: String(existing.departmentId || "")
      });
    }
  }, [existing]);

  // Role verification guards
  const isEmployeeRole = user?.role === "EMPLOYEE";
  const isCreating = !id;
  const isHr = user?.role === "HR";
  const isManager = user?.role === "MANAGER";

  if (isEmployeeRole || (isCreating && !isHr) || (!isCreating && !isHr && !isManager)) {
    return (
      <div className="page-wrap">
        <div className="panel access-denied">
          <h2>Access Restricted</h2>
          <p className="muted">
            Your current role (<strong>{user?.role}</strong>) does not have permission to{" "}
            {isCreating ? "create new employee records" : "modify this record"}.
          </p>
          <Link className="button button-primary" to="/employees">
            Return to Employees
          </Link>
        </div>
      </div>
    );
  }

  if (id && !existing) {
    return (
      <div className="page-wrap">
        <div className="panel access-denied">
          <h2>Employee Not Found</h2>
          <p className="muted">The requested employee record (ID: {id}) could not be located.</p>
          <Link className="button button-primary" to="/employees">
            Return to Employee Directory
          </Link>
        </div>
      </div>
    );
  }

  const updateField = (event) => {
    const { name, value } = event.target;
    setForm((old) => ({ ...old, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError("");

    if (!form.name.trim()) {
      setError("Please provide a valid full name.");
      return;
    }

    if (!form.email.trim() || !form.email.includes("@")) {
      setError("Please enter a valid email address.");
      return;
    }

    const sal = Number(form.salary);
    if (isNaN(sal) || sal < 0) {
      setError("Monthly salary must be a non-negative number.");
      return;
    }

    if (!form.departmentId || !departments.some((d) => d.id === Number(form.departmentId))) {
      setError("Please select an active department.");
      return;
    }

    try {
      setSubmitting(true);
      await saveEmployee({
        ...form,
        id: id ? Number(id) : undefined,
        salary: sal,
        departmentId: Number(form.departmentId)
      });
      navigate("/employees");
    } catch (err) {
      setError(err.message || "Failed to save employee record.");
    } finally {
      setSubmitting(false);
    }
  };

  const previewInitials = form.name.trim()
    ? form.name
        .trim()
        .split(" ")
        .map((p) => p[0])
        .slice(0, 2)
        .join("")
        .toUpperCase()
    : "EMP";

  const annualCtc = Number(form.salary) > 0 ? (Number(form.salary) * 12).toLocaleString("en-IN") : "0";

  return (
    <div className="page-wrap form-page">
      <div className="page-title-row">
        <div>
          <p className="eyebrow">PEOPLE OPERATIONS</p>
          <h1>{id ? "Edit Employee Record" : "Register New Employee"}</h1>
          <p className="muted">
            {id ? "Update employee details, compensation, and department assignment." : "Add a team member to the organizational workforce."}
          </p>
        </div>
        <Link className="button button-outline" to="/employees">
          ← Cancel & Return
        </Link>
      </div>

      <section className="panel form-panel">
        <div className="form-panel-heading">
          <div className="form-avatar-preview">
            <span className="avatar avatar-large">{previewInitials}</span>
          </div>
          <div>
            <h2>{form.name.trim() || "Employee Details"}</h2>
            <p className="muted">
              {id ? `Editing record EMP-${String(id).padStart(3, "0")}` : "Complete required fields (*)"}
            </p>
          </div>
        </div>

        {error && <div className="alert alert-error" role="alert">✕ {error}</div>}

        <form className="employee-form" onSubmit={handleSubmit}>
          {/* Full Name */}
          <div className="field">
            <label htmlFor="name">Full Name *</label>
            <input
              id="name"
              name="name"
              value={form.name}
              onChange={updateField}
              placeholder="e.g. Ramesh Chandra"
              required
            />
          </div>

          {/* Email Address */}
          <div className="field">
            <label htmlFor="email">Corporate Email Address *</label>
            <input
              id="email"
              name="email"
              type="email"
              value={form.email}
              onChange={updateField}
              placeholder="name@company.com"
              required
            />
          </div>

          {/* Monthly Salary */}
          <div className="field">
            <label htmlFor="salary">
              Monthly Salary (INR ₹) *
              {Number(form.salary) > 0 && (
                <span className="field-hint">Annual CTC: ₹{annualCtc}</span>
              )}
            </label>
            <input
              id="salary"
              name="salary"
              type="number"
              min="0"
              step="500"
              value={form.salary}
              onChange={updateField}
              placeholder="e.g. 65000"
              required
            />
          </div>

          {/* Department Selection */}
          <div className="field">
            <label htmlFor="departmentId">Department Assignment *</label>
            <select
              id="departmentId"
              name="departmentId"
              value={form.departmentId}
              onChange={updateField}
              required
            >
              <option value="">Select Department...</option>
              {departments.map((dept) => (
                <option key={dept.id} value={dept.id}>
                  {dept.name}
                </option>
              ))}
            </select>
          </div>

          {/* Form Actions */}
          <div className="form-actions">
            <Link to="/employees" className="button button-outline">
              Cancel
            </Link>
            <button type="submit" className="button button-primary" disabled={submitting}>
              {submitting ? "Saving..." : id ? "Save Changes →" : "Create Employee →"}
            </button>
          </div>
        </form>
      </section>
    </div>
  );
}
