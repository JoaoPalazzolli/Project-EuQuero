FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -Pdev

EXPOSE 8080

ENTRYPOINT mvn spring-boot:run -Pdev