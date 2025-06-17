# Z-Auction Backend

This project is a Spring Boot backend application for our auction platform named Z-Auction. It serves as the core server-side component, handling business logic, data management with JPA, and providing RESTful APIs to support the auction functionalities.


## Prerequisites

- Java JDK 17 or higher
- Maven 3.x
- A database configured if needed (by default, an in-memory H2 database is used)


## Installation and Running

1. Clone the repository:
   ```bash
   git clone https://github.com/ETML-ES-LABEFE-MANUEL-THEO/BACKEND.git
   cd BACKEND


2. Run the application with Maven:

   ```bash
   mvn clean spring-boot:run
   ```

   This command will:

    * Download all required dependencies
    * Compile the project
    * Start the embedded Tomcat server on port 8080 (configurable)


## Main Features

* REST API exposed via Spring REST controllers
* Entity management with Spring Data JPA
* Automatic data initialization on startup with dev profile (e.g., preloading lots)
* Endpoint usage examples with compatible HTTPie/Postman json file included in docs


## Configuration

## Environment Variables

The application can be configured via environment variables or by editing the `application.properties` file located in `src/main/resources/`. Below are the main configuration properties:

### API Security

| Property                      | Description                                     |
|-------------------------------|-------------------------------------------------|
| `security.cors-origin`        | Allowed CORS origins for API requests.          |
| `security.api-header`         | HTTP header used for API key authentication.    |
| `security.api-secret`         | Secret value for API authentication.            |
| `security.cookie-name`        | Name of the authentication cookie.              |
| `security.cookie-validity`    | Cookie validity duration in minutes. |

### MySQL Configuration

| Property                          | Description                                                |
|------------------------------------|------------------------------------------------------------|
| `spring.jpa.hibernate.ddl-auto`    | JPA schema management strategy.                            |
| `spring.datasource.url`            | JDBC URL for the MySQL database.                           |
| `spring.datasource.username`       | Database username.                                         |
| `spring.datasource.password`       | Database password.                                         |
| `spring.datasource.driver-class-name` | JDBC driver class name for MySQL.                          |
| `spring.jpa.show-sql`              | Enables SQL statement logging in the console.              |

> **Note:** You can override any of these properties by setting environment variables or using command-line arguments as described in the [Spring Boot documentation][1][3][4].

### Example `application.properties`

```properties
# Application Name
spring.application.name=zauction

# API context path
server.servlet.context-path=/api/v1

# API SECURITY
security.cors-origin=*
security.api-header=X-API-KEY
security.api-secret=mysecretapi
security.cookie-name=Authorization
security.cookie-validity=43200

# MYSQL CONFIG
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/zauction
spring.datasource.username=zauction
spring.datasource.password=secret
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true

# S3 CONFIG
s3.bucket=projlabefe.theomanuel
s3.region=us-east-1
s3.access-key=YOUR_ACCESS_KEY
s3.secret-key=YOUR_SECRET_KEY

# FILE UPLOAD
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=100MB
```

**Remember:**
- For production, never commit sensitive values (like passwords or secrets) to version control.
- You can externalize configuration using environment variables or a secure configuration server[3][4].


## Useful Resources

* Official Spring Boot documentation: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
* JPA tutorial: [https://spring.io/guides/gs/accessing-data-jpa/](https://spring.io/guides/gs/accessing-data-jpa/)
* REST API guide with Spring: [https://spring.io/guides/gs/rest-service/](https://spring.io/guides/gs/rest-service/)
