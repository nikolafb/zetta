# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="Nikola Todorov"

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/*.jar

# Copy the packaged jar file into the container
COPY target/initializer-0.0.1-SNAPSHOT.jar zetta.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/zetta.jar"]