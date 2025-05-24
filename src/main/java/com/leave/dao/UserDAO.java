package com.leave.dao;

import com.leave.model.Employee;
import java.sql.*;

public class UserDAO {

    // Method to get DB connection with driver explicitly loaded
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the MySQL driver
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found!");
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
    }

    // Validate login credentials
    public static Employee validateLogin(String email, String password) {
        Employee emp = null;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM employees WHERE email=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            System.out.println("❌ Error during login validation:");
            e.printStackTrace();
        }
        return emp;
    }

    // Register new employee
    public static boolean registerEmployee(Employee emp) {
        boolean result = false;
        try (Connection con = getConnection()) {
            String sql = "INSERT INTO employees (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPassword());

            System.out.println("📝 Received registration data: Name=" + emp.getName() + ", Email=" + emp.getEmail());

            int rows = ps.executeUpdate();
            result = rows > 0;

            if (result) {
                System.out.println("✅ Registration successful for: " + emp.getEmail());
            } else {
                System.out.println("❌ Registration failed: No rows inserted.");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Duplicate entry: Email may already exist.");
        } catch (SQLException e) {
            System.out.println("❌ SQL Error Occurred");
            System.out.println("Message: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ General Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
