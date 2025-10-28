package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginGUI extends JFrame implements ActionListener {
    
    static {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}
        }
    }

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginButton;
    private JCheckBox showPasswordCheckbox;

    public LoginGUI() {
        setTitle("Secure Login / Student Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 240); 
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        userField = new JTextField(15);
        passField = new JPasswordField(15);
        loginButton = new JButton("Login / Register Student");
        loginButton.addActionListener(this);
        
        showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.setFocusable(false);
        showPasswordCheckbox.addActionListener(e -> {
            if (showPasswordCheckbox.isSelected()) {
                passField.setEchoChar((char) 0);
            } else {
                passField.setEchoChar('*');
            }
        });
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        add(new JLabel("Username (ID):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        add(passField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        add(showPasswordCheckbox, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);
        
        KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        
        InputMap inputMapUser = userField.getInputMap(JComponent.WHEN_FOCUSED);
        inputMapUser.put(enter, "loginAction");
        userField.getActionMap().put("loginAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { loginButton.doClick(); }
        });
        
        InputMap inputMapPass = passField.getInputMap(JComponent.WHEN_FOCUSED);
        inputMapPass.put(enter, "loginAction");
        passField.getActionMap().put("loginAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) { loginButton.doClick(); }
        });

        setLocationRelativeTo(null); 
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            handleLogin();
        }
    }
    
    private void handleLogin() {
        String username = userField.getText().trim();
        String password = new String(passField.getPassword());
        
        String role = AuthManager.authenticate(username, password);
        
        if (role != null) {
            AuthManager.setUsername(username);
            this.dispose(); 
            new FeedbackGUI(role); 
        } else {
            if (registerStudent(username, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Logged in as: STUDENT.", "Success", JOptionPane.INFORMATION_MESSAGE);
                AuthManager.setUsername(username);
                this.dispose(); 
                new FeedbackGUI("student");
            } else {
                JOptionPane.showMessageDialog(this, "Login Failed. Invalid credentials or username already taken.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean registerStudent(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        if (username.equalsIgnoreCase("admin") || username.equalsIgnoreCase("faculty")) {
            return false;
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'student')";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static void main(String[] args) {
        // Ensure this is the first thing that runs, creating the database and initial users
        DatabaseConnector.createTable(); 
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }
}