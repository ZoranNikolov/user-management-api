# User Management REST API

A Spring Boot REST API for managing users.  
Supports **Create**, **Read**, **Update**, **Delete** (CRUD) operations with search, sorting, and pagination.

---

## 1. Prerequisites

- **Java**: 21+
- **Maven**: 3.9+
- **MySQL**: 8.x
- **Git** (optional, for version control)
- **Postman** (for API testing)

---

## 2. Database Setup

1. Start MySQL.
2. Run the following SQL commands:

```sql
CREATE DATABASE user_management;
CREATE USER 'car_digital'@'localhost' IDENTIFIED BY '5281';
GRANT ALL PRIVILEGES ON user_management.* TO 'car_digital'@'localhost';
FLUSH PRIVILEGES;
```

## 3. Application Configuration

Edit ``src/main/resources/application.properties:``

```
spring.datasource.url=jdbc:mysql://localhost:3306/user_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=car_digital
spring.datasource.password=5281

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080
```

## 4. Running the Application

Using Maven

```
mvn spring-boot:run
```
The application will start at:

```
http://localhost:8080
```

## 5. API Endpoints

### 5.1 Create User

### POST ```/users```

### Request body:

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "phoneNumber": "+1234567890",
  "emailAddress": "john.doe@example.com"
}
```

#### Sample response:

```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-05-15",
  "phoneNumber": "+1234567890",
  "emailAddress": "john.doe@example.com"
}
```

### 5.2 Get All Users (with optional pagination, sorting, and search)

### GET ```/users?page=0&size=10&sort=lastName,asc&search=doe```

### Sample response:

```json
{
  "content": [
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "dateOfBirth": "1990-05-15",
      "phoneNumber": "+1234567890",
      "emailAddress": "john.doe@example.com"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 5.3 Get User by ID

### GET ```/users/{id}```

Example:

```GET /users/1```

### 5.4 Update User

### PUT ```/users/{id}```

### Request body:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "dateOfBirth": "1992-04-20",
  "phoneNumber": "+0987654321",
  "emailAddress": "jane.doe@example.com"
}
```

### 5.5 Delete User

### DELETE ```/users/{id}```

Example:

```DELETE /users/1```

### 6. Error Handling Examples

### User Not Found (404)

```json
{
  "error": "UserNotFoundException",
  "message": "User with ID 999 not found",
  "status": 404
}
```

### Validation Error (400)

```json
{
  "error": "ValidationException",
  "message": "Email address is invalid",
  "status": 400
}
```

### 7. API Documentation (Swagger UI)

Swagger UI is available at:

```http://localhost:8080/swagger-ui/index.html```

### 8. Troubleshooting

- #### MySQL Public Key Retrieval Error
  Add allowPublicKeyRetrieval=true to the JDBC URL.
- #### Date Parsing Error
Ensure date format is YYYY-MM-DD.