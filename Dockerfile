FROM openjdk:11

EXPOSE 8080

COPY target/Vocaliz-0.0.1-SNAPSHOT.jar /app/Vocaliz-0.0.1-SNAPSHOT.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "/app/Vocaliz-0.0.1-SNAPSHOT.jar"]
