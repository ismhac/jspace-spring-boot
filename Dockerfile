# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/jspace-1.0.jar /app/jspace-service-1.0.jar

# Expose the port your Spring Boot application listens on (default is 8080)
EXPOSE 8081

# Run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "jspace-service-1.0.jar"]