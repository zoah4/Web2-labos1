FROM maven:3.9.5-openjdk-18 as builder
WORKDIR /app
COPY src ./src
COPY pom.xml .

RUN mvn clean package -DskipTests

FROM openjdk:18-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]