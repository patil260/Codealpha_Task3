package com.leave.servlets;

import com.leave.dao.AdminDAO;
import com.leave.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Read and trim inputs
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username != null) username = username.trim();
        if (password != null) password = password.trim();

        System.out.println(" Attempting Admin Login with Username: '" + username + "'");
        System.out.println(" Attempting Admin Login with Password: '" + password + "'");

        Admin admin = AdminDAO.validateLogin(username, password);

        if (admin != null) {
            System.out.println(" Admin Login Successful for: " + admin.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect("adminDashboard.jsp");
        } else {
            System.out.println(" Admin Login Failed for Username: " + username);
            request.setAttribute("errorMessage", "Invalid admin credentials");
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("adminLogin.jsp");
    }
}
