# Application Processing System (APS) API
## Technologies

- Spring Boot 3.5
- Spring Data JPA
- Microsoft SQL Server Driver
- Spring Boot DevTools
- Gradle build system
- Java 17


### Prerequisites

- Java 17
- Gradle
- SQL Server instance running on localhost:1433

### Database Setup

database named `employee_management`

## Sample `curl` for Testing (POST)

# GET all 
curl --request GET 'http://localhost:8080/api/application'

# GET by ID
curl --request GET 'http://localhost:8080/api/application/{appId}'

# create new
curl --request POST 'http://localhost:8080/api/application' \
--header 'Content-Type: application/json' \
--data-raw '{
"productType": "CreditCard",
"productProgram": "Standard",
"cardType": "Visa",
"campaignCode": "CAMP001",
"appStatus": "PENDING",
"isVip": true
}'

# update
curl --request PUT 'http://localhost:8080/api/application/{appId}' \
--header 'Content-Type: application/json' \
--data-raw '{
"productType": "CreditCard",
"productProgram": "Platinum",
"cardType": "MasterCard",
"campaignCode": "CAMP002",
"appStatus": "APPROVED",
"isVip": false
}'

# DELETE by ID
curl --request DELETE 'http://localhost:8080/api/application/{appId}'
