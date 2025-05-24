<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.leave.model.LeaveRequest" %>
<%
    String employeeName = (String) session.getAttribute("employeeName");
    List<LeaveRequest> leaveList = (List<LeaveRequest>) request.getAttribute("leaveList");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 20px;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 10px;
        }

        button {
            background-color: #007BFF;
            border: none;
            color: white;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            margin-right: 10px;
        }

        button:hover {
            background-color: #0056b3;
        }

        form input, form textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 15px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 10px;
            text-align: left;
        }

        .status-approved {
            color: green;
        }

        .status-rejected {
            color: red;
        }

        .logout-btn {
            background-color: #ff4d4d;
        }

        #toast {
            display: none;
            position: fixed;
            top: 20px;
            right: 20px;
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border-radius: 6px;
            z-index: 1000;
        }
    </style>
</head>
<body>
<div id="toast">Leave request submitted successfully!</div>

<div class="container">
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h2>Welcome, <%= employeeName %></h2>
        <form action="LogOutServlet" method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
    </div>

    <div style="margin-top: 20px;">
        <button onclick="showSection('dashboard')">Dashboard</button>
        <button onclick="showSection('requestForm')">Request Leave</button>
    </div>

    <!-- Leave History Table -->
    <div id="dashboardSection" style="margin-top: 20px;">
        <h3>Your Leave Requests</h3>
        <table>
            <thead>
                <tr><th>From</th><th>To</th><th>Reason</th><th>Status</th></tr>
            </thead>
            <tbody>
                <%
                    if (leaveList != null && !leaveList.isEmpty()) {
                        for (LeaveRequest leave : leaveList) {
                %>
                <tr>
                    <td><%= leave.getFromDate() %></td>
                    <td><%= leave.getToDate() %></td>
                    <td><%= leave.getReason() %></td>
                    <td class="status-<%= leave.getStatus().toLowerCase() %>"><%= leave.getStatus() %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="4">No leave requests found.</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <!-- Leave Request Form -->
    <div id="requestSection" style="display: none; margin-top: 20px;">
        <h3>Submit Leave Request</h3>
        <form method="post" action="LeaveRequestServlet">
            <label for="fromDate">From Date:</label>
            <input type="date" name="fromDate" required>

            <label for="toDate">To Date:</label>
            <input type="date" name="toDate" required>

            <label for="reason">Reason:</label>
            <textarea name="reason" rows="3" required></textarea>

            <button type="submit">Submit Request</button>
        </form>
    </div>
</div>

<script>
    function showSection(section) {
        const dashboard = document.getElementById('dashboardSection');
        const request = document.getElementById('requestSection');

        if (section === 'dashboard') {
            dashboard.style.display = 'block';
            request.style.display = 'none';
        } else {
            dashboard.style.display = 'none';
            request.style.display = 'block';
        }
    }

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get("requestSuccess") === "true") {
        document.getElementById("toast").style.display = "block";
        showSection("dashboard");
        setTimeout(() => {
            document.getElementById("toast").style.display = "none";
        }, 3000);
    }
</script>

</body>
</html>
