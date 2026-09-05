# EcoPharma Warehouse Management System

A production-grade Warehouse Management System (WMS) built for a pharmaceutical company. The system handles end-to-end warehouse operations — from inbound receipts and inventory tracking to outbound shipments and cross-docking — with enterprise-level security and observability.

## Tech Stack

| Category | Technologies |
|----------|-------------|
| **Backend** | Java 21, Spring Boot 3.3.4 |
| **Security** | Keycloak, OAuth2 Resource Server, JWT, RBAC |
| **Database** | PostgreSQL, Liquibase (migrations) |
| **Messaging** | RabbitMQ |
| **File Storage** | MinIO |
| **Containerization** | Docker, Docker Compose (dev/stage/prod profiles) |
| **API Docs** | SpringDoc / Swagger UI |
| **Monitoring** | Sentry |
| **Scheduling** | ShedLock (distributed locks) |
| **Testing** | JUnit 5, Testcontainers, WireMock, JaCoCo |
| **Mapping** | MapStruct |
| **HTTP Client** | OpenFeign |
| **Logging** | AOP + Log4j2 |

## Key Features

### Warehouse Operations
- **Inbound receipts** — receive goods with metadata and status tracking
- **Outbound shipments** — manage shipment orders with status lifecycle
- **Cross-docking** — direct transfer between inbound and outbound without storage
- **Product returns** — handle return flows with proper audit

### Inventory Management
- **Real-time inventory tracking** — per location, rack, sector, and warehouse
- **Inventory audits** — scheduled and on-demand stock verification
- **Inventory snapshots** — automated daily snapshots via ShedLock scheduler
- **Unit conversions** — support for multiple measurement units per product

### Warehouse Structure
```
Warehouse
  └── Sector
        └── Rack
              └── Floor
                    └── Cell
                          └── Location
```

### Security & Access Control
- OAuth2 Resource Server with JWT validation via Keycloak
- Custom RBAC — roles with granular permission sets
- Audit trail on all entity changes (who changed what and when)

### Infrastructure
- Multi-stage Docker build (Gradle + Eclipse Temurin JDK 21 Alpine)
- Separate Docker Compose files per environment: `dev`, `stage`, `prod`, `test`
- Shared Docker network for microservice communication
- Remote JVM debugging support in containers

## Architecture

```
+---------------------------------------------------+
|                   API Gateway                     |
+----------------------+----------------------------+
                       |
+----------------------v----------------------------+
|           WMS Service (this repo)                 |
|  +------------+  +----------+  +---------------+ |
|  | Controllers|  | Services |  | Jobs/Scheduler| |
|  +------------+  +----------+  +---------------+ |
+------+---------------+----------------+----------+
       |               |                |
  +----v----+   +-------v--+   +--------v----+
  |Postgres |   | RabbitMQ |   |    MinIO    |
  +---------+   +----------+   +-------------+
       |
  +----v----+
  |Keycloak |
  +---------+
```

## Getting Started

### Prerequisites
- Java 21
- Docker & Docker Compose
- PostgreSQL
- Keycloak instance
- RabbitMQ
- MinIO

### Running with Docker

```bash
# Development
docker compose -f docker-compose-wms-dev.yml up -d

# Production
docker compose -f docker-compose-wms-prod.yml up -d
```

### Running locally

```bash
# Build
./gradlew bootJar

# Run with dev profile
java -jar build/libs/*.jar --spring.profiles.active=dev
```

### Running tests

```bash
# Run all tests (requires Docker for Testcontainers)
./gradlew test

# Generate JaCoCo coverage report
./gradlew jacocoTestReport
```

## API Documentation

Swagger UI is available at `http://localhost:{port}/swagger-ui/index.html` when the application is running.

## Project Structure

```
src/main/java/uz/duol/ecopharmwarehouse/
├── config/          # Security, MQ, MinIO, Scheduler, OpenAPI configs
├── controller/      # REST API endpoints (27 controllers)
├── entity/          # JPA entities + RBAC entities
├── enums/           # Domain enums
├── event/           # Application events (User, Product, Unit)
├── exception/       # Global exception handling
├── jobs/            # Scheduled jobs (DB backup, inventory snapshots)
├── listener/        # Event listeners + Audit trail
├── logging/         # AOP-based request/response logging
└── module/          # Business modules
    ├── address/
    ├── inbound/
    ├── inventory/
    ├── outbound/
    ├── crossdocking/
    └── ...
```

## Environment Profiles

| Profile | Purpose |
|---------|---------|
| `dev` | Local development with debug port exposed |
| `test` | Integration testing with Testcontainers |
| `stage` | Pre-production environment |
| `prod` | Production deployment |
