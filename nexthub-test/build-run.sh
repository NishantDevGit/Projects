#!/bin/bash

APP_NAME="nexthub-test"
IMAGE_NAME="nexthub-test"
CONTAINER_NAME="nexthub-test"
PORT="8080"


echo "=================================="
echo "Building Spring Boot application"
echo "=================================="

mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "Maven build failed"
    exit 1
fi


echo "=================================="
echo "Stopping existing container"
echo "=================================="

docker stop $CONTAINER_NAME 2>/dev/null || true


echo "=================================="
echo "Removing existing container"
echo "=================================="

docker rm $CONTAINER_NAME 2>/dev/null || true


echo "=================================="
echo "Building Docker image"
echo "=================================="

docker build -t $IMAGE_NAME .


if [ $? -ne 0 ]; then
    echo "Docker image build failed"
    exit 1
fi


echo "=================================="
echo "Starting container"
echo "=================================="


docker run -d \
    --name $CONTAINER_NAME \
    -p $PORT:$PORT \
    -e NEXTHUB_NODE_MAX_LIMIT=1000 \
    -e SPRING_PROFILES_ACTIVE=docker \
    -v "$(pwd)/../logs:/app/logs" \
    --restart unless-stopped \
    $IMAGE_NAME


echo "=================================="
echo "Application started"
echo "=================================="


docker ps | grep $CONTAINER_NAME