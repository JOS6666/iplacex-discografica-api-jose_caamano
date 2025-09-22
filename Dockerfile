# ---------- Stage 1: Build with Gradle ----------
FROM gradle:8.7-jdk17 AS build
WORKDIR /home/gradle/src
COPY . .
# Build a bootable jar
RUN gradle clean bootJar --no-daemon

# ---------- Stage 2: Run with OpenJDK ----------
FROM eclipse-temurin:17-jre-alpine AS runtime
ENV JAVA_OPTS=""
WORKDIR /app
# Copy the jar built by Gradle
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
# Use env var MONGODB_URI for DB connection (configured in application.properties)
ENTRYPOINT ["/bin/sh", "-c", "java $JAVA_OPTS -jar app.jar"]
