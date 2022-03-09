FROM azul/zulu-openjdk-alpine:11.0.9-jre-headless
EXPOSE 8080
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]