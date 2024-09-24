FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "gateway-service.jar"]
