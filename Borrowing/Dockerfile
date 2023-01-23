FROM maven:3-sapmachine-17 as builder 
ARG MVN_ARGS
ADD ./src/ /app/src/
COPY ./pom.xml /app/src/borrowing
WORKDIR /app
RUN mvn --batch-mode package ${MVN_ARGS}

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8102
ENTRYPOINT ["java","-jar","app.jar"]
