FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f home/app/pom.xml clean package

FROM tomcat:9
COPY --from=MAVEN_BUILD /home/app/target/spring.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]