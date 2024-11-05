FROM jenkins/jenkins:jdk17
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]