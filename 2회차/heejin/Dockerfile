FROM adoptopenjdk/openjdk11
WORKDIR /usr/src/app
COPY . .
EXPOSE 8081
CMD ["java", "-jar", "web-app-1.0.jar", "--sqs.endpoint=http://sqs-host:4566", "--redis.host=redis-host", "--redis.port=6379"]