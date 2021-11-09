FROM openjdk:11
VOLUME /main-app
#RUN ./gradlew clean build run previous dont use memory
ADD build/libs/tul-challenge-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]