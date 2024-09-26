#Java Runtime
FROM openjdk:17-jdk-slim

LABEL key="Cup Of Java Tech" 

WORKDIR /app

COPY target/volume-readwrite-0.0.1-SNAPSHOT.jar /app/volume-readwrite-0.0.1-SNAPSHOT.jar

VOLUME /app/games

ENTRYPOINT ["java", "-jar", "volume-readwrite-0.0.1-SNAPSHOT.jar" ]