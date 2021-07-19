FROM localstack/localstack-full

ENV SERVICES=sqs
ENV DATA_DIR=/tmp/localstack/data

EXPOSE 4566 4571 8080

RUN touch /docker-entrypoint-initaws.d

RUN aws configure set aws_access_key_id DUMMY
RUN aws configure set aws_secret_access_key DUMMY
RUN aws configure set default.region ap-northeast-2
RUN echo "#!/bin/sh" >> /docker-entrypoint-initaws.d
RUN echo "aws sqs create-queue --queue-name deocks-queue" >> /docker-entrypoint-initaws.d

CMD ["echo", "sqs created"]
