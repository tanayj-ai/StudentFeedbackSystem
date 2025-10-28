# 📚 Student Feedback System (OOPL Mini Project)

## Project Overview
This project is a secure, **role-based desktop application** developed for the Object-Oriented Programming Laboratory (OOPL) Mini Project. It digitizes the collection, storage, and analysis of student feedback on courses and facilities.

| Authors | Roll Numbers |
| :--- | :--- |
| Uday Farhad | 16015024080 |
| Tanay Jobanputra | 16015024032 |

## Technologies Used
* **Core Language:** Java
* **GUI:** Java Swing
* **Database:** **SQLite** (via JDBC)
* **Dependency:** `sqlite-jdbc-3.50.3.0.jar`

***

## 🌟 Key System Features

* **Role-Based Access:** Separate secure logins for Student, Faculty, and Admin.
* **Dynamic Forms:** Feedback questions are **customizable per course** and loaded dynamically.
* **Automated Reporting:** Generates **real-time statistical reports** including course averages and rating distributions.

***

## ⚙️ Execution Instructions

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/YourUsername/student-feedback-system.git](https://github.com/YourUsername/student-feedback-system.git)
    ```
2.  **Compile the Code:**
    ```bash
    javac -cp "lib/*" -d bin src/com/example/*.java
    ```
3.  **Run the Application:**
    ```bash
    java -cp "bin/*;lib/*" com.example.LoginGUI
    ```

### Default Login Credentials
| Username | Password | Role |
| :--- | :--- | :--- |
| **admin** | **admin123** | Admin |
| **faculty** | **facpass** | Faculty |

***
*Commit the `README.md` file to finalize your professional repository.*
