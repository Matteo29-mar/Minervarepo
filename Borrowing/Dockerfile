FROM maven:3-sapmachine-17 as builder 
COPY Borrowing/src/ /app/src/
COPY Borrowing/pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8102
ENTRYPOINT ["java","-jar","app.jar"]
