# Ecommerce Microservice (Spring Boot + Spring Cloud)

A small Spring Cloud microservice setup with Config Server, Eureka, and three domain services (`product`, `order`, `user`). It uses RabbitMQ for Spring Cloud Bus refresh and Docker for local infrastructure.

## Modules
- `configserver`: Spring Cloud Config Server (native profile) serving config from `configserver/src/main/resources/config/`
- `eureka`: Service registry (Eureka Server)
- `product`: Product service (PostgreSQL)
- `order`: Order service (PostgreSQL)
- `user`: User service (MongoDB)

## Tech Stack
- Java 25
- Spring Boot 4.0.x
- Spring Cloud 2025.1.0
- Maven (with `mvnw` wrappers)
- Docker Compose for Postgres, RabbitMQ, pgAdmin

## Ports
- Config Server: `8888`
- Eureka: `8761`
- Product Service: `8081`
- User Service: `8082`
- Order Service: `8083`
- Postgres: `5432`
- pgAdmin: `5050`
- RabbitMQ: `5672` (AMQP), `15672` (Management UI)

## Configuration Overview
Configs live in `configserver/src/main/resources/config/`:
- `product-service.yaml`: PostgreSQL settings; expects `DB_USER` and `DB_PASSWORD` env vars
- `order-service.yml`: PostgreSQL settings
- `user-service.yaml`: MongoDB settings

Each service imports config from the config server via:
```
optional:configserver:http://localhost:8888
```

## Service Functionality
- Config Server: Serves centralized config from `configserver/src/main/resources/config/` using the native file system backend.
- Eureka Server: Service discovery registry for all services.
- Product Service (`/api/products`):
- `POST /api/products` create a product
- `GET /api/products` list active products
- `GET /api/products/{id}` fetch a product by id (active only)
- `PUT /api/products/{id}` update a product
- `DELETE /api/products/{id}` soft-delete a product (sets active=false)
- `GET /api/products/search?keyword=...` search products by keyword
- User Service (`/api/users`):
- `POST /api/users` create a user
- `GET /api/users` list users
- `GET /api/users/{id}` fetch a user by id
- `PUT /api/users/{id}` update a user
- Order Service:
- `POST /api/orders` create an order from the current cart; requires header `X-User-ID`
- Cart Service:
- `POST /api/cart` add item to cart; requires header `X-User-ID`
- `GET /api/cart` list cart items; requires header `X-User-ID`
- `DELETE /api/cart/items/{productId}` remove cart item; requires header `X-USER-ID`
- Order service calls Product service at `http://192.168.0.106:8081/` via `ProductServiceClients` to validate product and stock before cart add.

## Prerequisites
- Java 25 installed
- Docker (for Postgres/RabbitMQ/pgAdmin)

## Local Setup
1. Start infrastructure services:
```
docker compose up -d
```

2. Start Config Server:
```
./mvnw -f configserver/pom.xml spring-boot:run
```

3. Start Eureka Server:
```
./mvnw -f eureka/pom.xml spring-boot:run
```

4. Start domain services (in any order):
```
./mvnw -f product/pom.xml spring-boot:run
./mvnw -f user/pom.xml spring-boot:run
./mvnw -f order/pom.xml spring-boot:run
```

## Notes
- The config server uses a local absolute path in `configserver/src/main/resources/application.yaml`. If you move the repo, update `spring.cloud.config.server.native.search-locations` to your local path.
- `product-service` expects `DB_USER` and `DB_PASSWORD` environment variables (see `product-service.yaml`).
- RabbitMQ is required for Spring Cloud Bus (`spring-cloud-starter-bus-amqp`).

## Useful URLs
- Eureka dashboard: `http://localhost:8761`
- RabbitMQ UI: `http://localhost:15672`
- pgAdmin: `http://localhost:5050`

## Project Structure
```
.
├─ configserver
├─ eureka
├─ product
├─ order
├─ user
└─ docker-compose.yml
```

Screenshot : 
<img width="2026" height="1464" alt="image" src="https://github.com/user-attachments/assets/9359aef4-cec1-4536-818d-932a0aa4458f" />
Get users api ss: 
<img width="2160" height="1600" alt="image" src="https://github.com/user-attachments/assets/ccbc7cb0-ec4d-4793-b07d-e8ab417f41ce" />
product Api ss: 
<img width="2070" height="1448" alt="image" src="https://github.com/user-attachments/assets/6deeb427-3706-43fa-b466-4253991cfc55" />



