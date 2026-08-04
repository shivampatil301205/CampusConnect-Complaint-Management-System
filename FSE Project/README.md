# CampusConnect – Complaint Management System

CampusConnect is a full-stack Complaint Management System developed for educational institutions. It provides a centralized platform where students can raise complaints regarding campus-related issues, while administrators can efficiently track, manage, and resolve them.

---

## 📌 Features

### Student
- Register and Login
- Submit new complaints
- View all submitted complaints
- Track complaint status
- View complaint details

### Admin
- Secure Admin Login
- View all complaints
- Update complaint status
- Mark complaints as:
  - Pending
  - In Progress
  - Resolved
- Manage student complaints from a centralized dashboard

---

## 🛠 Tech Stack

### Frontend
- React.js
- Vite
- React Router
- Axios
- React Icons

### Backend
- Spring Boot
- Spring Web
- Spring Data JPA
- Maven

### Database
- PostgreSQL

### Tools
- VS Code
- IntelliJ IDEA
- Postman
- Git & GitHub

---

## 📂 Project Structure

```
CampusConnect
│
├── backend
│   ├── src
│   ├── pom.xml
│   └── application.properties
│
├── frontend
│   ├── src
│   ├── public
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Install the following:

- Java 21 or later
- Maven
- Node.js
- npm
- PostgreSQL
- Git

---

# Backend Setup

### 1. Navigate to backend

```bash
cd backend
```

### 2. Configure PostgreSQL

Update the database credentials inside:

```
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/campusconnect
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run Backend

```bash
mvn spring-boot:run
```

Backend runs on

```
http://localhost:8080
```

---

# Frontend Setup

### Navigate to frontend

```bash
cd frontend
```

Install dependencies

```bash
npm install
```

Run application

```bash
npm run dev
```

Frontend runs on

```
http://localhost:5173
```

---

## API Overview

### Authentication

- Login
- Register

### Complaints

- Create Complaint
- View Complaints
- Update Complaint Status
- Delete Complaint (if enabled)

---

## Complaint Workflow

```
Student Login
       │
       ▼
Raise Complaint
       │
       ▼
Complaint Stored in Database
       │
       ▼
Admin Dashboard
       │
       ▼
Update Status
       │
       ▼
Student Tracks Progress
```

---

## Database

The project uses PostgreSQL to store:

- Student Information
- Admin Information
- Complaint Details
- Complaint Status
- Categories

---

## Future Improvements

- Email Notifications
- Complaint Priority Levels
- Image Upload Support
- Search and Filter
- Dashboard Analytics
- JWT Authentication
- Role-Based Authorization
- File Attachments

---

## Screenshots

Add screenshots here:

```
screenshots/

login.png
dashboard.png
student-home.png
admin-dashboard.png
complaints.png
```

---

## Author

**Shivam Patil**

B.Tech Computer Science Engineering

VIT Bhopal University

---

## License

This project is developed for educational purposes.

```
MIT License
```

---

## Acknowledgements

- Spring Boot
- React
- PostgreSQL
- Maven
- Vite
