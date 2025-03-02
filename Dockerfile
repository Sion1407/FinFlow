# Use a base image with Java and Spring Boot
FROM openjdk:17-jdk-alpine

# Set the working directory
# WORKDIR /app

# Copy the JAR file into the container
COPY target/FinFlow-0.0.1-SNAPSHOT.jar FinFlow-0.0.1-SNAPSHOT.jar

# Expose the port
# EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "FinFlow-0.0.1-SNAPSHOT.jar"]