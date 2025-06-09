# Stage 1: Build the Spring Boot application using Gradle
FROM gradle:8.6.0-jdk21-alpine AS build
WORKDIR /app

# Copy only build-related files first for efficient layer caching
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/

# Download dependencies to cache them
RUN gradle dependencies --no-daemon || true

# Copy the rest of the application code
COPY . .

# Build the bootJar (skip tests for faster build, override with --no-build-cache if needed)
RUN gradle bootJar --no-daemon -x test

# Stage 2: Create a lightweight runtime image
FROM eclipse-temurin:21-jre-alpine

# Install only what's necessary
RUN apk add --no-cache postgresql-client gzip

# App directory
WORKDIR /opt/app

ENV SPRING_PROFILE=dev \
    SERVER_PORT=9091 \
    JAVA_OPTS="-Xmx512m" \
    EXTRA_ARGS=""
# Copy the fat jar from the build stage
COPY --from=build /app/build/libs/*.jar app-wms.jar

# Ports (standard + debug)
EXPOSE 9091 9092

# Use exec form to ensure signal handling works (e.g., for shutdown)
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app-wms.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT --spring.profiles.active=$SPRING_PROFILE $EXTRA_ARGS"]
