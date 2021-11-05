# Tul-Challenge

This application was developed to solve Tul's Challenge.

Technologies Used

- Spring Boot
- Spring Data JPA
- Lombok
- MySQL


How to Run this application

First change the **src/main/resources/application.properties** with your MySQL instance properties.

Then,

```shell
$ ./gradlew bootRun
```

or create a build using following command,

```shell
$ ./gradlew clean build
```

Then start the JAR file using java

```shell
$ java -jar build/libs/com.tul.challenge-0.0.1-SNAPSHOT.jar
```
