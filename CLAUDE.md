# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

Educational examples repository demonstrating Spring Data across multiple database backends, accompanying a codecentric AG blog series. Each module is a standalone Maven project showing repository patterns, query derivation, and configuration for a specific Spring Data technology.

## Build System

Maven multi-module project (Java 8). The root `pom.xml` declares 6 modules.

```bash
# Build and test all modules
mvn clean test

# Build and install all modules
mvn clean install

# Target a specific module
mvn clean test -pl springdata-mongodb

# Run a single test class
mvn test -Dtest=OrderRepositoryTest -pl springdata-mongodb

# Run a single test method
mvn test -Dtest=OrderRepositoryTest#testFind -pl springdata-mongodb
```

The `spring-boot-mongo` module has its own Maven wrapper (`./mvnw`) and a Docker build profile:
```bash
cd spring-boot-mongo
mvn clean package -Pdocker
```

## Modules

| Module | Technology | External Dependencies |
|--------|-----------|----------------------|
| `springdata-jpa` | Spring Data JPA + Hibernate + HSQLDB | None (embedded HSQLDB) |
| `springdata-mongodb` | Spring Data MongoDB 2.1.8 | Local MongoDB or Docker |
| `springdata-redis` | Spring Data Redis 1.6.4 + Jedis | Local Redis required |
| `springdata-neo4j` | Spring Data Neo4j 4.0 + OGM | None (embedded harness) |
| `spring-boot-mongo` | Spring Boot 1.5.8 + MongoDB REST | Local MongoDB or Docker |
| `mongodriver` | MongoDB Java Driver 3.10 | Local MongoDB or Docker |

## Infrastructure

**MongoDB** — used by `springdata-mongodb`, `spring-boot-mongo`, and `mongodriver`:
```bash
# Single-node config is in springdata-mongodb/src/misc/resources/single-node.yml
# Replica set config: rs-dev0.yml
cd spring-boot-mongo && docker-compose up -d   # starts MongoDB 3.4 on port 27017
```

**Redis** — required by `springdata-redis` tests; no embedded alternative:
```bash
redis-server   # must be running locally
```

CI (Travis CI) enables both MongoDB and Redis as services, targeting OpenJDK 11 with `mongodb-org-server 3.4`.

## Architecture Patterns

All modules follow the same Spring Data patterns:
- **Repository interfaces** extend `CrudRepository` / `MongoRepository` / `GraphRepository` etc.
- **Query derivation** via method names (e.g., `findByLastName`, `findByLocationNear`)
- **@Configuration classes** wire up `DataSource` / `MongoClient` / `RedisConnectionFactory`
- **Test classes** use `@RunWith(SpringJUnit4ClassRunner.class)` with XML or Java config

`spring-boot-mongo` differs: it uses Spring Boot auto-configuration and Spring Data REST to expose repositories as HAL/JSON REST endpoints under `/rest` base path, with Actuator enabled.

`mongodriver` demonstrates raw driver usage without Spring Data repositories.

## Source Layout

Each module uses standard Maven layout. `springdata-mongodb` additionally uses `src/misc/java` and `src/misc/resources` for supplementary test utilities.
