package com.leave.dao;

import com.leave.model.Admin;

import java.sql.*;

public class AdminDAO {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "Ssp$$sql26");
    }

    public static Admin validateLogin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Assuming admin table has id, username, password columns
                int id = rs.getInt("id");
                String uname = rs.getString("username");
                String pass = rs.getString("password");

                return new Admin(id, uname, pass);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // login failed
    }
}
