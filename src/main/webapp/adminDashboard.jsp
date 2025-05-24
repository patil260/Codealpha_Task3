<%@ page import="java.util.List" %>
<%@ page import="com.leave.model.LeaveRequest" %>
<%
    List<LeaveRequest> pendingRequests = (List<LeaveRequest>) request.getAttribute("pendingRequests");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h2>Admin - Pending Leave Requests</h2>

<% if (message != null) { %>
    <p style="color:green;"><%= message %></p>
<% } %>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>Employee ID</th>
        <th>From</th>
        <th>To</th>
        <th>Reason</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <% if (pendingRequests != null && !pendingRequests.isEmpty()) {
        for (LeaveRequest l : pendingRequests) { %>
        <tr>
            <td><%= l.getEmployeeId() %></td>
            <td><%= l.getFromDate() %></td>
            <td><%= l.getToDate() %></td>
            <td><%= l.getReason() %></td>
            <td><%= l.getStatus() %></td>
            <td>
                <form action="AdminDashboardServlet" method="post" style="display:inline;">
                    <input type="hidden" name="leaveId" value="<%= l.getId() %>" />
                    <input type="hidden" name="action" value="approve" />
                    <button type="submit">Approve</button>
                </form>
                <form action="AdminDashboardServlet" method="post" style="display:inline;">
                    <input type="hidden" name="leaveId" value="<%= l.getId() %>" />
                    <input type="hidden" name="action" value="reject" />
                    <button type="submit">Reject</button>
                </form>
            </td>
        </tr>
    <% } } else { %>
        <tr><td colspan="6" style="text-align:center;">No pending leave requests.</td></tr>
    <% } %>
</table>
</body>
</html>
