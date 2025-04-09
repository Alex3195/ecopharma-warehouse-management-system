FROM gradle:8.6.0-jdk21-alpine AS build

WORKDIR /opt/app

ENV SPRING_PROFILE=dev \
    SERVER_PORT=9091 \
    JAVA_OPTS="-Xmx512m" \
    EXTRA_ARGS=""

ARG DEBUG_PORT=9092

COPY ./build/libs/*.jar app-api-wms.jar

EXPOSE 9091 9092

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app-api-wms.jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT --spring.profiles.active=$SPRING_PROFILE $EXTRA_ARGS"]
