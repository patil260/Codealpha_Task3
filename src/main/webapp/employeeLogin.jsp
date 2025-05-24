<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
<% } %>

<!DOCTYPE html>
<html>
<head><title>Employee Login</title><link rel="stylesheet" href="styles.css"></head>
<body><div class="login-container">
    <h2>Employee Login</h2>
    <form action="EmployeeLoginServlet" method="post">
        <input type="email" name="email" placeholder="Email" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Login</button>
    </form></div></body></html>
