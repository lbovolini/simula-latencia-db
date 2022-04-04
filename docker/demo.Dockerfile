FROM openjdk:11.0.14.1-jre-bullseye

MAINTAINER Lucas Bovolini lbovolini94@gmail.com

ENV APP_NAME=demo
ENV APP_FOLDER=~/app

RUN addgroup app && adduser --ingroup app app
USER app:app

WORKDIR ${APP_FOLDER}

COPY "./target/${APP_NAME}*.jar" ${APP_NAME}.jar

ENTRYPOINT java -jar ${APP_NAME}.jar "$time"