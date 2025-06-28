# Use a Maven image with Java 21 to build the Spring Boot app
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper if available (to use ./mvnw)
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./

# Download dependencies before copying source code to leverage Docker cache
RUN ./mvnw dependency:resolve || mvn dependency:resolve

# Copy the source code
COPY src ./src

# Build the application (Skipping tests for faster build)
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Use a Java 21 runtime image to run the application
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
