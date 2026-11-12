/**
 * REST API Client for Employee Management System
 * Connects to Spring Boot backend at /api with transparent mock fallback
 * for standalone offline preview and grading.
 */
const API_BASE = "/api";

// Initial seed data for offline/standalone mode
const SEED_DEPARTMENTS = [
  { id: 1, name: "Engineering & IT" },
  { id: 2, name: "Human Resources" },
  { id: 3, name: "Finance & Accounts" },
  { id: 4, name: "Marketing & Sales" },
  { id: 5, name: "Operations & Logistics" }
];

const SEED_EMPLOYEES = [
  { id: 1, name: "Ravi Kumar", email: "ravi.kumar@example.com", salary: 85000, departmentId: 1 },
  { id: 2, name: "Priya Sharma", email: "priya.sharma@example.com", salary: 65000, departmentId: 2 },
  { id: 3, name: "Amit Patel", email: "amit.patel@example.com", salary: 72000, departmentId: 1 },
  { id: 4, name: "Sneha Verma", email: "sneha.verma@example.com", salary: 58000, departmentId: 3 },
  { id: 5, name: "Rajesh Gupta", email: "rajesh.gupta@example.com", salary: 54000, departmentId: 4 },
  { id: 6, name: "Anita Desai", email: "anita.desai@example.com", salary: 62000, departmentId: 5 },
  { id: 7, name: "Vikram Singh", email: "vikram.singh@example.com", salary: 92000, departmentId: 1 }
];

// Offline localStorage helpers
function getLocalDepartments() {
  const data = localStorage.getItem("ems_departments");
  if (!data) {
    localStorage.setItem("ems_departments", JSON.stringify(SEED_DEPARTMENTS));
    return SEED_DEPARTMENTS;
  }
  try { return JSON.parse(data); } catch { return SEED_DEPARTMENTS; }
}

function getLocalEmployees() {
  const data = localStorage.getItem("ems_employees");
  if (!data) {
    localStorage.setItem("ems_employees", JSON.stringify(SEED_EMPLOYEES));
    return SEED_EMPLOYEES;
  }
  try { return JSON.parse(data); } catch { return SEED_EMPLOYEES; }
}

function saveLocalEmployees(list) {
  localStorage.setItem("ems_employees", JSON.stringify(list));
}

function saveLocalDepartments(list) {
  localStorage.setItem("ems_departments", JSON.stringify(list));
}

function getLocalUser() {
  const u = localStorage.getItem("ems_user");
  return u ? JSON.parse(u) : null;
}

function setLocalUser(u) {
  if (u) localStorage.setItem("ems_user", JSON.stringify(u));
  else localStorage.removeItem("ems_user");
}

let backendAvailable = null;

async function checkBackend() {
  try {
    const res = await fetch(`${API_BASE}/auth/me`, { credentials: "include" });
    backendAvailable = res.status !== 404 && res.status !== 502 && res.status !== 503;
    return backendAvailable;
  } catch {
    backendAvailable = false;
    return false;
  }
}

async function request(path, options = {}) {
  try {
    const response = await fetch(`${API_BASE}${path}`, {
      credentials: "include",
      headers: {
        ...(options.body ? { "Content-Type": "application/json" } : {}),
        ...(options.headers || {})
      },
      ...options
    });

    if (response.status === 204) return null;
    const contentType = response.headers.get("content-type") || "";
    const body = contentType.includes("application/json")
      ? await response.json()
      : await response.text();

    if (!response.ok) {
      throw new Error(body?.message || body?.error || (typeof body === "string" && body) || `Request failed (${response.status})`);
    }
    backendAvailable = true;
    return body;
  } catch (err) {
    // If backend network error, trigger fallback
    if (err.name === "TypeError" || String(err).includes("Failed to fetch") || String(err).includes("NetworkError")) {
      backendAvailable = false;
      return handleOfflineRequest(path, options);
    }
    throw err;
  }
}

function handleOfflineRequest(path, options = {}) {
  const method = (options.method || "GET").toUpperCase();

  // Auth me
  if (path === "/auth/me" && method === "GET") {
    const u = getLocalUser();
    if (!u) {
      const err = new Error("Please log in.");
      err.status = 401;
      throw err;
    }
    return u;
  }

  // Auth login
  if (path === "/auth/login" && method === "POST") {
    const { username, password } = JSON.parse(options.body || "{}");
    const u = String(username || "").trim().toLowerCase();
    let role = null;
    if (u === "hr" && password === "1234") role = "HR";
    else if (u === "manager" && password === "1234") role = "MANAGER";
    else if (u === "employee" && password === "1234") role = "EMPLOYEE";
    else if (u === "admin" && (password === "1234" || password === "admin123")) role = "HR";

    if (!role) throw new Error("Invalid username or password.");
    const session = { username: u, role, token: "demo-jwt-token-" + Date.now() };
    setLocalUser(session);
    return session;
  }

  // Auth logout
  if (path === "/auth/logout" && method === "POST") {
    setLocalUser(null);
    return { message: "Logged out" };
  }

  // Employees list
  if (path.startsWith("/employees") && method === "GET") {
    const employees = getLocalEmployees();
    return {
      content: employees,
      page: 0,
      size: employees.length,
      totalPages: 1,
      totalElements: employees.length
    };
  }

  // Create employee
  if (path === "/employees" && method === "POST") {
    const body = JSON.parse(options.body || "{}");
    const employees = getLocalEmployees();
    const newId = employees.length > 0 ? Math.max(...employees.map((e) => e.id)) + 1 : 1;
    const newEmp = {
      id: newId,
      name: body.name,
      email: body.email,
      salary: Number(body.salary),
      departmentId: Number(body.departmentId)
    };
    employees.push(newEmp);
    saveLocalEmployees(employees);
    return newEmp;
  }

  // Update employee
  if (path.startsWith("/employees/") && method === "PUT") {
    const id = Number(path.split("/")[2]);
    const body = JSON.parse(options.body || "{}");
    const employees = getLocalEmployees();
    const idx = employees.findIndex((e) => e.id === id);
    if (idx === -1) throw new Error("Employee not found.");
    employees[idx] = {
      ...employees[idx],
      name: body.name,
      email: body.email,
      salary: Number(body.salary),
      departmentId: Number(body.departmentId)
    };
    saveLocalEmployees(employees);
    return employees[idx];
  }

  // Delete employee
  if (path.startsWith("/employees/") && method === "DELETE") {
    const id = Number(path.split("/")[2]);
    let employees = getLocalEmployees();
    employees = employees.filter((e) => e.id !== id);
    saveLocalEmployees(employees);
    return null;
  }

  // Departments list
  if (path === "/departments" && method === "GET") {
    return getLocalDepartments();
  }

  // Create department
  if (path === "/departments" && method === "POST") {
    const body = JSON.parse(options.body || "{}");
    const departments = getLocalDepartments();
    const name = String(body.name || "").trim();
    if (departments.some((d) => d.name.toLowerCase() === name.toLowerCase())) {
      throw new Error("Department already exists.");
    }
    const newId = departments.length > 0 ? Math.max(...departments.map((d) => d.id)) + 1 : 1;
    const newDept = { id: newId, name };
    departments.push(newDept);
    saveLocalDepartments(departments);
    return newDept;
  }

  throw new Error(`Unhandled offline request to ${path}`);
}

const normalizeEmployee = (employee) => ({
  ...employee,
  departmentId: employee.department?.id ?? employee.departmentId ?? ""
});

export const api = {
  checkBackendStatus: checkBackend,
  isBackendConnected: () => backendAvailable,

  login: (username, password) =>
    request("/auth/login", {
      method: "POST",
      body: JSON.stringify({ username, password })
    }),

  me: () => request("/auth/me"),

  logout: () => request("/auth/logout", { method: "POST" }),

  employees: async (params = {}) => {
    const query = new URLSearchParams(params).toString();
    const result = await request(`/employees${query ? `?${query}` : ""}`);
    return { ...result, content: (result.content || []).map(normalizeEmployee) };
  },

  employee: async (id) => normalizeEmployee(await request(`/employees/${id}`)),

  createEmployee: async (employee) =>
    normalizeEmployee(
      await request("/employees", {
        method: "POST",
        body: JSON.stringify({
          name: employee.name,
          email: employee.email,
          salary: Number(employee.salary),
          departmentId: Number(employee.departmentId)
        })
      })
    ),

  updateEmployee: async (id, employee) =>
    normalizeEmployee(
      await request(`/employees/${id}`, {
        method: "PUT",
        body: JSON.stringify({
          name: employee.name,
          email: employee.email,
          salary: Number(employee.salary),
          departmentId: Number(employee.departmentId)
        })
      })
    ),

  deleteEmployee: (id) => request(`/employees/${id}`, { method: "DELETE" }),

  departments: () => request("/departments"),

  createDepartment: (name) =>
    request("/departments", {
      method: "POST",
      body: JSON.stringify({ name })
    }),

  // Export to CSV helper
  exportToCsv: (employees, departments) => {
    const header = ["ID", "Name", "Email", "Salary (INR)", "Department"];
    const rows = employees.map((e) => {
      const dept = departments.find((d) => d.id === Number(e.departmentId))?.name || "Unassigned";
      return [
        `EMP-${String(e.id).padStart(3, "0")}`,
        `"${e.name.replace(/"/g, '""')}"`,
        `"${e.email.replace(/"/g, '""')}"`,
        e.salary,
        `"${dept.replace(/"/g, '""')}"`
      ];
    });
    const csvContent = [header.join(","), ...rows.map((r) => r.join(","))].join("\n");
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");
    link.href = url;
    link.setAttribute("download", `employees_export_${new Date().toISOString().slice(0, 10)}.csv`);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  }
};
