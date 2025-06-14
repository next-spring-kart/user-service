FROM gradle:8.14.2-jdk24 AS build
LABEL MAINTAINER="pritamkundu771@gmail.com"
COPY service .
RUN gradle bootJar --no-daemon

FROM amazoncorretto:24-alpine3.21-jdk
COPY --from=build target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","app.jar" ]

