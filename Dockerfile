FROM openjdk:11

EXPOSE 8080

COPY target/Vocaliz-0.0.1-SNAPSHOT.jar /app/
WORKDIR /app

ENTRYPOINT ["java", "-jar", "Vocaliz-0.0.1-SNAPSHOT.jar"]