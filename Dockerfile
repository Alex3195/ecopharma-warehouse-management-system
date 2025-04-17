# Stage 1: Build the Spring Boot application using Gradle
FROM gradle:8.6.0-jdk21-alpine AS build
WORKDIR /app

# Copy Gradle configuration for caching dependencies
COPY gradle/ gradle/
RUN gradle --no-daemon build || true                         # Prevent build failure due to test failures during caching

# Copy source code and build the JAR
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Create a lightweight runtime image
FROM eclipse-temurin:21-jre-alpine

# Install required tools only
RUN apk add --no-cache postgresql-client gzip

# App directory
WORKDIR /opt/app

# Copy the built application JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Expose required ports
EXPOSE 9091 9092

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
