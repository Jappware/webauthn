# WebAuthn Demo Application

A POC project to demonstrate how passwordless authentication works.

This project is a demo for our articles: [Intro to Passwordless Authentication](https://www.jappware.com/insights/intro-to-passwordless-authentication/)
and [Passwordless Authentication with WebAuthn](https://medium.com/@jappware/passwordless-authentication-with-webauthn-4bfe7c34ee95)

## Prerequisites

**Java 21**: If you don't have it, you can refer the [OpenJDK website](https://openjdk.java.net/install/) for instructions, or use the [SDKMan](https://sdkman.io/)

**Spring Boot 3.1.4**: [Spring Boot website](https://spring.io/projects/spring-boot);

**Thymeleaf**: template engine is used for HTML rendering in Java application: [Thymeleaf website](https://www.thymeleaf.org/);

**Project Lombok**: simplifies boilerplate code for defining Java objects. [Project Lombok website](https://projectlombok.org/);

**MySQL**: version 8.2.0 [MySQL website](https://www.mysql.com/);

**Yubico Webauthn**: library - an API that simplifies integration with strong authentication into applications using support built in to all leading browsers and platforms.
For more info, visit [Yubico website](https://www.yubico.com/authentication-standards/webauthn/).

## Getting Started

To install this demo application, clone our Git repo

```bash
git clone https://github.com/Jappware/webauthn.git
```

### MySQL Configuration

You can use docker-compose to start the MySQL database in a container:
```shell
docker-compose up
```

Alternatively, you may install your local [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) and configure its connection with username and password defined in application.properties of the project (in this scenario we configure it in Intellij Idea IDE):
<img width="1116" alt="Screenshot 2023-10-28 at 18 34 42" src="https://github.com/Jappware/webauthn/assets/71008388/994864a0-e0c2-458c-89e7-5a0b17f30b5d">

After configuring DB start the application using your IDE or run:

```bash
mvn spring-boot:run
```

You can now access it by opening http://localhost:8080
