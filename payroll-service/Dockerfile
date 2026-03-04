FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/payroll-service.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]