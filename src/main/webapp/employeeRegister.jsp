<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<% 
   String error = (String) request.getAttribute("errorMessage");
   if (error != null) { 
%>
    <p style="color:red;"><%= error %></p>
<% } %>



<!DOCTYPE html>
<html>
<head>
    <title>Employee Registration</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="login-container">
    <h2>Employee Registration</h2>
    <form action="EmployeeRegisterServlet" method="post">
        <input type="text" name="name" placeholder="Full Name" required />
        <input type="email" name="email" placeholder="Email" required />
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Register</button>
    </form>
    <p>Already registered? <a href="employeeLogin.jsp">Login here</a></p>
</div>
</body>
</html>
