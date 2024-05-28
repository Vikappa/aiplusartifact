# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-8-alpine AS build

# Set the working directory
WORKDIR /usr/src/app

# Copy the pom.xml file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Run the Maven build
RUN mvn clean package

# Stage 2: Create the final image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /usr/src/app

# Copy the jar file from the build stage
COPY --from=build /usr/src/app/target/*.jar ./app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]
