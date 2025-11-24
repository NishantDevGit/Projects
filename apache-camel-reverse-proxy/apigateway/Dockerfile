FROM openjdk:21-jdk-slim
EXPOSE 8080
EXPOSE 9090
ADD target/apigateway-0.0.1-SNAPSHOT.jar apigateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/apigateway-0.0.1-SNAPSHOT.jar"]