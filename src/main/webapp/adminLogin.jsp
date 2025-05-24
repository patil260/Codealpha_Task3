<%
    if (session.getAttribute("admin") != null) {
        response.sendRedirect("adminDashboard.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="login-container">
    <h2>Admin Login</h2>

    <% String error = (String) request.getAttribute("errorMessage"); %>
    <% if (error != null) { %>
        <p style="color:red;"><%= error %></p>
    <% } %>

    <form action="AdminLoginServlet" method="post">
        <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
    </form>
</div>
</body>
</html>



