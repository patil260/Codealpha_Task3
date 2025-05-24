package com.leave.servlets;

import com.leave.dao.LeaveDAO;
import com.leave.model.LeaveRequest;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Optional: Admin session check (adjust attribute name accordingly)
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("adminLogin.jsp");
            return;
        }

        // Use static method correctly
        List<LeaveRequest> pendingRequests = LeaveDAO.getAllPendingRequests();
        request.setAttribute("pendingRequests", pendingRequests);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from form
        String leaveIdStr = request.getParameter("leaveId");
        String action = request.getParameter("action"); // "approve" or "reject"

        if (leaveIdStr != null && action != null) {
            try {
                int leaveRequestId = Integer.parseInt(leaveIdStr);
                String newStatus = action.equalsIgnoreCase("approve") ? "Approved" : "Rejected";

                LeaveDAO.updateLeaveStatus(leaveRequestId, newStatus);

                request.setAttribute("message", "Leave request " + newStatus.toLowerCase() + " successfully.");
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid leave request ID.");
            }
        } else {
            request.setAttribute("message", "Missing leave request ID or action.");
        }

        // Reload pending requests after update
        List<LeaveRequest> pendingRequests = LeaveDAO.getAllPendingRequests();
        request.setAttribute("pendingRequests", pendingRequests);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
