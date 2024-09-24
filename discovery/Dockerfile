FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/discovery-0.0.1-SNAPSHOT.jar discovery.jar
ENTRYPOINT ["java", "-jar", "discovery.jar"]
