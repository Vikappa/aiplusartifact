# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Copy the pom.xml file
COPY pom.xml /user/src/app/

# Set the working directory
COPY src /usr/src/app/src

RUN mvn -f /user/src/app/pom.xml clean package -DskipTests

FROM openjdk:21-slim

COPY --from=build /user/src/app/target/app.jar app.jar

EXPOSE 3001

# Command to run the application
CMD ["java", "-jar", "app.jar"]
