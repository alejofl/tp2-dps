# Invoice Management System

A small, modular Invoice Management System implemented with Spring Boot and a hexagonal architecture.

This repository contains a multi-module Maven project that implements core invoice domain logic, application use-cases, adapters for persistence and I/O, and a web interface.

## Purpose / Domain

This application manages invoices, clients and products for a small business. It provides features typically required in invoicing systems:

- CRUD for clients and invoices
- Business rules and domain invariants encapsulated in the core domain model
- Export invoices to PDF, Excel (XLSX) and CSV
- Server-rendered UI (Thymeleaf) with basic security
- Persistence using MySQL (or H2 for tests/development)

The goal of this project is to clearly separate business logic from framework and delivery concerns so the core rules stay testable and independent.

## Architecture (Hexagonal)

This project follows a hexagonal architecture. The idea is to keep the domain (core business rules) at the center and expose it through well-defined ports. Adapters implement those ports to interact with external technologies (web, DB, files).

High-level mapping of repository modules to hexagon concepts:

- `domain` — The heart of the hexagon. Contains domain entities, value objects, domain services and business rules. No framework dependencies.
- `application` — Application services / use cases. Implements the "driving" side of the hexagon: orchestrates domain objects.
- `infrastructure` — Adapters for persistence, file export, remote APIs, and any framework integrations. For example, JPA repositories and MySQL entities.
- `interface` — Driving adapters: Thymeleaf controllers, DTOs, view templates and static assets. This module depends on `application` and provides HTTP endpoints and web pages.
- `boot` — Spring Boot application starter. Wires modules together and contains application configuration (profiles, properties). Use this to run the app.
- `common` — Shared types, utilities and helpers used across modules.

This separation makes it easy to test business rules in isolation and replace adapters without changing core logic.

## How to run

### Prerequisites

- Java 23 (project set to compile with Java 23)
- Maven
- Docker & Docker Compose (optional, recommended to run a local MySQL database)

### Configuration

The Boot module includes a default `application.properties` with environment-variable-driven settings. Important defaults:

- Database: `jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${MYSQL_DATABASE:invoice_db}`
- DB user: `${MYSQL_USER:invoice_user}`
- DB password: `${MYSQL_PASSWORD:invoice_password}`
- Server port: `${PORT:8080}`

The repository also includes `compose.yaml` which defines a MySQL service you can bring up locally. By default it exposes MySQL on 127.0.0.1:3306 and uses the environment variables above.

### 1. Start the database with Docker Compose

```bash
# from project root
docker compose -f compose.yaml up -d
```

### 2. Build and run the application

Option A — Run with Maven (recommended during development):

```bash
# build all modules (skip tests to speed up if desired)
mvn -DskipTests package

# run the Boot module
mvn -pl boot spring-boot:run
```

### Running tests

To run the full test suite:

```bash
mvn test
```

## Final Remarks

This project was done in an academic environment, as part of the curriculum of Professional Software Development from Instituto Tecnológico de Buenos Aires (ITBA)

The project was carried out by:

* [Alejo Flores Lucey](https://github.com/alejofl)
* [Elian Paredes](https://github.com/elianparedes)
* [Juan Burda](https://github.com/burdajuan)
* [Nehuén Gabriel Llanos](https://github.com/NehuenLlanos)