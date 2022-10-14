FROM openjdk:11

EXPOSE 8080

COPY . /project
WORKDIR /project

COPY /project/target/Vocaliz-0.0.1-SNAPSHOT.jar /app/Vocaliz-0.0.1-SNAPSHOT.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "Vocaliz-0.0.1-SNAPSHOT.jar"]
