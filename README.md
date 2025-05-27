# Application Processing System (APS) API
## Technologies

- Spring Boot 3.5
- Spring Data JPA
- Microsoft SQL Server Driver
- Spring Boot DevTools
- Gradle build system
- Java 17
- JWT Authentication


### Prerequisites

- Java 17
- Gradle
- SQL Server instance running on localhost:1433

### Database Setup

database named `db-aps-exam`

### Authentication

This API uses JWT for authentication. Only ADMIN users can access the API endpoints.

#### Login to get JWT token:

```bash
curl --request POST 'http://localhost:8080/api/auth/login' \
-H "Content-Type: application/json" \
--data-raw '{
  "username": "admin",
  "password": "admin123"
}'

# Run Testing 
.\gradlew test
