FROM openjdk:17-alpine
ARG JAR_FILE=Auth/build/libs/Auth-0.0.1.jar
RUN mkdir /libera
WORKDIR /libera
COPY ${JAR_FILE} /libera
ENTRYPOINT java -jar /libera/Auth-0.0.1.jar