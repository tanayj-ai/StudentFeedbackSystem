# 📚 Student Feedback System (OOPL Mini Project)

## Project Overview

This project implements a **secure, role-based desktop application** designed for the **Object-Oriented Programming Laboratory (Semester - III)** Mini Project. It digitizes the process of collecting, storing, and analyzing student feedback on courses, faculty, and facilities.

The system replaces inefficient manual methods by ensuring **anonymity** and providing **real-time statistical reports** to support administrative decision-making.

| Role | Name | Roll Number |
| :--- | :--- | :--- |
| Developer | **Mday Farhad** | 16015024080 |
| Developer | **Tanay Jobanputra** | 16015024032 |

***

## ✨ Key System Features

* **Role-Based Access Control:** Provides secure login for **Student, Faculty, and Admin** user roles.
* **Dynamic Forms:** Feedback forms are **customizable per course** or department. The GUI dynamically loads relevant questions based on the course selected.
* **Data Confidentiality:** Ensures **confidentiality and anonymity** of all student responses (Objective 2).
* **Automated Reporting:** Generates **consolidated reports** with calculated **course average ratings** and overall **rating distributions** for quality analysis.
* **Database Management:** Admin controls are available for maintenance tasks, including **clearing all feedback data**.

***

## 💻 Technologies Used

| Category | Technology | Purpose |
| :--- | :--- | :--- |
| **Language** | **Java** | Core programming using OOP principles. |
| **Interface** | **Java Swing** | Used to design the Graphical User Interface (GUI). |
| **Database** | **SQLite** | Lightweight, file-based database for persistent storage (`feedback.db`). |
| **Connectivity**| **JDBC** | Java Database Connectivity for database operations. |
| **Dependency**| `sqlite-jdbc-3.50.3.0.jar` | JDBC Driver required for SQLite connection. |

***

## ⚙️ Installation and Execution Instructions

### Prerequisites
1.  Java Development Kit (JDK 17+ recommended).
2.  The SQLite JDBC driver JAR file must be in the `lib` folder.

### Running the Project
1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/tanayj-ai/StudentFeedbackSystem.git](https://github.com/tanayj-ai/StudentFeedbackSystem.git)
    cd StudentFeedbackSystem
    ```
2.  **Compile the Code:**
    ```bash
    # Ensure the JAR is present in the 'lib' folder
    javac -cp "lib/*" -d bin src/com/example/*.java
    ```
3.  **Run the Application:**
    ```bash
    java -cp "bin/*;lib/*" com.example.LoginGUI
    ```

### Default Login Credentials
| Username | Password | Role |
| :--- | :--- | :--- |
| **admin** | **admin123** | Admin (Full control) |
| **faculty** | **facpass** | Faculty (View reports) |
| *[Any New ID]* | *[Any Password]* | Student (Auto-registers upon first login attempt) |

***

## 🖼️ GUI Snapshots

| Feature | Description |
| :--- | :--- |
| **Project File Structure** | Organization of source code (`src`), compiled files (`bin`), and libraries (`lib`). |
| **Student Submission Form** | Demonstrates the dynamic loading of course-specific questions (Q1 and Q2) based on selection. |
| **Faculty/Admin Report View** | Shows the consolidated table of records alongside the calculated statistical summary (Course Averages and Distribution). |
| **Admin Controls** | Highlights the specialized buttons for administrative tasks, such as clearing all feedback records. |

***

## 📄 Project Documentation

* **Full Report:** [OOPL Mini Project Report Format.docx] (Detailed analysis, design, and conclusion).
* **Problem Statement:** [Problem statement Mini Project.pdf] (Original document defining objectives and functional requirements).
