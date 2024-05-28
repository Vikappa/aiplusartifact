FROM maven:3.9.6-eclipse-temurin-8-alpine AS build

COPY pom.xml usr/src/app/
COPY src usr/src/app/src

RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:21-jre-slim

COPY --from=build /usr/src/app/target/aiplusartifact-0.0.1-SNAPSHOT.jar /usr/src/app/target/aiplusartifact-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/src/app/target/aiplusartifact-0.0.1-SNAPSHOT.jar"]