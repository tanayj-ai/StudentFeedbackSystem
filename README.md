# 📚 Student Feedback System — Modern Java Swing Desktop App

A secure, role-based **desktop application for collecting, storing, and analyzing student feedback**, built entirely with **Java Swing** and utilizing **SQLite** for data persistence.

This project focuses on **improving institutional transparency** by providing automated, real-time reports to faculty and administration.

> 🎯 Designed to replace inefficient manual feedback systems by ensuring **confidentiality** and providing **data-driven insights**.

***

## 🚀 Features at a Glance

* 👤 **Role-Based Access** — Secure login portals for **Student, Faculty, and Admin** user types.
* 📝 **Dynamic Forms** — Feedback questions are dynamically loaded and **customizable per course**.
* 🔒 **Data Confidentiality** — System ensures **anonymity** for all student responses.
* 📊 **Automated Reporting** — Generates **consolidated reports** with calculated course averages and rating distributions for decision-making.
* 🔧 **Admin Controls** — Specialized functionality for **clearing all feedback data** and user management.
* 🗄 **SQLite Persistence** — Data is stored locally in an efficient SQLite database via JDBC.

---

## 🖼 GUI Snapshots (Output Screens)

This section highlights the key interfaces accessible to different user roles, demonstrating the system's core functionality.

| Feature | User Role | Description | Screenshot |
| :--- | :--- | :--- | :--- |
| **Submission Form** | **STUDENT** | The dynamic interface showing fields for course selection, ratings, and comments. | <img width="350" alt="Student Feedback Submission Form" src="https://github.com/user-attachments/assets/054feb48-22a5-49c5-a53b-dd8e7d031cd3" /> |
| **Admin Report View** | **ADMIN** | The full reporting dashboard, including data tables, statistics, and **Admin Control buttons**. | <img width="350" alt="Admin Consolidated Report View" src="https://github.com/user-attachments/assets/ff37f40c-827a-403c-a2be-bac21aa01aa0" /> |
| **Faculty Report View** | **FACULTY** | The read-only report view accessible to faculty, showing data relevant to their courses. | <img width="350" alt="Faculty Consolidated Report View" src="https://github.com/user-attachments/assets/535d6aa4-dc2f-4d1e-a13e-e3f0eebc5609" /> |

---

## 🛠 Tech Stack

| Layer | Technology | Purpose |
| :--- | :--- | :--- |
| Language | **Java** (JDK 8+) | Core programming logic using OOP principles. |
| UI Framework | **Java Swing** | Used for the desktop Graphical User Interface. |
| Database | **SQLite** | Lightweight, file-based persistence (`feedback.db`). |
| Connectivity | **JDBC** | Java Database Connectivity for communication with SQLite. |
| Dependency | `sqlite-jdbc-3.50.3.0.jar` | Required SQLite driver. |

---

## ⚙️ Installation and Execution Instructions

### Prerequisites
1.  Java Development Kit (JDK 17+ recommended).
2.  Ensure the SQLite JDBC driver JAR file is present in the `lib` folder.

### Running the Project
1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/tanayj-ai/StudentFeedbackSystem.git](https://github.com/tanayj-ai/StudentFeedbackSystem.git)
    cd StudentFeedbackSystem
    ```
2.  **Compile the Code:**
    ```bash
    # Run this from the project root directory
    javac -cp "lib/*" -d bin src/com/example/*.java
    ```
3.  **Run the Application:** Execution begins with the `LoginGUI` class.
    ```bash
    java -cp "bin/*;lib/*" com.example.LoginGUI
    ```

### Default Login Credentials

| Username | Password | Role | Notes |
| :--- | :--- | :--- | :--- |
| **admin** | **admin123** | Admin | Has full data control and can clear feedback records. |
| **faculty** | **facpass** | Faculty | Can view reports and statistics, but cannot alter data. |
| *[New ID]* | *[New Password]* | Student | Users are auto-registered upon first login attempt. |
