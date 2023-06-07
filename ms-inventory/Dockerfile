FROM openjdk:11.0.3-jdk-slim-stretch
ARG DEPLOY_ENV
ENV DEPLOY_ENV=$DEPLOY_ENV
ADD build/libs/*.jar dockerapp.jar
ENTRYPOINT ["java", "-jar", "/dockerapp.jar", "--spring.profiles.active=${DEPLOY_ENV}"]