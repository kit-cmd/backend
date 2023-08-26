FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./UserServer/build/libs/user-server.jar app.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
