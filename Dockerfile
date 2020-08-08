FROM maven:3.5-jdk-8-alpine
MAINTAINER Wavelabs Team
EXPOSE 8086
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/TeamManagementDomain.jar TeamManagementDomain.jar
ENTRYPOINT ["java","-jar","/TeamManagementDomain.jar"]