# Base image with Gradle and JDK 21
FROM gradle:8.6.0-jdk21-alpine

# Set the working directory inside the container
WORKDIR /opt/app

# Define build arguments with default values
ENV SPRING_PROFILE=test
ENV SERVER_PORT=8080
ARG DEBUG_PORT=8081
ARG JAVA_OPTS="-Xmx512m"
ARG EXTRA_ARGS=""

# Copy the JAR file from the build output
COPY ./build/libs/*.jar /opt/app/app.jar

# Expose the server and debug ports
EXPOSE 8080
EXPOSE 8081

# Define the entrypoint to run the application
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar \
    -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT \
    --spring.profiles.active=$SPRING_PROFILE $EXTRA_ARGS"]