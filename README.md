Customer Management Backend
This is the Spring Boot backend for the Customer Management System.
It provides REST APIs for managing customers, bulk uploading via Excel, and supports all business requirements for customer, address, and relationship management.

Features
Customer CRUD: Create, read, update customers.

Attributes: Name, Date of Birth, NIC (unique), multiple mobile numbers, multiple addresses (with city/country as text), family member relationships.

Bulk Upload: Asynchronous bulk customer creation via Excel file (supports up to 1,000,000 records with batching and streaming).

Master Data: Cities and countries are managed as master data (not exposed in the frontend).

Optimized: Minimal DB calls, batch processing, and async operations to prevent timeouts and memory issues.

API-first: RESTful endpoints for all operations.

Getting Started
Prerequisites
Java 21

Maven 3.6+

Maria DB

Installation
Clone the repository

bash
git clone https://github.com/your-username/customer-management-system.git
cd customer-management-system/backend
Configure Database

Update src/main/resources/application.properties with your DB credentials.

Run Database Scripts

Use the provided DDL and DML files:

bash
mysql -u <user> -p < database < ../ddl/schema.sql
mysql -u <user> -p < database < ../ddl/master-data.sql
Build and Run

bash
mvn clean install
mvn spring-boot:run
The backend will start at http://localhost:8080.
    
API Endpoints
POST /api/customers - Create customer
http://localhost:8080/api/customers

GET /api/customers/{id} - Get customer by ID
http://localhost:8080/api/customers/1

PUT /api/customers/{id} - Update customer


GET /api/customers - Paginated customer list
http://localhost:8080/api/customers?page=0&size=10

POST /api/customers/upload - Bulk upload via Excel (multipart/form-data)
http://localhost:8080/api/customers/upload

More endpoints for addresses, family members, etc.

Bulk Upload
Upload an Excel file with columns: Name, DOB, NIC, MobileNumbers (comma-separated), AddressLine1, AddressLine2, City, Country.

The backend processes the file asynchronously and supports very large files with batching.

Configuration
File Upload Limits: Set in application.properties:

text
spring.servlet.multipart.max-file-size=1GB
spring.servlet.multipart.max-request-size=1GB
Async Processing: Configured for bulk upload to avoid timeouts.

Testing
Unit and integration tests are provided in src/test/java.

Run all tests:

bash
mvn test
DDL and DML
ddl/schema.sql: Contains all table definitions (customers, addresses, mobile numbers, cities, countries, relationships).

ddl/master-data.sql: Contains initial city and country data.
