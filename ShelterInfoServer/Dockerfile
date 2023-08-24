FROM openjdk:11-jre-slim
WORKDIR /app
COPY shelter-info-server.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
