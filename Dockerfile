FROM adoptopenjdk:11-jre-hotspot as builder
ARG JAR_FILE=target/authentication.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]