package com.example;

import java.sql.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:sqlite:feedback.db";

    public static Connection connect() throws SQLException {
        try { 
            Class.forName("org.sqlite.JDBC"); 
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver Error: Check if sqlite-jdbc-X.X.X.jar is in the lib folder and correctly referenced.", e);
        }
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        createUsersTable(); 
        createFormTemplatesTable();

        String sqlFeedback = "CREATE TABLE IF NOT EXISTS feedback ("
                           + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                           + " student_id TEXT NOT NULL,"
                           + " course_name TEXT NOT NULL,"
                           + " template_id INTEGER NOT NULL," 
                           + " rating1 INTEGER,"              
                           + " rating2 INTEGER,"
                           + " comment TEXT);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlFeedback); 
        } catch (SQLException e) {
            System.err.println("Error creating database tables: " + e.getMessage());
        }
    }
    
    private static void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ( username TEXT PRIMARY KEY, password TEXT NOT NULL, role TEXT NOT NULL);";
        String sqlInsertAdmin = "INSERT OR IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'admin');";
        String sqlInsertFaculty = "INSERT OR IGNORE INTO users (username, password, role) VALUES ('faculty', 'facpass', 'faculty');";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); 
            stmt.executeUpdate(sqlInsertAdmin); 
            stmt.executeUpdate(sqlInsertFaculty); 
        } catch (SQLException e) {
            System.err.println("Error creating users table: " + e.getMessage());
        }
    }
    
    private static void createFormTemplatesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS form_templates ("
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                   + " course TEXT NOT NULL UNIQUE,"
                   + " q1 TEXT NOT NULL," 
                   + " q2 TEXT NOT NULL);"; 
        
        String sql1 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Computational Statistics, Probability, and Calculus', 'Clarity of theoretical explanations (1-5)?', 'Effectiveness of problem-solving sessions (1-5)?');";
        String sql2 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Data Structure', 'Instructor''s ability to simplify complex algorithms (1-5)?', 'Laboratory exercises relevance and difficulty (1-5)?');";
        String sql3 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Computer Organization and Architecture', 'Coverage of fundamental hardware concepts (1-5)?', 'Balance between theory and practical architecture examples (1-5)?');";
        String sql4 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Discrete Mathematics', 'Use of real-world examples to illustrate concepts (1-5)?', 'Pacing and complexity of weekly assignments (1-5)?');";
        String sql5 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Fundamentals of Economics', 'Relevance of the course material to current events (1-5)?', 'Accessibility and clarity of the textbook (1-5)?');";
        String sql6 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Indian Knowledge System', 'Instructor''s depth of knowledge and passion (1-5)?', 'Overall value in broadening your perspective (1-5)?');";
        String sql7 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Object Oriented Programming Laboratory', 'Effectiveness of lab exercises in learning OOP concepts (1-5)?', 'Availability and helpfulness of lab assistants (1-5)?');";
        String sql8 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Data Structures Laboratory', 'Quality of data structure implementation tasks (1-5)?', 'Time allotted for completing lab assignments (1-5)?');";
        String sql9 = "INSERT OR IGNORE INTO form_templates (course, q1, q2) VALUES ('Digital Design Laboratory', 'Relevance of experiments to modern digital design (1-5)?', 'Adequacy of equipment and tools provided (1-5)?');";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql); 
            stmt.executeUpdate(sql1); 
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            stmt.executeUpdate(sql5);
            stmt.executeUpdate(sql6);
            stmt.executeUpdate(sql7);
            stmt.executeUpdate(sql8);
            stmt.executeUpdate(sql9);
        } catch (SQLException e) {
            System.err.println("Error creating form templates table: " + e.getMessage());
        }
    }
}