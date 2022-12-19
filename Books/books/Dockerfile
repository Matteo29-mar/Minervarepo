FROM openjdk:17-alpine
WORKDIR /app
COPY Books/books/mvn/ ./mvn
COPY Books/books/mvnw Books/books/pom.xml ./
RUN Books/books/mvnw dependency:go-offline
RUN mkdir ./src
COPY Books/books/src/ /app/src
CMD ["./mvnw", "spring-boot:run"]