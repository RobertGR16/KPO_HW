FROM maven:3.8.2-openjdk-17-slim as builder
ARG VERSION=0.0.1
WORKDIR /usr/src/app
COPY ./src ./src
COPY *.xml ./
RUN mvn package -Dversion.application=${VERSION} -DskipTests

FROM openjdk:17-jdk-alpine as release
ARG VERSION=0.0.1
WORKDIR /app

RUN apk add --no-cache curl # Для тестирования

RUN apk add --no-cache dumb-init # Для запуска приложения

COPY --from=builder /usr/src/app/target/TicketService-${VERSION}.jar ./TicketService.jar
EXPOSE 8080
ENTRYPOINT ["dumb-init", "java", "-jar", "-Dmyserver.bindPort=8080", "./TicketService.jar"]