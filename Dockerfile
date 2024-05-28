# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

# Copy the pom.xml file
COPY pom.xml /usr/src/app/

# Set the working directory
WORKDIR /usr/src/app

# Copy the source code
COPY src /usr/src/app/src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Create the final image
FROM openjdk:17-slim

# Copy the built jar from the previous stage
COPY --from=build /usr/src/app/target/aiplusartifact-0.0.1-SNAPSHOT.jar.original app.jar

# Expose the port
EXPOSE 3001

# Command to run the application
CMD ["java", "-jar", "app.jar"]
