FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD ./fse-pm-app.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar /app.jar" ]