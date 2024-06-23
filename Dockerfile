# Use an official Gradle image to build the app
FROM gradle:7.3.3-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle build --no-daemon

# Use an official OpenJDK image as the base image
FROM openjdk:17-jdk-slim
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
