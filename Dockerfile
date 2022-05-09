FROM gradle-7.4.2-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

FROM openjdk:11

WORKDIR /app
USER app

COPY --from=build --chown=app:app /home/gradle/src/build/libs/tuum_test-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "tuum_test-0.0.1-SNAPSHOT.jar"]



