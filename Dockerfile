FROM maven:3.5-jdk-8-alpine
MAINTAINER Wavelabs Team
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/search-service-0.0.1-SNAPSHOT.jar search-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/search-service-0.0.1-SNAPSHOT.jar"]
