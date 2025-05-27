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
## Sample curl for Testing
#### Login to get JWT token:

```bash
curl --request POST 'http://localhost:8080/api/auth/login' \
-H "Content-Type: application/json" \
--data-raw '{
  "username": "admin",
  "password": "admin123"
}'

GET All Applications
curl --request GET 'http://localhost:8080/api/application'
-H "Authorization: Bearer ?"

GET Application By ID
curl --request GET 'http://localhost:8080/api/application/{appId}'
-H "Authorization: Bearer ?"

Create New Application
curl --request POST 'http://localhost:8080/api/application'
-H "Content-Type: application/json"
-H "Authorization: Bearer ?"
--data-raw '{ "productType": "CreditCard", "productProgram": "Standard", "cardType": "Visa", "campaignCode": "CAMP001", "appStatus": "PENDING", "isVip": true }'

Update Application
curl --request PUT 'http://localhost:8080/api/application/{appId}'
-H "Content-Type: application/json"
-H "Authorization: Bearer ?"
--data-raw '{ "productType": "CreditCard", "productProgram": "Platinum", "cardType": "MasterCard", "campaignCode": "CAMP002", "appStatus": "APPROVED", "isVip": false }'

DELETE Application By ID
curl --request DELETE 'http://localhost:8080/api/application/{appId}'
-H "Authorization: Bearer ?"

DELETE All Applications
curl --request DELETE 'http://localhost:8080/api/application'
-H "Authorization: Bearer ?"

DELETE All Applications with Confirmation
curl --request DELETE 'http://localhost:8080/api/application?confirm=true'
-H "Authorization: Bearer ?"

# Run Testing 
.\gradlew test
