FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the pre-built JAR file
COPY target/json-placeholder-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 