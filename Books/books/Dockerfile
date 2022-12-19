FROM openjdk:17-alpine
WORKDIR /minerva/books
COPY mvnw ./
COPY pom.xml ./
RUN mkdir ./src
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "spring-boot:run"]