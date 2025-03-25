
# POS Management System

A Java-based Point of Sale (POS) management system designed to handle clients, employees, receipts (bonuri), and administrative operations. The project follows an object-oriented design and separates concerns using repositories and service classes.

## 📦 Features

- Admin and client management
- Receipt generation and tracking
- Basic auditing of actions (`audit.csv`)
- Database connectivity using JDBC
- Modular services and repositories
- Maven-based build system

## 🛠️ Technologies Used

- Java
- Maven
- JDBC (Manual DB connection)
- IntelliJ IDEA (project files included)

## ▶️ Getting Started

1. Clone or download the repository.
2. Import the project into IntelliJ or your preferred IDE.
3. Ensure you have a MySQL or other database ready.
4. Configure the database connection in `DatabaseConnection.java`.
5. Build and run the project.

## 📑 Notes

- The audit log is saved in `audit.csv`.
- Make sure to create and configure the appropriate tables in your database.
- No GUI is implemented; this is a backend-focused project.

## 📄 License

This project is for educational purposes and may be extended or modified as needed.

---

*Created as part of academic coursework.*
