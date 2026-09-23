<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring MVC Portal - SRM Full Stack Practical</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f8fafc; margin: 0; padding: 40px; display: flex; justify-content: center; }
        .container { background: white; max-width: 580px; width: 100%; padding: 36px; border-radius: 12px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); text-align: center; }
        h1 { color: #1e3a8a; margin-top: 0; font-size: 26px; }
        p.subtitle { color: #64748b; font-size: 15px; margin-bottom: 30px; }
        .feature-box { background: #f1f5f9; padding: 20px; border-radius: 8px; text-align: left; margin-bottom: 24px; border-left: 4px solid #3b82f6; }
        .feature-box h3 { margin: 0 0 8px 0; color: #1e293b; }
        .feature-box p { margin: 0; color: #475569; font-size: 14px; }
        .btn { display: inline-block; background: #2563eb; color: white; padding: 12px 28px; text-decoration: none; border-radius: 6px; font-weight: 600; font-size: 15px; }
        .btn:hover { background: #1d4ed8; }
        .footer { margin-top: 30px; font-size: 12px; color: #94a3b8; }
    </style>
</head>
<body>
<div class="container">
    <h1>Spring MVC Web Application</h1>
    <p class="subtitle">SRM Institute of Science & Technology &bull; Java Full Stack Practical</p>

    <div class="feature-box">
        <h3>Interactive Calculator Web Module</h3>
        <p>Demonstrates Spring Web MVC architecture with <code>DispatcherServlet</code>, Controller mapping (<code>@GetMapping</code>, <code>@PostMapping</code>), and Model-View data binding.</p>
    </div>

    <a href="${pageContext.request.contextPath}/calculator" class="btn">Launch Web Calculator &rarr;</a>

    <div class="footer">
        Configured with Tomcat Maven Plugin &bull; Spring Framework 6.1.6
    </div>
</div>
</body>
</html>
