FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/disaster-info-collection-server.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
