FROM maven:3.8.3-openjdk-17 AS build
COPY books/src /home/app/src
COPY books/pom.xml /home/app/
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 9011
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
