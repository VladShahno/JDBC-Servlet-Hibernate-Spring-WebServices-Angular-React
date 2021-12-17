FROM maven:3.6.3-jdk-8-slim
COPY pom.xml /home/client/
COPY src /home/client/src/
WORKDIR /home/client/
CMD ["mvn", "site:run"]