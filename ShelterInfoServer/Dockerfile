FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./DisasterInformationCollectionServer/build/libs/shelter-info-server.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]
