FROM maven:3.10.0-eclipse-temurin-27-alpine
WORKDIR /tests
copy pom.xml .
copy src ./src
CMD ["mvn", "-B", "test"]