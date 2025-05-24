
package com.leave.dao;

import com.leave.model.LeaveRequest;
import com.leave.dao.DBConnection;
import java.sql.*;
import java.sql.Date;
import java.util.*;



public class LeaveDAO {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "");
    }
    
   
    public static List<LeaveRequest> getLeaveRequestsByEmployee(int empId) {
        List<LeaveRequest> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM leave_requests WHERE employee_id = ? ORDER BY from_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setFromDate(rs.getDate("from_date"));
                lr.setToDate(rs.getDate("to_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                list.add(lr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    
     
    


    public static List<LeaveRequest> getAllPendingRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM leave_requests WHERE status = 'Pending'";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaveRequest leave = new LeaveRequest(
                    rs.getInt("id"),
                    rs.getInt("employee_id"),
                    rs.getDate("from_date"),
                    rs.getDate("to_date"),
                    rs.getString("reason"),
                    rs.getString("status")
                );
                list.add(leave);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    
    
    public static List<LeaveRequest> getLeaveHistoryByEmployee(int empId) {
        List<LeaveRequest> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM leave_requests WHERE employee_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest(rs.getInt("id"), rs.getInt("employee_id"),
                        rs.getDate("from_date"), rs.getDate("to_date"),
                        rs.getString("reason"), rs.getString("status"));
                list.add(lr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
   
    
    
    public static void updateLeaveStatus(int leaveId, String newStatus) {
        String sql = "UPDATE leave_requests SET status = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, leaveId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    public static boolean submitLeaveRequest(LeaveRequest leaveRequest) {
        boolean result = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO leave_requests (employee_id, from_date, to_date, reason, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, leaveRequest.getEmployeeId());
            stmt.setDate(2, new java.sql.Date(leaveRequest.getFromDate().getTime()));
            stmt.setDate(3, new java.sql.Date(leaveRequest.getToDate().getTime()));
            stmt.setString(4, leaveRequest.getReason());
            stmt.setString(5, leaveRequest.getStatus());

            int rows = stmt.executeUpdate();
            result = rows > 0;
        } catch (Exception e) {
            System.out.println("❌ Error in submitLeaveRequest: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


}


