package com.example;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class FeedbackManager {
    
    public Map<Integer, String> getCourseTemplates() {
        Map<Integer, String> templates = new TreeMap<>();
        String sql = "SELECT id, course FROM form_templates ORDER BY course";
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { templates.put(rs.getInt("id"), rs.getString("course")); }
        } catch (SQLException e) { System.err.println("Error loading templates: " + e.getMessage()); }
        return templates;
    }

    public Map<String, String> getTemplateQuestions(int templateId) {
        Map<String, String> questions = new HashMap<>();
        String sql = "SELECT q1, q2 FROM form_templates WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, templateId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { questions.put("q1", rs.getString("q1")); questions.put("q2", rs.getString("q2")); }
            }
        } catch (SQLException e) { System.err.println("Error loading questions: " + e.getMessage()); }
        return questions;
    }

    public void submitFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback(student_id, course_name, template_id, rating1, rating2, comment) VALUES(?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, feedback.studentId);
            pstmt.setString(2, feedback.courseName);
            pstmt.setInt(3, feedback.templateId);
            pstmt.setInt(4, feedback.rating1);
            pstmt.setInt(5, feedback.rating2);
            pstmt.setString(6, feedback.comment);
            
            pstmt.executeUpdate(); 
        } catch (SQLException e) {
            // CRITICAL ERROR LOGGING
            System.err.println("\n--- SQL SUBMISSION FAILED for Student: " + feedback.studentId + " ---");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    public boolean clearAllFeedback() {
        String sqlDelete = "DELETE FROM feedback";
        String sqlReset = "DELETE FROM sqlite_sequence WHERE name='feedback'";
        
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(sqlDelete);
            stmt.executeUpdate(sqlReset); 
            
            return true;
        } catch (SQLException e) {
            System.err.println("Error clearing feedback data and resetting sequence: " + e.getMessage());
            return false;
        }
    }
    
    public boolean deleteFeedbackById(int feedbackId) {
        String sql = "DELETE FROM feedback WHERE id = ?";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, feedbackId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting feedback by ID: " + e.getMessage());
            return false;
        }
    }

    public Vector<Vector<Object>> getReportTableData() {
        String sql = "SELECT id, course_name, rating1, rating2, comment FROM feedback ORDER BY id ASC";
        Vector<Vector<Object>> tableData = new Vector<>();
        
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("course_name"));
                row.add(rs.getInt("rating1"));
                row.add(rs.getInt("rating2"));
                
                String comment = rs.getString("comment");
                row.add(comment.length() > 50 ? comment.substring(0, 47) + "..." : comment); 
                
                tableData.add(row);
            }
        } catch (SQLException e) { 
            System.err.println("Error generating table data: " + e.getMessage());
        }
        return tableData;
    }
    
    public String generateReportSummary(Vector<Vector<Object>> rawTableData) {
        if (rawTableData.isEmpty()) return "No feedback data available. Submit some feedback first.";
        
        List<Map<String, Object>> records = new ArrayList<>();
        for (Vector<Object> row : rawTableData) {
            Map<String, Object> record = new HashMap<>();
            record.put("course_name", row.get(1));
            record.put("rating1", row.get(2));
            record.put("rating2", row.get(3));
            records.add(record);
        }

        StringBuilder report = new StringBuilder();
        report.append("==================================================\nSTATISTICAL SUMMARY & CHARTS\n==================================================\n\n");
        
        Map<String, List<Integer>> courseRating1 = new HashMap<>();
        Map<String, List<Integer>> courseRating2 = new HashMap<>();
        
        for(Map<String, Object> rec : records) {
            String course = (String) rec.get("course_name");
            courseRating1.computeIfAbsent(course, k -> new ArrayList<>()).add((Integer) rec.get("rating1"));
            courseRating2.computeIfAbsent(course, k -> new ArrayList<>()).add((Integer) rec.get("rating2"));
        }
        
        // 1. Course Averages
        report.append("COURSE AVERAGES\n--------------------------------------------------\n");
        for (String course : courseRating1.keySet()) {
            double avg1 = courseRating1.get(course).stream().mapToInt(val -> val).average().orElse(0.0);
            double avg2 = courseRating2.get(course).stream().mapToInt(val -> val).average().orElse(0.0);
            report.append(String.format("Course: %s\n", course));
            report.append(String.format("   Total Submissions: %d | Avg Q1 Rating: %.2f | Avg Q2 Rating: %.2f\n", 
                                courseRating1.get(course).size(), avg1, avg2));
            report.append("--------------------------------------------------\n");
        }
        
        // 2. Overall Rating Distribution Chart
        report.append("\nOVERALL RATING DISTRIBUTION (Question 1)\n--------------------------------------------------\n");
        Map<Integer, Long> q1Counts = records.stream().collect(Collectors.groupingBy(r -> (Integer)r.get("rating1"), Collectors.counting()));
        
        for (int r = 5; r >= 1; r--) {
            long count = q1Counts.getOrDefault(r, 0L);
            String bar = "█".repeat(Math.min((int) count, 20)); 
            report.append(String.format("Rating %d (%d submissions): %s\n", r, count, bar));
        }
        report.append("--------------------------------------------------\n\nSummary End.");
        return report.toString();
    }
}