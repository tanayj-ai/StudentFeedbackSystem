package com.example;

import java.sql.*;

public class AuthManager {
    
    private static String currentUsername = "";

    public static String authenticate(String username, String password) {
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }
    
    public static void setUsername(String username) {
        currentUsername = username;
    }
    public static String getUsername() {
        return currentUsername;
    }
    
    public static boolean deleteStudentUser(String username) {
        String sql = "DELETE FROM users WHERE username = ? AND role = 'student'";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            int rowsAffected = pstmt.executeUpdate();
            
            return rowsAffected == 1;
        } catch (SQLException e) {
            System.err.println("Error deleting student user: " + e.getMessage());
            return false;
        }
    }
}