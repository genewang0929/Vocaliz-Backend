FROM openjdk:11

EXPOSE 8080

RUN mkdir -p /project
COPY . /project
WORKDIR /project

COPY  --from=build /project/target/Vocaliz-0.0.1-SNAPSHOT.jar /app/Vocaliz-0.0.1-SNAPSHOT.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "Vocaliz-0.0.1-SNAPSHOT.jar"]
