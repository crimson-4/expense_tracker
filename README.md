# Expense Tracker Backend

This is a Spring Boot application for tracking expenses. It provides a RESTful API for managing expenses, users, and authentication.

## Features

* User registration and authentication with JWT
* CRUD operations for expenses
* Expense analytics
* Kafka integration for event-driven features

## Technologies Used

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* PostgreSQL
* Kafka
* Maven
* Docker

## Getting Started

### Prerequisites

* Java 17
* Maven
* Docker
* A running PostgreSQL instance

### Local Development

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/expense-backend.git
    cd expense-backend
    ```

2.  **Set up the database:**

    *   Make sure you have a PostgreSQL database running.
    *   Create a database named `expense_db`.
    *   Update the `src/main/resources/application.yml` file with your database credentials:

        ```yml
        spring:
          datasource:
            url: jdbc:postgresql://localhost:5432/expense_db
            username: your-username
            password: your-password
        ```

3.  **Start Kafka and Zookeeper:**

    ```bash
    docker-compose up -d
    ```

4.  **Run the application:**
5. 

    ```bash
    ./mvnw spring-boot:run
    ```

The application will be running at `http://localhost:8080`.
