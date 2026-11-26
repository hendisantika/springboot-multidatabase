# springboot-multidatabase

[![Java CI with Maven](https://github.com/hendisantika/springboot-multidatabase/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/springboot-multidatabase/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.2.0-brightgreen?logo=springboot)

## Introduction

This project is a backend application that uses **Spring Data JPA** with **multiple relational databases** at the same
time. It connects to **MySQL** and **Microsoft SQL Server**, each with its own `DataSource`, `EntityManagerFactory` and
`TransactionManager`. Read more in this article:
[SpringBoot with spring data JPA using multi-data source databases MySql + SQLServer](https://medium.com/@sopheamak/springboot-with-spring-data-jpa-using-multi-data-source-databases-mysql-sqlserver-3ce5f69559)

## Technologies

- Java 25
- Spring Boot 4.2.0 (Spring Web MVC, Spring Data JPA)
- Hibernate / Jakarta Persistence
- MySQL (`com.mysql:mysql-connector-j`) + Microsoft SQL Server (`mssql-jdbc`)
- H2 in-memory database (tests only)
- Lombok
- Maven
- GitHub Actions + Dependabot

## Project Structure

```
src/main/java/com/hendisantika/springbootmultidatabase
├── config
│   ├── MySqlConfig.java          # primary datasource (spring.datasource.*)
│   └── SqlServerConfig.java      # secondary datasource (sqlserver.datasource.*)
├── controller
│   └── TestController.java       # REST endpoints
├── domain
│   ├── mysql/Category.java       # entity stored in MySQL
│   └── sqlserver/Country.java    # entity stored in SQL Server
└── repository
    ├── mysql/CategoryRepository.java
    └── sqlserver/CountryRepository.java
```

Each config class scans **only** its own `domain.*` and `repository.*` package, so every entity/repository is bound to
the right database.

## Prerequisites

- JDK 25
- Maven 3.9+ (or use the bundled `./mvnw`)
- Docker Engine (for running the databases)
- IntelliJ IDEA / Eclipse / VS Code / other tools

## Run the Databases using Docker

### SQL Server

```shell
docker run --name sqlserver1 -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=jEe8BUkAta32sA' \
  -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest

# create the database (only the first time)
docker exec -it sqlserver1 /opt/mssql-tools18/bin/sqlcmd -C -S localhost -U sa -P 'jEe8BUkAta32sA' \
  -Q "CREATE DATABASE multiDB"
```

### MySQL

```shell
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.4
```

The MySQL database `multiDB` is created automatically thanks to `createDatabaseIfNotExist=true` in the JDBC URL.

## Configuration

`src/main/resources/application.properties`

```properties
# MySQL (primary)
spring.datasource.url=jdbc:mysql://localhost/multiDB?createDatabaseIfNotExist=true&serverTimezone=Asia/Jakarta&useSSL=false
spring.datasource.username=root
spring.datasource.password=root
# SQL Server (secondary)
sqlserver.datasource.url=jdbc:sqlserver://127.0.0.1;databaseName=multiDB
sqlserver.datasource.username=sa
sqlserver.datasource.password=jEe8BUkAta32sA
```

## Build, Test & Run

```shell
# build & run all tests (uses in-memory H2 for both datasources, no Docker needed)
mvn clean verify

# run the application (requires MySQL & SQL Server running)
mvn spring-boot:run
```

## REST APIs

```shell
# create a new category (stored in MySQL)
curl -X POST http://localhost:8080/api/category \
  -H 'Content-Type: application/json' \
  -d '{"name":"category1","description":"description1"}'

# create a new country (stored in SQL Server)
curl -X POST http://localhost:8080/api/country \
  -H 'Content-Type: application/json' \
  -d '{"name":"indonesia","isoCode":"id"}'

# get all data from both databases
curl -X GET http://localhost:8080/api/all
```

Response:

```json
{
  "country": [{"id": 1, "name": "indonesia", "isoCode": "id"}],
  "category": [{"id": 1, "name": "category1", "description": "description1"}]
}
```

## CI/CD & Dependency Updates

- **GitHub Actions** (`.github/workflows/maven.yml`): builds and tests the project on JDK 25 (Temurin) for every push
  and pull request to `master`, then submits the Maven dependency graph on push.
- **Dependabot** (`.github/dependabot.yml`): daily Maven updates and weekly GitHub Actions updates, grouped (Spring,
  database drivers, Maven plugins, minor & patch), labelled and using conventional commit prefixes.
