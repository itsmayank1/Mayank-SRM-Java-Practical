<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring MVC Calculator - SRM Practical</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f4f8; margin: 0; padding: 40px; display: flex; justify-content: center; }
        .card { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); width: 440px; }
        h2 { color: #1e3a8a; margin-top: 0; text-align: center; }
        .subtitle { font-size: 13px; color: #64748b; text-align: center; margin-bottom: 24px; }
        .form-group { margin-bottom: 16px; }
        label { display: block; font-weight: 600; margin-bottom: 6px; color: #334155; font-size: 13px; }
        input[type="number"], select { width: 100%; padding: 10px; border: 1px solid #cbd5e1; border-radius: 6px; font-size: 14px; box-sizing: border-box; }
        .btn-group { display: flex; gap: 10px; margin-top: 20px; }
        button { flex: 1; padding: 12px; background: #2563eb; color: white; border: none; border-radius: 6px; font-weight: 600; cursor: pointer; font-size: 14px; }
        button:hover { background: #1d4ed8; }
        .btn-secondary { background: #64748b; text-decoration: none; text-align: center; display: inline-block; padding: 12px; border-radius: 6px; color: white; font-weight: 600; }
        .btn-secondary:hover { background: #475569; }
        .alert { padding: 12px; border-radius: 6px; margin-top: 20px; }
        .alert-success { background: #dcfce7; color: #166534; border: 1px solid #bbf7d0; }
        .alert-error { background: #fee2e2; color: #991b1b; border: 1px solid #fecaca; }
        .result-box { font-size: 18px; font-weight: bold; text-align: center; margin-top: 5px; }
    </style>
</head>
<body>
<div class="card">
    <h2>Spring MVC Calculator</h2>
    <div class="subtitle">SRM Institute of Science and Technology &bull; CSE Core</div>

    <form action="${pageContext.request.contextPath}/calculate" method="post">
        <div class="form-group">
            <label for="num1">First Number</label>
            <input type="number" step="any" id="num1" name="num1" value="${num1 != null ? num1 : ''}" required>
        </div>

        <div class="form-group">
            <label for="num2">Second Number</label>
            <input type="number" step="any" id="num2" name="num2" value="${num2 != null ? num2 : ''}" required>
        </div>

        <div class="form-group">
            <label for="operation">Select Operation</label>
            <select id="operation" name="operation">
                <option value="ADD" ${operation == 'ADD' ? 'selected' : ''}>Addition (+)</option>
                <option value="SUBTRACT" ${operation == 'SUBTRACT' ? 'selected' : ''}>Subtraction (-)</option>
                <option value="MULTIPLY" ${operation == 'MULTIPLY' ? 'selected' : ''}>Multiplication (×)</option>
                <option value="DIVIDE" ${operation == 'DIVIDE' ? 'selected' : ''}>Division (/)</option>
                <option value="MODULO" ${operation == 'MODULO' ? 'selected' : ''}>Modulo (%)</option>
                <option value="POWER" ${operation == 'POWER' ? 'selected' : ''}>Power (x^y)</option>
            </select>
        </div>

        <div class="btn-group">
            <button type="submit">Compute Result</button>
            <a href="${pageContext.request.contextPath}/calculator" class="btn-secondary">Reset</a>
        </div>
    </form>

    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-error">
            <strong>Error:</strong> <%= request.getAttribute("error") %>
        </div>
    <% } else if (request.getAttribute("result") != null) { %>
        <div class="alert alert-success">
            <div>Calculation Equation:</div>
            <div class="result-box">
                <%= request.getAttribute("num1") %> <%= request.getAttribute("symbol") %> <%= request.getAttribute("num2") %> = <%= request.getAttribute("result") %>
            </div>
        </div>
    <% } %>
</div>
</body>
</html>
