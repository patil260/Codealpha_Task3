package com.leave.servlets;

import com.leave.dao.LeaveDAO;
import com.leave.model.LeaveRequest;
import com.leave.model.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EmployeeDashboardServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	    if (session == null || session.getAttribute("employee") == null) {
	        response.sendRedirect("employeeLogin.jsp");
	        return;
	    }

	    Employee emp = (Employee) session.getAttribute("employee");
	    List<LeaveRequest> leaveList = LeaveDAO.getLeaveRequestsByEmployee(emp.getId());

	    // Optional: Set name to greet employee
	    session.setAttribute("employeeName", emp.getName());

	    request.setAttribute("leaveList", leaveList);
	    RequestDispatcher rd = request.getRequestDispatcher("employeeDashboard.jsp");
	    rd.forward(request, response);
	}

}
