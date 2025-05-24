package com.leave.servlets;

import com.leave.dao.LeaveDAO;
import com.leave.model.LeaveRequest;
import com.leave.model.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;

public class LeaveRequestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("employee") == null) {
            response.sendRedirect("employeeLogin.jsp");
            return;
        }

        Employee emp = (Employee) session.getAttribute("employee");

        LeaveRequest lr = new LeaveRequest();
        lr.setEmployeeId(emp.getId());
        lr.setFromDate(Date.valueOf(request.getParameter("fromDate")));
        lr.setToDate(Date.valueOf(request.getParameter("toDate")));
        lr.setReason(request.getParameter("reason"));
        lr.setStatus("Pending");

        // ✅ Use correct method here
        boolean ok = LeaveDAO.submitLeaveRequest(lr);

        if (ok) {
            response.sendRedirect("EmployeeDashboardServlet?requestSuccess=true");
        } else {
            response.sendRedirect("EmployeeDashboardServlet?requestSuccess=false");
        }
    }
}
