# 🪖 ArmouryHub - Tactical Inventory Management System

An efficient, lightweight, and modern **ArmouryHub** built with **Spring Boot** and **MySQL**. It is designed to catalog weapons, assign equipment to officers, manage storage categories, track returns, push real-time alerts via the Observer Pattern, and log compliance events to an immutable security audit database.

The user interface features a **Light Tactical Desert Theme** inspired by military field control consoles, complete with coordinate grids, monospace telemetry displays, and structured status feeds.

---

## 🛠️ Tech Stack

*   **Backend:** Java 17+, Spring Boot 3.2.5 (Web, Data JPA, Validation)
*   **Database:** MySQL 8.0+ (connection pooled with HikariCP)
*   **Frontend:** Vanilla HTML5, CSS3 (Tactical sand/khaki stylesheet), Vanilla ES6 JavaScript
*   **Build Tool:** Maven

---

## 🚀 Key Features

1.  **Weapon Registry (Inventory):** Track tactical weapon models, operational statuses (`OPERATIONAL`, `MAINTENANCE`, `DECOMMISSIONED`), and real-time availability.
2.  **Officer Registration:** Onboard security personnel with unique badges, rank designations, and secure access profiles.
3.  **Weapon Issuance (Checkout):** Authorize checkouts of weapons to officers with a custom expected return period. Automatically updates availability to `false`.
4.  **Weapon Returns:** Log weapon returns, inspect weapon conditions (`EXCELLENT`, `NEED_CLEANING`, `DAMAGED`), and restore weapon availability.
5.  **Alerts Feed (Observer Pattern):** Pushes real-time status notifications to individual officers when weapons are assigned or returned.
6.  **Immutable Audit Logs:** Tracks compliance and logs every system action (creation, issuance, returns, registration) into a searchable audit registry.

---

## 💻 Setup & Installation

### 1. Database Configuration
Ensure MySQL is running. The system will automatically create the database if it doesn't exist. Update database credentials in [application.properties](src/main/resources/application.properties):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/armourydb?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 2. Run the Application
Start the Spring Boot server using Maven:
```bash
mvn spring-boot:run
```
The server will boot on port **8080** and automatically seed initial sample weapons, categories, and test officers if the database is clean.

### 3. Open the Dashboard
Access the frontend in your browser:
*   **Inventory Register:** `http://localhost:8080/index.html`
*   **Issuance & returns:** `http://localhost:8080/issuance.html`
*   **Audit logs:** `http://localhost:8080/audit.html`

---

## 🔌 API Endpoints Reference

### 📂 Categories
*   `GET /api/categories` - Fetch all weapon categories.
*   `POST /api/categories` - Create a new category.
    *   *Body:* `{"name": "Submachine Gun", "description": "Description text"}`

### 🔫 Weapons
*   `GET /api/weapons` - Fetch all weapons in the registry.
*   `GET /api/weapons/available` - Fetch weapons currently in stock and ready for deployment.
*   `GET /api/weapons/category/{id}` - Fetch weapons filtered by category ID.
*   `POST /api/weapons` - Register a new weapon.
    *   *Body:* `{"serialNumber": "SN-101", "name": "M4 Carbine", "categoryId": 1, "status": "OPERATIONAL"}`

### 👮 Officers
*   `GET /api/officers` - Fetch all registered officers.
*   `POST /api/officers` - Register a new officer.
    *   *Body:* `{"name": "Jane Doe", "badgeNumber": "B101", "rank": "Sergeant", "email": "jane@police.gov", "password": "securepass"}`

### 📝 Issuance & Returns
*   `GET /api/issuance/active` - Fetch all active weapon assignments.
*   `POST /api/issuance` - Authorize a weapon checkout to an officer.
    *   *Body:* `{"officerId": 1, "weaponId": 2, "expectedReturnDays": 7}`
*   `POST /api/return` - Complete return checkout of a weapon.
    *   *Body:* `{"issuanceRecordId": 1, "conditionStatus": "EXCELLENT"}`

### 🔔 Alerts & Audits
*   `GET /api/notifications/officer/{officerId}` - Fetch alert feeds for a specific officer.
*   `PUT /api/notifications/{id}/read` - Mark an alert notification as read.
*   `GET /api/audit` - Retrieve all immutable system audit logs.
