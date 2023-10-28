# Webauthn Demo Application

A POC project to demonstrate how passwordless authentication works

## Prerequisites

**Java 21**: This project uses Java 21. If you don't have Java 21, you can install it using OpenJDK on
the [OpenJDK website](https://openjdk.java.net/install/);

**Spring Boot 3**: This project uses Spring Boot version 3.1.4. For more info,
visit [Spring Boot website](https://spring.io/projects/spring-boot);

**Thymeleaf**: This project uses Thymeleaf template engine for generating HTML in Java application. For more info,
visit [Thymeleaf website](https://www.thymeleaf.org/);

**Project Lombok**: This project uses Lombok tools to simplify boilerplate code for defining Java objects.
For more info, visit [Project Lombok website](https://projectlombok.org/);

**MySQL**: This project uses relational MySQL database version 8.1.0 to manage required entities in tables.
You can install it on the [MySQL website](https://www.mysql.com/);

**Yubico Webauthn**: This project uses Yubico Webauthn library which is an API that simplifies integration with strong
authentication into applications using support built in to all leading browsers and platforms.
For more info, visit [Yubico website](https://www.yubico.com/authentication-standards/webauthn/).

## Getting Started

To install this demo application, run the following command using Git:

```bash
git clone https://github.com/Jappware/webauthn.git
```

This will get a copy of the project installed locally.

### MySQL Configuration

To access webauthn database you can install [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)
and configure its connection with username and password defined in application.properties of the project
(in this scenario we configure it in Intellij Idea IDE):

![Screenshot 2023-10-27 at 16.10.57.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fyf%2Fnxfzt3_14kg4hvqc25m191_40000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_b0fuDb%2FScreenshot%202023-10-27%20at%2016.10.57.png)

After configuring DB start the application using your IDE or run:

```bash
mvn spring-boot:run
```

You can now access it by opening http://localhost:8080
