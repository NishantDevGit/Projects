🚀 API Management Using Apache Camel – Reverse Proxy & Routing Engine (Java 21 + Virtual Threads)

This project demonstrates how to build a lightweight API Management layer using Apache Camel.
It works as a Reverse Proxy Gateway that handles routing, request forwarding, logging, transformation, and basic policies for downstream services.

This project is designed to showcase practical API Gateway patterns using Camel Routes, making it highly valuable for Service integration.


🧩 Project Overview

The API Management layer exposes a single endpoint and forwards requests to internal microservices.
Camel acts as the reverse proxy, performing:

Dynamic routing

Request/response logging

Header enrichment

Error handling

Timeout control


🏗️ Architecture
                 +---------------------------+
                 |   Client / Frontend App   |
                 +-------------+-------------+
                               |
                         (Public API)
                               |
                    +----------v-----------+
                    |  Camel Reverse Proxy |
                    |   API Management     |
                    +----------+-----------+
                               |
         --------------------------------------------------
         |                          |                     |
+--------v--------+     +-----------v--------+  +---------v-------+
| Backend API 1   |     | Backend API 2      |  | Backend API 3   |
+-----------------+     +--------------------+  +-----------------+


Admin API Endpoints (Reverse-Proxied Through Camel)

The API Gateway exposes a set of Admin Management Endpoints under the base path:

http://127.0.0.1:9090/admin/**


These endpoints are routed internally via Apache Camel to different services or external APIs.
The Admin API section demonstrates CRUD operations, metrics extraction, call logs, and service management.


📘 1. Metrics Endpoint
GET /admin/metric

Retrieves platform-level metrics such as:

API response times

Total calls processed

Upstream service health

Failure counts

Virtual thread processing stats

📌 Example:

GET http://127.0.0.1:9090/admin/metric

👤 2. User Management Endpoints

Manage admin users through the API Gateway.

POST /admin/user

Add a new admin user.


📞 3. Call Details Endpoint
GET /admin/call

Fetch the list of all API calls processed through the gateway, including:

Endpoint accessed

Processing time

Status code

Backend service routed

Timestamp

Request ID


🔧 4. Service Management Endpoints

This endpoint allows creating, updating, listing, and deleting upstream services managed by the gateway.

GET /admin/service

Fetch all registered services.

POST /admin/service

Register a new target service.

DELETE /admin/service/{id}

Delete a service by ID.