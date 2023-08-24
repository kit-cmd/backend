FROM openjdk:11-jre-slim
WORKDIR /app
COPY notification-server.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
