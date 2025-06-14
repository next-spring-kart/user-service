# Build Stage
FROM gradle:8.14.2-jdk24 AS build
LABEL MAINTAINER="pritamkundu771@gmail.com"
COPY service /home/gradle/service
WORKDIR /home/gradle/service
RUN gradle bootJar --no-daemon

# Runtime Stage
FROM amazoncorretto:24-alpine3.21-jdk
WORKDIR /app
COPY --from=build /home/gradle/service/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]
