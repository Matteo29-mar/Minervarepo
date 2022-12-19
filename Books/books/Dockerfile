FROM openjdk:17-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ADD ./target/books-0.0.1-SNAPSHOT.jar ./target/books-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} minerva-books.jar
ENTRYPOINT ["java","-jar","/minerva-books.jar"]