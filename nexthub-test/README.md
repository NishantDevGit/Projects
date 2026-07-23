# Nexthub Test Application

A Spring Boot application to generate random node details and serve them through REST APIs.

The application uses an in-memory cache to store generated nodes and creates new nodes only when required.

---

## Technology Stack

- Java 21
- Spring Boot 3.x
- Maven
- Spring Cache
- Caffeine Cache
- Docker
- Virtual Threads

---

# Features

- Generate random node information
- Store nodes in memory cache
- Incremental node creation
- Reuse cached nodes for future requests
- Maximum node limit configuration
- Query node by node ID
- Dummy POST API for request testing
- Docker support
- External log directory support

---

# Application Flow

```
Client Request
      |
      |
      v
Node Controller
      |
      |
      v
Node Service
      |
      |
      v
Caffeine Cache
      |
      |
      v
Generate Missing Nodes
```

---

# Cache Behavior

The application does not create all nodes at startup.

Nodes are created only when requested.

## Example 1

Request:

```
GET /nexthub/node?limit=10
```

Creates:

```
NODE-000001
NODE-000002
...
NODE-000010
```

Cache contains:

```
10 Nodes
```

---

## Example 2

Request:

```
GET /nexthub/node?limit=20
```

Existing:

```
10 Nodes
```

Creates only:

```
NODE-000011
...
NODE-000020
```

Cache contains:

```
20 Nodes
```

---

## Maximum Limit

Configuration:

```properties
nexthub.node.max-limit=1000
```

Request:

```
GET /nexthub/node?limit=2000
```

Behavior:

- No error
- Maximum 1000 nodes returned

---

# Node Object Example

Response:

```json
[
  {
    "nodeId": "NODE-000001",
    "nodeName": "Server-1234",
    "ipAddress": "192.168.1.10",
    "uptimeSeconds": 234567,
    "lastStartup": "2026-07-23T10:30:00"
  }
]
```

---

# REST APIs

## Get Nodes

### Request

```
GET /nexthub/node?limit=100
```

Parameters:

| Parameter | Default | Maximum |
|---|---|---|
| limit | 10 | 1000 |

Example:

```
http://localhost:8080/nexthub/node?limit=50
```

---

## Get Node By ID

Request:

```
GET /nexthub/node/{nodeId}
```

Example:

```
GET /nexthub/node/NODE-000001
```

---

## Dummy POST API

This API is only for testing.

It does not save data.

It returns the same request body with HTTP status `201 Created`.

Request:

```
POST /nexthub/node
```

Example body:

```json
{
  "nodeName":"test-server",
  "ip":"10.10.10.10"
}
```

Response:

HTTP:

```
201 Created
```

Body:

```json
{
  "nodeName":"test-server",
  "ip":"10.10.10.10"
}
```

---

# Configuration

## application.properties

```properties
spring.application.name=nexthub-test

server.port=8080

spring.threads.virtual.enabled=true

spring.cache.type=caffeine

nexthub.node.max-limit=1000
```

---

# Running Locally

Requirements:

- Java 21
- Maven

Build:

```
mvn clean package -DskipTests
```

Run:

```
java -jar target/nexthub-test.jar
```

Application URL:

```
http://localhost:8080
```

---

# Running From IntelliJ

Run:

```
NexthubTestApplication.java
```

Logs will appear in:

```
Console
```

---

# Docker Support

## Build Docker Image

First create application jar:

```
mvn clean package -DskipTests
```

Build image:

```
docker build -t nexthub-test .
```

---

# Run Docker Container

Create log directory:

```
mkdir ../logs
```

Run:

```
docker run -d \
--name nexthub-test \
-p 8080:8080 \
-e NEXTHUB_NODE_MAX_LIMIT=1000 \
-e SPRING_PROFILES_ACTIVE=docker \
-v "$(pwd)/../logs:/app/logs" \
--restart unless-stopped \
nexthub-test
```

---

# Docker Logs

Application logs are stored outside the container.

Host:

```
../logs/nexthub-test.log
```

View logs:

```
tail -f ../logs/nexthub-test.log
```

or:

```
docker logs -f nexthub-test
```

---

# Docker Commands

## Check Running Container

```
docker ps
```

---

## Stop Container

```
docker stop nexthub-test
```

---

## Remove Container

```
docker rm nexthub-test
```

---

# Project Structure

```
nexthub-test
|
├── pom.xml
├── README.md
├── Dockerfile
├── build-run.sh
├── build-run.ps1
|
└── src
    |
    └── main
        |
        ├── java
        |   └── com.nexthub.test
        |       |
        |       ├── controller
        |       ├── service
        |       ├── model
        |       └── config
        |
        └── resources
            |
            ├── application.properties
            └── application-docker.properties
```

---

# Future Enhancements

Possible improvements:

- Spring Boot Actuator health checks
- Prometheus metrics
- Load testing
- Kubernetes deployment
- Distributed cache support
- Database persistence