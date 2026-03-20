# syntax=docker/dockerfile:1

###
# Stage 1: Build with Maven Wrapper
###
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copy Maven wrapper + pom first for better caching
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/

# Copy application sources
COPY src ./src

RUN chmod +x mvnw
RUN ./mvnw -B clean package

###
# Stage 2: Run with a lightweight JRE
###
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Render will set PORT; application.properties uses ${PORT:8080}
ENV PORT=8080

COPY --from=build /workspace/target/jwt-auth-demo.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

