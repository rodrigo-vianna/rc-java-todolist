FROM ubuntu:latest AS build
WORKDIR /app

RUN apt-get update && apt-get install openjdk-17-jdk -y && apt-get install maven -y

COPY . .

RUN mvn clean install

FROM openjdk-17-jdk-slim AS production
WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]