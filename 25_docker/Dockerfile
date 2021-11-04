FROM maven:3.6.3-jdk-8 AS MAVEN_BUILD

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f home/app/pom.xml clean package

FROM openjdk:8-jdk-slim

COPY --from=MAVEN_BUILD home/app/target/25_docker-0.0.1-SNAPSHOT.jar /usr/local/runme/target/25_docker.jar

COPY --from=MAVEN_BUILD home/app/target/lib/ /usr/local/runme/target/lib/

ENTRYPOINT ["java", "-jar", "/usr/local/runme/target/25_docker.jar"]
