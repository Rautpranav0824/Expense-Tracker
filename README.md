#  Expense Tracker REST API

A backend Expense Tracker application built with **Spring Boot** and **MongoDB** that allows users to manage their personal expenses through REST APIs.

This project focuses on building a clean backend architecture with proper CRUD operations, user-expense relationships, and MongoDB document references.

---

##  Features

###  User Management
- Create User
- Get User Details
- Update User
- Delete User

###  Expense Management
- Add Expense to a User
- Get All Expenses of a User
- Get a Specific Expense
- Update an Expense
- Delete an Expense

---

##  Tech Stack

- Java 21
- Spring Boot
- Spring Data MongoDB
- MongoDB Atlas
- Maven
- Lombok
- Postman

---

##  Project Structure

```
src
 └── main
      ├── controller
      ├── service
      ├── repository
      ├── entity
      └── resources
```

---

##  Database Design

### User

```java
User
├── id
├── username
├── password
├── email
└── List<Expense> expenses
```

### Expense

```java
Expense
├── id
├── title
├── amount
├── category
├── description
└── date
```

The relationship between User and Expense is maintained using **MongoDB DBRef**, where the User stores references to Expense documents.

---

##  API Endpoints

### User APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/user` | Create User |
| GET | `/user/{username}` | Get User |
| PUT | `/user/{username}` | Update User |
| DELETE | `/user/{username}` | Delete User |

---

### Expense APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | `/user/{username}/expenses` | Add Expense |
| GET | `/user/{username}/expenses` | Get All Expenses |
| GET | `/user/{username}/{id}/expenses` | Get Expense by ID |
| PUT | `/user/{username}/{id}/expenses` | Update Expense |
| DELETE | `/user/{username}/{id}/expenses` | Delete Expense |

---

##  Concepts Implemented

- RESTful API Design
- Layered Architecture
- CRUD Operations
- Spring Boot
- Spring Data MongoDB
- MongoDB Atlas
- DBRef Mapping
- Exception Handling
- Path Variables
- Request Body Mapping
- Dependency Injection

---

##  Testing

All APIs were tested using **Postman**.

---

##  Future Improvements

- Spring Security
- JWT Authentication
- BCrypt Password Encoding
- Request Validation
- Global Exception Handling
- DTO Pattern
- Swagger / OpenAPI Documentation
- Docker
- Unit Testing (JUnit & Mockito)

---

##  Author

**Pranav Raut**

Backend Developer | Java | Spring Boot | MongoDB

---
 Feel free to fork the repository and contribute!
