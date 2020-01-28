FROM openjdk:11.0.6-jre-slim
ADD build/libs/simple-http-0.0.1-SNAPSHOT-all.jar simple-http.jar
ENTRYPOINT ["java", "-jar", "simple-http.jar"]