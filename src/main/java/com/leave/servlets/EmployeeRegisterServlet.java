package com.leave.servlets;

import com.leave.dao.UserDAO;
import com.leave.model.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class EmployeeRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("📝 Received registration data: Name=" + name + ", Email=" + email + ", Password=" + password);

        // Validate input fields
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("employeeRegister.jsp").forward(request, response);
            return;
        }

        Employee emp = new Employee(name, email, password);
        boolean success = UserDAO.registerEmployee(emp);

        if (success) {
            System.out.println("✅ Registration successful for: " + email);
            // You can also set a success message if you want
            response.sendRedirect("employeeLogin.jsp");
        } else {
            System.out.println("❌ Registration failed for: " + email);
            request.setAttribute("errorMessage", "Registration failed. Email may already exist or other error occurred.");
            request.getRequestDispatcher("employeeRegister.jsp").forward(request, response);
        }
    }
}
