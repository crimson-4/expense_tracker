# Use a lightweight Java 17 runtime
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the Spring Boot jar file into the image
COPY target/expense-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
