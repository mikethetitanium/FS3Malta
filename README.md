# FS3Malta Application

## Overview
FS3Malta is a Spring Boot application designed to generate FS3 reports for employees. It includes features for generating PDFs, viewing reports, and managing employee data.

---

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- H2 Database (embedded)
- A web browser to access the application

---

## How to Run the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/mikethetitanium/FS3Malta.git
   ```
2. Navigate to the project directory:
   ```bash
   cd FS3Malta
   ```
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

---

## Accessing the H2 Database
The application uses an embedded H2 database. To view the database:

1. Ensure the H2 console is enabled in `application.properties`:
   ```properties
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   spring.datasource.url=jdbc:h2:mem:payrolldb
   spring.datasource.username=sa
   spring.datasource.password=
   ```

2. Open your browser and navigate to:
   ```
   http://localhost:8080/h2-console
   ```

3. Use the following credentials to log in:
   - **JDBC URL**: `jdbc:h2:mem:payrolldb`
   - **Username**: `sa`
   - **Password**: (leave blank)

4. Run SQL queries to view or manipulate data. For example, to view all employees:
   ```sql
   SELECT * FROM employees;
   ```

---

## Features
- Generate FS3 reports for employees.
- Export reports as PDFs.
- View employee data and reports.
- Embedded H2 database for development and testing.

---

## Project Structure
- **src/main/java**: Contains the application source code.
- **src/main/resources/templates**: Thymeleaf templates for the web interface.
- **src/main/resources/static**: Static resources (CSS, JS, images).
- **src/main/resources/application.properties**: Application configuration.

---

## Troubleshooting
- **H2 Console Not Accessible**:
  - Ensure `spring.h2.console.enabled=true` is set in `application.properties`.
  - Verify the application is running on `http://localhost:8080`.

- **Template Not Found**:
  - Ensure the required Thymeleaf templates are in `src/main/resources/templates/`.

- **Database Issues**:
  - Check the H2 console for errors.
  - Verify the database schema matches the application requirements.

---

## Author
Michael Mukosi John - Mercans offline assignment