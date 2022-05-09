FROM gradle:7.4.2-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle clean build -Pversion=0.0.1-SNAPSHOT

FROM openjdk:11
WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/tuum_test-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "tuum_test-0.0.1-SNAPSHOT.jar"]