FROM maven:3.8.6-jdk-11-slim AS build

RUN mkdir -p /project
COPY . /project
WORKDIR /project

FROM openjdk:11

EXPOSE 8080

COPY  --from=build /project/target/Vocaliz-0.0.1-SNAPSHOT.jar /app/Vocaliz-0.0.1-SNAPSHOT.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "Vocaliz-0.0.1-SNAPSHOT.jar"]
