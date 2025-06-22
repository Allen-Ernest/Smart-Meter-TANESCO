# TANESCO Smart Meter Web Application

A web-based simulation of the **TANESCO Smart Meter System**, built as part of the Object-Oriented Programming (OOP) course assignment. This project implements core OOP principles within a real-world context of a smart electricity billing and management system for both **clients** and **administrators**.

---

## 📌 Project Description

The **TANESCO Smart Meter** portal allows registered clients to:

- Log in securely using their meter number and password.
- Purchase power tokens using virtual TZS currency.
- View past transactions and download their history.
- Access personalized profile information.

Administrators can:

- Register new admin users.
- Log in to the admin dashboard.
- View system-wide statistics (clients, transactions, daily purchases).
- Manage client accounts and monitor their purchase activity.

---

## 🛠️ Technologies Used

- **Java 21**
- **Spring Boot 3**
- **Thymeleaf** (for dynamic HTML rendering)
- **Spring Security** (for authentication and session handling)
- **Tailwind CSS** (for responsive UI styling)
- **PostgreSQL** (for database management)
- **Lombok** (to reduce boilerplate code)
- **Maven** (for dependency management)

---

## 🧠 Key OOP Concepts Applied

- **Encapsulation**: DTOs, Entities, and Service layers isolate logic and data.
- **Abstraction**: Service and controller layers abstract complex logic from the view.
- **Inheritance**: Common user properties managed through shared class design.
- **Polymorphism**: Flexibility in handling different user roles and transaction types.

---

## 📂 Project Structure

├── controller/
│ └── ClientController.java, AuthController.java, NavigationController.java
├── dto/
│ └── AdminDTO.java, ClientDTO.java, TransactionDTO.java
├── model/
│ └── Admin.java, Client.java, PowerTransaction.java
├── repository/
│ └── AdminRepository.java, ClientRepository.java, PowerTransactionRepository.java
├── service/
│ └── AuthService.java, PowerService.java
├── templates/
│ ├── admin/
│ ├── client/
│ └── shared/
├── static/
│ └── Tailwind styles & assets
└── application.properties
