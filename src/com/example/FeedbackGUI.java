package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Vector;

public class FeedbackGUI extends JFrame implements ActionListener { 

    private FeedbackManager manager;
    private String userRole; 
    
    // Student View Components
    private JComboBox<String> courseDropdown;
    private JComboBox<Object> ratingBox1, ratingBox2; 
    private JTextArea commentArea;
    private JButton submitButton;
    private JLabel questionLabel1, questionLabel2;
    private Map<Integer, String> availableCourses;
    private int selectedTemplateId = -1;

    // Report View Components
    private JTable feedbackTable; 
    private DefaultTableModel tableModel; 
    private JTextArea chartArea; 
    private JButton refreshReportButton;
    private JButton clearDataButton; 
    private JTextField deleteIdField;  
    private JButton deleteSelectedButton; 

    public FeedbackGUI(String role) {
        try {
            this.userRole = role;
            this.manager = new FeedbackManager();
            
            setTitle("Student Feedback System - Logged in as: " + role.toUpperCase());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            if (role.equals("student")) {
                tabbedPane.addTab("Submit Feedback", createSubmissionPanel());
            }
            
            if (role.equals("faculty") || role.equals("admin")) {
                tabbedPane.addTab("Consolidated Report", createReportPanel());
                
                tabbedPane.addChangeListener(e -> {
                    if (tabbedPane.getSelectedIndex() != -1 && tabbedPane.getTitleAt(tabbedPane.getSelectedIndex()).contains("Report")) {
                        if (tableModel != null) {
                            refreshReport();
                        }
                    }
                });
            }
            
            add(tabbedPane, BorderLayout.CENTER);
            setLocationRelativeTo(null);
            
            pack();
            
            setVisible(true);
            
        } catch (Exception e) {
            System.err.println("\n!!! FATAL ERROR DURING GUI INITIALIZATION !!!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "A fatal error occurred. Check the console for details.", "Startup Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    private JPanel createSubmissionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        gbc.fill = GridBagConstraints.NONE; 
        
        availableCourses = manager.getCourseTemplates();
        Vector<String> courseNames = new Vector<>(availableCourses.values());
        
        courseNames.insertElementAt("Select Course", 0);
        
        Font boldFont = new Font("Segoe UI", Font.BOLD, 12); 

        // 1. Course Selection Row
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0; 
        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setFont(boldFont); 
        formPanel.add(courseLabel, gbc); 

        gbc.gridx = 1; 
        gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        
        courseDropdown = new JComboBox<>(courseNames);
        courseDropdown.setMaximumRowCount(10); 
        courseDropdown.setPreferredSize(new Dimension(500, 30)); 
        courseDropdown.setSelectedIndex(0); 
        
        courseDropdown.addActionListener(e -> loadTemplateQuestions());
        formPanel.add(courseDropdown, gbc); 
        
        gbc.fill = GridBagConstraints.NONE;

        // --- Rating Box Data Setup (Used for both Q1 and Q2) ---
        Vector<Object> ratingOptions = new Vector<>();
        ratingOptions.add("Select Rating"); 
        ratingOptions.add(5);
        ratingOptions.add(4);
        ratingOptions.add(3);
        ratingOptions.add(2);
        ratingOptions.add(1);

        // 2. Question 1 Row
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; 
        questionLabel1 = new JLabel("Question 1:"); 
        questionLabel1.setFont(boldFont); 
        formPanel.add(questionLabel1, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        ratingBox1 = new JComboBox<>(ratingOptions); 
        ratingBox1.setSelectedIndex(0); 
        formPanel.add(ratingBox1, gbc);

        gbc.fill = GridBagConstraints.NONE; 

        // 3. Question 2 Row
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0; 
        questionLabel2 = new JLabel("Question 2:"); 
        questionLabel2.setFont(boldFont); 
        formPanel.add(questionLabel2, gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        ratingBox2 = new JComboBox<>(ratingOptions); 
        ratingBox2.setSelectedIndex(0); 
        formPanel.add(ratingBox2, gbc);

        // 4. Comments Label 
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        JLabel commentLabel = new JLabel("Additional Comments:");
        commentLabel.setFont(boldFont); 
        formPanel.add(commentLabel, gbc);
        
        // 5. Comment Area 
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridheight = 2; gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        commentArea = new JTextArea(5, 40);
        JScrollPane scrollPane = new JScrollPane(commentArea);
        formPanel.add(scrollPane, gbc);

        // 6. Submit Button Row 
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridheight = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weighty = 0;
        submitButton = new JButton("Submit Feedback");
        
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        submitButton.setBackground(new Color(46, 204, 113)); 
        submitButton.setForeground(Color.BLACK);             
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false); 
        
        submitButton.addActionListener(this);
        formPanel.add(submitButton, gbc);

        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private void loadTemplateQuestions() {
        String selectedItem = (String) courseDropdown.getSelectedItem();
        
        if (selectedItem == null || selectedItem.equals("Select Course")) {
            selectedTemplateId = -1;
            questionLabel1.setText("Question 1:"); 
            questionLabel2.setText("Question 2:"); 
            return;
        }
        
        selectedTemplateId = availableCourses.entrySet().stream()
                .filter(entry -> entry.getValue().equals(selectedItem))
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);

        if (selectedTemplateId != -1) {
            Map<String, String> questions = manager.getTemplateQuestions(selectedTemplateId);
            questionLabel1.setText(questions.getOrDefault("q1", "Question 1 (Default):"));
            questionLabel2.setText(questions.getOrDefault("q2", "Question 2 (Default):"));
        }
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Table Setup (Top Section - Data Grid)
        String[] columnNames = {"ID", "Course Name", "Q1 Rating", "Q2 Rating", "Comments (Truncated)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0 || columnIndex == 2 || columnIndex == 3) {
                    return Integer.class; 
                }
                return String.class;
            }
        };
        
        feedbackTable = new JTable(tableModel);
        feedbackTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        feedbackTable.setAutoCreateRowSorter(true); 
        
        // --- JTable Column Sizing Fixes ---
        TableColumn idColumn = feedbackTable.getColumnModel().getColumn(0);
        idColumn.setPreferredWidth(30); idColumn.setMaxWidth(50); 
        TableColumn courseColumn = feedbackTable.getColumnModel().getColumn(1);
        courseColumn.setPreferredWidth(350); 
        TableColumn q1Column = feedbackTable.getColumnModel().getColumn(2);
        q1Column.setPreferredWidth(70); q1Column.setMaxWidth(80);
        TableColumn q2Column = feedbackTable.getColumnModel().getColumn(3);
        q2Column.setPreferredWidth(70); q2Column.setMaxWidth(80);
        TableColumn commentColumn = feedbackTable.getColumnModel().getColumn(4);
        commentColumn.setPreferredWidth(250); 
        
        feedbackTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        feedbackTable.setFillsViewportHeight(true); 

        JScrollPane tableScrollPane = new JScrollPane(feedbackTable);
        tableScrollPane.setPreferredSize(new Dimension(900, 300)); 
        
        panel.add(tableScrollPane, BorderLayout.NORTH);
        
        // 2. Chart/Summary Setup 
        chartArea = new JTextArea();
        chartArea.setEditable(false);
        chartArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        chartArea.setBorder(BorderFactory.createTitledBorder("Statistical Summary and Charts"));
        panel.add(new JScrollPane(chartArea), BorderLayout.CENTER);
        
        // 3. Control Panel Setup 
        JPanel controlPanel = new JPanel(); 
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS)); 
        
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        refreshReportButton = new JButton("Refresh Data");
        refreshReportButton.addActionListener(this);
        refreshPanel.add(refreshReportButton); 
        
        if (userRole.equals("admin")) { 
            clearDataButton = new JButton("CLEAR ALL FEEDBACK DATA");
            clearDataButton.addActionListener(this);
            refreshPanel.add(clearDataButton); 
            
            JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            deletePanel.setBorder(BorderFactory.createTitledBorder("Admin Controls"));
            
            deleteIdField = new JTextField(5);
            deleteSelectedButton = new JButton("Delete Feedback by ID");
            deleteSelectedButton.addActionListener(this);
            
            deletePanel.add(new JLabel("Enter Feedback ID:"));
            deletePanel.add(deleteIdField);
            deletePanel.add(deleteSelectedButton);
            
            JTextField deleteUserField = new JTextField(10);
            JButton deleteUserButton = new JButton("Delete Student Account");
            deleteUserButton.addActionListener(e -> handleDeleteStudentAccount(deleteUserField.getText()));

            deletePanel.add(new JLabel("Student ID/Username:"));
            deletePanel.add(deleteUserField);
            deletePanel.add(deleteUserButton);

            controlPanel.add(deletePanel);
        }
        
        controlPanel.add(refreshPanel); 
        panel.add(controlPanel, BorderLayout.SOUTH); 
        
        refreshReport(); 
        
        return panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            handleSubmission();
        } else if (e.getSource() == refreshReportButton) {
            refreshReport();
        } else if (userRole.equals("admin") && e.getSource() == clearDataButton) { 
            handleClearData();
        } else if (userRole.equals("admin") && e.getSource() == deleteSelectedButton) { 
            handleDeleteSelected();
        }
    }
    
    private void handleSubmission() {
        try { 
            if (selectedTemplateId == -1 || 
                courseDropdown.getSelectedIndex() == 0 || 
                ratingBox1.getSelectedIndex() == 0 || 
                ratingBox2.getSelectedIndex() == 0) 
            {
                JOptionPane.showMessageDialog(this, "Error: Please select a valid Course and ratings (1-5).", "Submission Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String studentId = AuthManager.getUsername(); 
            String course = (String) courseDropdown.getSelectedItem();
            int currentTemplateId = this.selectedTemplateId; 
            
            int rating1 = (Integer) ratingBox1.getSelectedItem(); 
            int rating2 = (Integer) ratingBox2.getSelectedItem(); 
            String comment = commentArea.getText().trim();
            
            if (studentId.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Login session expired or course not selected.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Feedback newFeedback = new Feedback(studentId, course, currentTemplateId, rating1, rating2, comment); 
            manager.submitFeedback(newFeedback);
            
            // Check if there was an error printout (a slight delay helps the console catch up)
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}

            JOptionPane.showMessageDialog(this, "Feedback submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // RESET UI
            commentArea.setText("");
            courseDropdown.setSelectedIndex(0); 
            ratingBox1.setSelectedIndex(0);     
            ratingBox2.setSelectedIndex(0);     
            loadTemplateQuestions(); 

        } catch (Exception ex) {
            System.err.println("\n!!! ERROR during Feedback Submission or UI Reset !!!");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An unexpected error occurred during submission. Check the console for database details.", "Submission Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshReport() {
        if (tableModel == null) { return; }

        try {
            Vector<Vector<Object>> tableData = manager.getReportTableData(); 
            tableModel.setRowCount(0);
            
            for (Vector<Object> row : tableData) {
                tableModel.addRow(row);
            }

            String summaryContent = manager.generateReportSummary(tableData); 
            chartArea.setText(summaryContent);
            chartArea.setCaretPosition(0); 
            
        } catch (Exception e) {
            System.err.println("\n!!! ERROR during Report Refresh !!!");
            e.printStackTrace();
            if (chartArea != null) { chartArea.setText("Error generating report. Check console for details."); }
        }
    }

    private void handleClearData() {
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "WARNING: This will permanently delete ALL submitted student feedback.\nAre you sure you want to proceed?", 
            "Confirm Data Deletion", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.clearAllFeedback()) {
                JOptionPane.showMessageDialog(this, "All student feedback data has been successfully deleted.", "Data Cleared", JOptionPane.INFORMATION_MESSAGE);
                refreshReport(); 
            } else {
                JOptionPane.showMessageDialog(this, "Error clearing feedback data.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleDeleteSelected() {
        if (!userRole.equals("admin")) return;

        try {
            int idToDelete = Integer.parseInt(deleteIdField.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Are you sure you want to delete feedback entry with ID: " + idToDelete + "?", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                if (manager.deleteFeedbackById(idToDelete)) {
                    JOptionPane.showMessageDialog(this, "Feedback ID " + idToDelete + " successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    deleteIdField.setText("");
                    refreshReport();
                } else {
                    JOptionPane.showMessageDialog(this, "Deletion Failed. ID " + idToDelete + " not found or database error.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleDeleteStudentAccount(String username) {
        if (!userRole.equals("admin") || username.trim().isEmpty()) return;

        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "WARNING: This will permanently delete the student account: " + username + ".\nAre you sure you want to proceed?", 
            "Confirm Account Deletion", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (AuthManager.deleteStudentUser(username)) {
                JOptionPane.showMessageDialog(this, "Student account '" + username + "' successfully deleted. Their submitted feedback remains until cleared separately.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Deletion Failed. User '" + username + "' not found or database error. Only 'student' accounts can be deleted.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}