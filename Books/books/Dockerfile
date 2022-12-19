FROM openjdk:17-alpine
WORKDIR /app
COPY .mvn/ ./mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
RUN mkdir ./src
COPY Books/books/src/ /app/src
CMD ["./mvnw", "spring-boot:run"]