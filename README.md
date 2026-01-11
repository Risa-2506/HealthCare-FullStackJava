# HealthCare Backend – Java Servlets

## Description
HealthCare Backend is a Java Servlet–based web application developed using
JDBC and MySQL. The project provides backend functionality for user
authentication, doctor management, and appointment booking with proper
session handling.

This project focuses on backend development concepts such as servlets,
database connectivity, and secure configuration practices.

## Tech Stack
- Java (Servlets)
- JDBC
- MySQL
- HTML, CSS, JavaScript
- Apache Tomcat
- Eclipse IDE

## Features
- User Signup and Login
- Logout using session invalidation
- Doctor Management
- Appointment Booking
- Session-based authentication
- MySQL database integration

## Database Details
- Database Name: `healthcare`
- Database Type: MySQL
- Database structure is provided in `schema.sql`

## Configuration
Database connection logic is centralized in `DBUtil.java`.

Before running the project, update the following field in `DBUtil.java`:
with your local MySQL password.

## How to Run
1. Import the project into Eclipse IDE
2. Configure Apache Tomcat server in Eclipse
3. Create a MySQL database named `healthcare`
4. Import the `schema.sql` file into the database
5. Update database credentials in `DBUtil.java`
6. Start the Tomcat server
7. Access the application from the browser

## Notes
- Database credentials are intentionally not hardcoded
- Each user must configure their own local database
- This project demonstrates backend-focused development using Java Servlets

## Author
Risa Mathew
