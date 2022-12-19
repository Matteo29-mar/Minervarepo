FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} minerva-books.jar
ENTRYPOINT ["java","-jar","/minerva-books.jar"]