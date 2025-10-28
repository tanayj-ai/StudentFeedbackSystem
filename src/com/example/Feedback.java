package com.example;

public class Feedback {
    public String studentId;
    public String courseName;
    public int templateId; 
    public int rating1;    
    public int rating2;    
    public String comment;

    public Feedback(String studentId, String courseName, int templateId, int rating1, int rating2, String comment) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.templateId = templateId;
        this.rating1 = rating1;
        this.rating2 = rating2;
        this.comment = comment;
    }
}