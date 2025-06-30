# 🏗️ Stage 1: Build the Spring Boot application using Gradle
FROM gradle:8.6.0-jdk21-alpine AS build
WORKDIR /app

# Copy Gradle files first for caching
COPY build.gradle settings.gradle ./
COPY gradle/ gradle/
RUN gradle dependencies --no-daemon || true

# Copy the rest of the application
COPY . .

# Build JAR
RUN gradle bootJar --no-daemon -x test

# 🏃 Stage 2: Runtime image with JDK and cert trust
FROM eclipse-temurin:21-jdk-alpine

# Add useful tools
RUN apk add --no-cache postgresql-client gzip

WORKDIR /opt/app

# ⬇️ Copy your application JAR
COPY --from=build /app/build/libs/*.jar app-wms.jar

# ⬇️ Copy the Eureka SSL cert
COPY star_rivermed_uz.crt /usr/local/share/ca-certificates/star_rivermed_uz.crt

# ⬇️ Import the cert into Java truststore
RUN keytool -import -trustcacerts -alias rivermed-cert \
    -file /usr/local/share/ca-certificates/star_rivermed_uz.crt \
    -keystore $JAVA_HOME/lib/security/cacerts \
    -storepass changeit -noprompt

# 🔧 Environment and ports
ENV SPRING_PROFILES_ACTIVE=dev \
    SERVER_PORT=9091 \
    JAVA_OPTS="-Xmx512m" \
    EXTRA_ARGS=""

EXPOSE 9091 9092

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app-wms.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT --spring.profiles.active=$SPRING_PROFILES_ACTIVE $EXTRA_ARGS"]
