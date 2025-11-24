Spring Boot Microservices Project – API Gateway + Service Discovery

This project demonstrates a complete Microservices Architecture built using Spring Boot, Spring Cloud Netflix Eureka, and API Gateway.
It includes 3 independent services:

HelloWorld Service

BookMyMovie Service

UserManagement Service

All services are registered with Service Discovery (Eureka Server) and exposed through a centralized API Gateway.


Architecture Overview

                  +---------------------+
                  |     API Gateway     |
                  |   (Spring Cloud)    |
                  +----------+----------+
                             |
         ------------------------------------------------
         |                      |                      |
+--------+--------+   +--------+--------+   +----------+---------+
|  HelloWorld     |   | BookMyMovie    |   | UserManagement     |
|  Microservice   |   | Microservice    |   | Microservice       |
+-----------------+   +-----------------+   +--------------------+
         \                    |                      /
          \                   |                     /
        +------------------------------------------------------+
        |               Eureka Service Discovery               |
        +------------------------------------------------------+


Features
✔ Eureka Server

Centralized service registry

Auto-registration of all microservices

Load-balanced service lookup

✔ API Gateway

Single entry point for all clients

Routing to microservices

Dynamic service discovery

Path-based routing

Future-ready for authentication + rate limiting

✔ HelloWorld Microservice

Simple demo service

Health check endpoint

Used to verify gateway routing

✔ BookMyMovie Microservice

Movie listing APIs

Ticket booking sample endpoints

Demonstrates real-world business logic

✔ UserManagement Microservice

Register user

Update user

Get user details

Clean architecture (Controller → Service → Repository)


🛠️ Tech Stack

Java 17+

Spring Boot

Spring Cloud Netflix Eureka

Spring Cloud API Gateway

Maven

Lombok

Actuator

Docker (optional)



Service URLs

After running all services:
Eureka Server
http://localhost:19000

API Gateway

Example routes:

http://localhost:19001/movie
http://localhost:19001/api/v1/user

Individual Microservices (if accessed directly)
BookMyMovie Service:     localhost:19003
UserManagement Service:  localhost:19002

