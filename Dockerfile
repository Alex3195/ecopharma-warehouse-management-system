# Stage 1: Build the Spring Boot application using Gradle
FROM gradle:8.6.0-jdk21-alpine AS build
WORKDIR /app

# Copy only essential files for dependency caching
COPY build.gradle settings.gradle gradle.properties ./
COPY gradle gradle
RUN gradle --no-daemon build || return 0  # Cache dependencies

# Now copy the rest of the source code
COPY . .
RUN gradle bootJar --no-daemon

# Stage 2: Create a lightweight runtime image
FROM eclipse-temurin:21-jre-alpine

# Install only the required tools: pg_dump (via postgresql-client) and gzip
RUN apk add --no-cache postgresql-client gzip

# Set working directory
WORKDIR /opt/app

# Copy built jar from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose necessary ports
EXPOSE 9091 9092

# Use exec form of CMD to properly signal processes
ENTRYPOINT ["java", "-jar", "app.jar"]
