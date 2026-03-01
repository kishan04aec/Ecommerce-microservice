# Ecommerce Microservice (Spring Boot + Spring Cloud)

A small Spring Cloud microservice setup with Config Server, Eureka, and three domain services (product, order, user). It uses RabbitMQ for Spring Cloud Bus refresh and Docker for local infrastructure.

## Modules

- configserver: Spring Cloud Config Server (native profile)
- eureka: Service registry (Eureka Server)
- product: Product service (PostgreSQL)
- order: Order service (PostgreSQL)
- user: User service (MongoDB)

## Tech Stack

- Java 25
- Spring Boot 4.0.x
- Spring Cloud 2025.1.0
- Maven
- Docker

## Ports

- Config Server: 8888
- Eureka: 8761
- Product Service: 8081
- User Service: 8082
- Order Service: 8083

## Configuration Overview

Configs live in:


