package com.leave.servlets;

import com.leave.dao.UserDAO;
import com.leave.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class EmployeeLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Login attempt with Email: " + email);

        Employee employee = UserDAO.validateLogin(email, password);

        if (employee != null && employee.getId() > 0) {
            System.out.println("Login success: " + employee.getName());
            HttpSession session = request.getSession();
            session.setAttribute("employee", employee); // ✅ Store full Employee object
            session.setAttribute("employeeName", employee.getName()); // (optional)

            response.sendRedirect("EmployeeDashboardServlet"); // ✅ Use servlet to load fresh data
        } else {
            System.out.println("Login failed");
            request.setAttribute("errorMessage", "Invalid credentials");
            request.getRequestDispatcher("employeeLogin.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("employeeLogin.jsp");
    }
}
