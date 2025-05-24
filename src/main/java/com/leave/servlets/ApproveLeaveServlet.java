
package com.leave.servlets;

import com.leave.dao.LeaveDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ApproveLeaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int leaveId = Integer.parseInt(request.getParameter("leaveId"));
        LeaveDAO.updateLeaveStatus(leaveId, "Approved");
        request.getRequestDispatcher("AdminDashboardServlet").forward(request, response);

    }
}


