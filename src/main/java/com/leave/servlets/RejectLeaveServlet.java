
package com.leave.servlets;

import com.leave.dao.LeaveDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RejectLeaveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int leaveId = Integer.parseInt(request.getParameter("leaveId"));
        LeaveDAO.updateLeaveStatus(leaveId, "Rejected");
        request.getRequestDispatcher("AdminDashboardServlet").forward(request, response);

    }
}
