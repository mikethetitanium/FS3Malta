# FS3Malta Application

## Overview
FS3Malta is a Spring Boot application that generates FS3 (Final Settlement System) reports for employees in Malta. It features a fully styled on-screen form, dynamic data population via employee/year selection, and PDF export that matches the on-screen layout exactly.

---

## Prerequisites
- Java 17 or higher
- Maven 3.8+
- H2 Database (embedded)
- A web browser to access the application

---

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/mikethetitanium/FS3Malta.git
   ```
2. Navigate to the project directory:
   ```bash
   cd FS3Malta
   ```
3. Build and run:
   ```bash
   mvn spring-boot:run
   ```
4. Open your browser and navigate to:
   ```
   http://localhost:8080
   ```

---

## How to Use

1. The application opens to the blank FS3 form with a filter bar at the top.
2. Select an **Employee** and **Year** from the dropdowns, then click **Generate Report**.
3. The form populates with the employee's data across all sections (A–F).
4. Click **Export PDF** to open the browser print dialog — select **Save as PDF** to download.

---

## Accessing the H2 Database

1. Ensure the H2 console is enabled in `application.properties`:
   ```properties
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   spring.datasource.url=jdbc:h2:mem:payrolldb
   spring.datasource.username=sa
   spring.datasource.password=
   ```
2. Navigate to `http://localhost:8080/h2-console`
3. Login credentials:
   - **JDBC URL**: `jdbc:h2:mem:payrolldb`
   - **Username**: `sa`
   - **Password**: *(leave blank)*

---

## Features
- Fully styled FS3 form matching the official Malta Tax & Customs Administration layout
- Employee and year selection filter to populate the form dynamically
- Character-box data entry style for ID cards, dates, and monetary values
- PDF export that preserves all colors, borders, and layout
- Embedded H2 database pre-seeded with sample employee and wage data

---

## Project Structure
```
src/main/java/com/FS3Malta/FS3Malta/
├── controller/       # FS3ReportController — handles form and report endpoints
├── Dto/              # FS3Report, SocialSecurityResult, WeeklyRecord
├── model/            # Employee, WeeklyWage entities
├── Repository/       # JPA repositories
└── service/          # FS3ReportService, FS3FormatterService, SocialSecurityCalculator

src/main/resources/
├── templates/        # fs3form.html — main Thymeleaf template
├── static/           # banner.png and other static assets
└── application.properties
```

---

## Screenshots

**1. Blank form — select employee and year to generate**
![Blank form](screenshots/1%20blank%20form.png)

**2. Populated report after selecting parameters and clicking Generate Report**
![Populated report](screenshots/2%20populated%20report%20after%20selecting%20parameters%20and%20clicking%20generate%20report.png)

**3. Report preview after clicking Export PDF**
![Export preview](screenshots/3%20report%20preview%20after%20clicking%20export%20pdf.png)

**4. Exported PDF**
![Exported PDF](screenshots/4%20exported%20report%20to%20pdf.png)

### Sample Exported PDF
[Download FS3 Report PDF](screenshots/FS3%20-%20Final%20Settlement%20System%20Form.pdf)

---

## Author
Michael Mukosi John — Mercans offline assignment
