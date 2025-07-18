# Translation Service

A Spring Boot application for managing translations with support for locale, tags, and export functionality.

## Prerequisites

- Java 21
- Maven 3.8+
- Docker (for database setup)

## Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/alijaved94/translation-service-demo.git

2. **Start the database using Docker**

    ```bash
    cd translation-service-demo
    ./make-docker-db.sh
    ```
    
    This script will start a PostgreSQL database in a Docker container.

3. **Run the application**

4. **First get the Authentication Token**

   Use the following command to get the JWT token:

   ```bash
   curl -X POST http://localhost:8080/api/auth/token -H "Content-Type: application/json"'
   ```
   
5. **Before calling any API, you need to pass the JWT token in the header**

   Example:

   ```bash
   curl -X GET http://localhost:8080/api/translations --header "Authorization Bearer <your_token>"
   ```



## Project Structure

- `src/main/java/com/demo/translation/` - Main source code
- `src/main/resources/` - Configuration files
- `make-docker-db.sh` - Script to start the database in Docker

## API Swagger Documentation
- Access the Swagger UI at `http://localhost:8080/swagger-ui/` after starting the application.

## Features

- Create, update, view, and search translations by content, tags, or locale

- JSON export endpoint for frontend applications

- JWT-based authentication

- Optimized database schema with indexing

- Flyway for database migrations with triggers for timestamps in initial schema

- Dockerized deployment

- OpenAPI/Swagger documentation

- 95%+ test coverage

- Data loader for 100k+ records

- Enums for Locale (EN, FR, ES, DE, IT) and Tag (MOBILE, DESKTOP, WEB)
