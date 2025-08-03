# HR Employee Management System

A full-stack web application to manage employee records with support for XML data import. Built using **Spring Boot**, **Angular**, and **Oracle Database**.

---

## 🚀 Features

- List all employees
- Add a new employee
- Edit employee details
- Delete employee records
- Import employees from XML file
- Oracle database integration
- CORS enabled for Angular frontend
- RESTful APIs built with Spring Boot

---

## 🧰 Tech Stack

| Layer         | Technology                     |
|---------------|--------------------------------|
| Frontend      | Angular 17 (Standalone API)    |
| Backend       | Spring Boot 3 + Spring Data JPA|
| Database      | Oracle XE                      |
| Communication | REST API + JSON + Multipart XML|
| Build Tools   | Maven, npm                     |

---

## 📁 Project Structure

hr-employee-app/
├── backend/ # Spring Boot project
│ └── src/main/java/... # Controllers, Models, Services
│ └── resources/ # application.properties
├── frontend/ # Angular project
│ └── src/app/ # Components, Services, Routes
├── employee_utf8.xml # Sample XML for import
└── README.md


---

## ⚙️ Backend Setup (Spring Boot)

1. Install JDK 17+
2. Configure Oracle DB (local or Docker)
3. Create `employees` table:

``sql
CREATE TABLE employees (
  id NUMBER PRIMARY KEY,
  firstname VARCHAR2(50),
  lastname VARCHAR2(50),
  title VARCHAR2(100),
  division VARCHAR2(100),
  building VARCHAR2(50),
  room VARCHAR2(50)
);

application.properties===>

spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect


run backend:

cd backend
./mvnw spring-boot:run

Install Node.js and Angular CLI:
npm install -g @angular/cli

Install dependencies:
cd frontend
npm install

Run the Angular app:
ng serve
App will be running at: http://localhost:4200


Import Employees from XML::
Use the provided employee_utf8.xml file to bulk import employees.
In Angular UI:
Upload XML via form

Author
S. M. Tarequl Hasan Robin
https://www.linkedin.com/in/tarequl-hasan/
