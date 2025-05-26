# Z-Auction

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

The default server port is **8080**, which can be changed in `application.properties`:

  ```properties
  server.port=8080
  ```

The data source configuration can be adapted to your environment. By default, an embedded H2 database is used.

Work in progress ...


## Useful Resources

* Official Spring Boot documentation: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)
* JPA tutorial: [https://spring.io/guides/gs/accessing-data-jpa/](https://spring.io/guides/gs/accessing-data-jpa/)
* REST API guide with Spring: [https://spring.io/guides/gs/rest-service/](https://spring.io/guides/gs/rest-service/)