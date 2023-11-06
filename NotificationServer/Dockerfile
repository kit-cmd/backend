FROM openjdk:11-jre-slim
WORKDIR /app
COPY build/libs/NotificationServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
