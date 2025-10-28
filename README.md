# 📚 Student Feedback System (OOPL Mini Project)

## Project Overview

[cite_start]This is a **secure, role-based desktop application** developed for the **Object-Oriented Programming Laboratory (Semester - III)**[cite: 5, 138]. [cite_start]It serves as a digital platform to collect, store, and analyze student feedback on courses, faculty, and facilities[cite: 49, 97].

[cite_start]The system replaces inefficient manual methods [cite: 54, 56] [cite_start]by providing **anonymity** [cite: 76] [cite_start]and generating **real-time statistical reports** for administrative decision-making[cite: 81].

| Role | Name | Roll Number |
| :--- | :--- | :--- |
| Developer | **Mday Farhad** | [cite_start]16015024080 [cite: 129, 131] |
| Developer | **Tanay Jobanputra** | [cite_start]16015024032 [cite: 129, 132] |

***

## ✨ Key System Features

* [cite_start]**Role-Based Access Control:** Provides secure login for **Student, Faculty, and Admin** user roles[cite: 117, 122, 123].
* **Dynamic Forms:** Feedback forms are **customizable per course** or department. The GUI dynamically loads relevant questions (e.g., Q1, Q2) based on the course selected.
* [cite_start]**Data Confidentiality:** Ensures **confidentiality and anonymity** of all student responses[cite: 76].
* [cite_start]**Automated Reporting:** Generates **consolidated reports** with calculated **course average ratings** and overall **rating distributions**[cite: 126].
* **Database Management:** Admin controls are included for maintenance tasks, such as **clearing all feedback data**.

***

## 💻 Technologies Used

| Category | Technology | Purpose |
| :--- | :--- | :--- |
| **Language** | **Java** | [cite_start]Core programming using Object-Oriented principles[cite: 29]. |
| **Interface** | **Java Swing** | [cite_start]Used to design the Graphical User Interface (GUI)[cite: 23, 156]. |
| **Database** | **SQLite** | Lightweight, file-based database for persistent storage (`feedback.db`). |
| **Connectivity**| **JDBC** | Java Database Connectivity for database operations. |
| **Dependency**| `sqlite-jdbc-3.50.3.0.jar` | JDBC Driver required for SQLite connection. |

***

## ⚙️ Installation and Execution Instructions

### Prerequisites
1.  Java Development Kit (JDK 17+ recommended).
2.  The SQLite JDBC driver JAR file must be present in the `lib` folder.

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

## 🖼️ GUI Snapshots (Output Screens)

| Feature | User Role | Description | Screenshot |
| :--- | :--- | :--- | :--- |
| **Submission Form** | **STUDENT** | Shows the dynamic form after selecting a course, ready for rating and comments. |  |
| **Empty Submission** | **STUDENT** | The initial view of the student submission interface before course selection. |  |
| **Admin Controls** | **ADMIN** | Consolidated report view with specialized controls for data maintenance (Delete Feedback, Clear All Data). |  |
| **Faculty Report** | **FACULTY** | Consolidated report view available to faculty users. |  |
| **Empty Admin Report** | **ADMIN** | The consolidated report view when no feedback data is currently available in the database. |  |

**STUDENT**
<img width="1918" height="1017" alt="Screenshot 2025-10-29 013742" src="https://github.com/user-attachments/assets/054feb48-22a5-49c5-a53b-dd8e7d031cd3" />

**FACULTY**
<img width="1919" height="1017" alt="Screenshot 2025-10-29 013838" src="https://github.com/user-attachments/assets/535d6aa4-dc2f-4d1e-a13e-e3f0eebc5609" />

**ADMIN**
<img width="1919" height="1018" alt="Screenshot 2025-10-29 013823" src="https://github.com/user-attachments/assets/ff37f40c-827a-403c-a2be-bac21aa01aa0" />

***

## 📄 Project Documentation

* **Full Report:** [OOPL Mini Project Report Format.docx] (Detailed analysis, design, and conclusion of the project).
* **Problem Statement:** [Problem statement Mini Project.pdf] (Original document defining objectives and functional requirements).
