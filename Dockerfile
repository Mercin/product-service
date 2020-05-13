FROM openjdk:8-jre-alpine
MAINTAINER Martina Terlevic

COPY ./build/libs/product-service.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "product-service.jar"]

ENV FIXER_API_KEY="$FIXER_API_KEY"