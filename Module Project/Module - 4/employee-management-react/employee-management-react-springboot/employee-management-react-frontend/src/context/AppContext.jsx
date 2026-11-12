import { createContext, useContext, useEffect, useMemo, useState, useCallback } from "react";
import { api } from "../services/api.js";

const AppContext = createContext(null);

export function AppProvider({ children }) {
  const [user, setUser] = useState(null);
  const [employees, setEmployees] = useState([]);
  const [departments, setDepartments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [appError, setAppError] = useState("");
  const [backendOnline, setBackendOnline] = useState(false);
  const [toast, setToast] = useState(null);
  const [activityLog, setActivityLog] = useState([
    { id: 1, action: "System initialized", time: "Just now", type: "system" }
  ]);

  const showToast = useCallback((message, type = "success") => {
    setToast({ message, type });
    setTimeout(() => {
      setToast((curr) => (curr?.message === message ? null : curr));
    }, 3500);
  }, []);

  const logActivity = useCallback((action, type = "info") => {
    setActivityLog((prev) => [
      {
        id: Date.now() + Math.random(),
        action,
        time: new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" }),
        type
      },
      ...prev.slice(0, 19)
    ]);
  }, []);

  const refreshData = useCallback(async () => {
    try {
      const [employeePage, departmentList] = await Promise.all([
        api.employees({ page: 0, size: 1000, sortBy: "name", direction: "asc" }),
        api.departments()
      ]);
      setEmployees(employeePage?.content || []);
      setDepartments(departmentList || []);
      setBackendOnline(api.isBackendConnected() ?? false);
    } catch (err) {
      console.warn("Error refreshing data:", err);
    }
  }, []);

  useEffect(() => {
    let active = true;
    (async () => {
      try {
        await api.checkBackendStatus();
        if (!active) return;
        setBackendOnline(api.isBackendConnected() ?? false);

        const current = await api.me();
        if (!active) return;
        setUser(current);
        await refreshData();
      } catch {
        // Expected when no active session
      } finally {
        if (active) setLoading(false);
      }
    })();
    return () => { active = false; };
  }, [refreshData]);

  const login = async (username, password) => {
    setAppError("");
    const result = await api.login(username, password);
    const session = { username: result.username, role: result.role };
    setUser(session);
    setBackendOnline(api.isBackendConnected() ?? false);
    await refreshData();
    logActivity(`User ${session.username} (${session.role}) signed in`, "auth");
    showToast(`Welcome back, ${session.username}! Signed in as ${session.role}.`, "success");
    return true;
  };

  const logout = async () => {
    try {
      await api.logout();
    } finally {
      const prevUser = user?.username || "User";
      setUser(null);
      setEmployees([]);
      setDepartments([]);
      logActivity(`${prevUser} signed out`, "auth");
      showToast("Signed out successfully.", "info");
    }
  };

  const saveEmployee = async (employee) => {
    let saved;
    if (employee.id) {
      saved = await api.updateEmployee(employee.id, employee);
      logActivity(`Updated employee: ${saved.name}`, "edit");
      showToast(`Employee "${saved.name}" updated successfully.`, "success");
    } else {
      saved = await api.createEmployee(employee);
      logActivity(`Created employee: ${saved.name}`, "create");
      showToast(`Employee "${saved.name}" created successfully.`, "success");
    }
    await refreshData();
    return saved;
  };

  const deleteEmployee = async (id) => {
    const target = employees.find((e) => e.id === Number(id));
    await api.deleteEmployee(id);
    const targetName = target ? target.name : `ID ${id}`;
    logActivity(`Deleted employee: ${targetName}`, "delete");
    showToast(`Employee "${targetName}" deleted.`, "info");
    await refreshData();
  };

  const addDepartment = async (name) => {
    const trimmed = name.trim();
    const created = await api.createDepartment(trimmed);
    logActivity(`Created department: ${trimmed}`, "create");
    showToast(`Department "${trimmed}" added successfully.`, "success");
    await refreshData();
    return created;
  };

  const value = useMemo(() => ({
    user,
    login,
    logout,
    employees,
    departments,
    saveEmployee,
    deleteEmployee,
    addDepartment,
    refreshData,
    loading,
    appError,
    setAppError,
    backendOnline,
    toast,
    showToast,
    activityLog,
    logActivity
  }), [user, employees, departments, loading, appError, backendOnline, toast, activityLog, showToast, logActivity, refreshData]);

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
}

export function useApp() {
  const context = useContext(AppContext);
  if (!context) throw new Error("useApp must be used within AppProvider");
  return context;
}
