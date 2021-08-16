### 요구사항

#### 1단계
- [x]  SQS listener health check 성공 메시지
- [x]  Redis health check 성공 메시지
- [x]  SQS에 메시지 송신하여 API server에서 메시지 수신 로그 찍히는 것 확인
- [x]  SQS에 메시지 송신하여 API server에서 Redis에 메시지 history 저장하는 것 확인

#### 2단계
- [x]  위에서 만든 Localstack 컨테이너를 재시작 (stop한 후에 다시 start) 하면 만들어두었던 queue가 사라지고 처음 상태가 된다. 뿐만 아니라, 다른 사람이 위의 api 서버를 이용하려면 localstack 이미지를 pull 받아서 queue를 생성하는 작업을 똑같이 해 주어야 한다. Localstack을 base image로 하는 새로운 이미지를 만들어서(이하 sqs image), port 설정만 잘 해주면 queue를 수동으로 생성해주지 않아도 되게끔  queue가 자동으로 생성된 상태의 이미지를 하나 만들어보자.
- [x]  1단계 요구사항을 모두 만족하도록 하는 sqs image, redis image가 모두 준비되었다면, 주어진 web-app-1.0.jar 파일과 Dockerfile을 이용해 API server도 도커 이미지로 만들어보자. 이미지로 만든 뒤에, 앞서 준비해 둔 두 컨테이너(sqs image, redis image로 만든 컨테이너)를 띄우고, API server 컨테이너를 띄워 보자. 그리고 1단계 요구사항들이 잘 동작하는지 확인 해 보자.
- [x]  sqs image, API server image를 성공적으로 만들었다면 개인 docker hub repository를 하나 만들고, 생성한 이미지를 배포해 보자. 배포할 때에는 다른 사람들이 쉽게 사용할 수 있도록 간단한 문서도 추가해 보도록 하자. (문서 예시 참고: [https://hub.docker.com/r/ngrinder/controller](https://hub.docker.com/r/ngrinder/controller/))

#### API 서버 기본 설정
- 연결 시도할 SQS endpoint : localhost:4566 
- 연결 시도할 SQS 이름 : deocks-queue
- 연결 시도할 Redis endpoint : localhost:6379

### Reference

[AWS SQS CLI](https://docs.aws.amazon.com/cli/latest/reference/sqs/)

[Localstack Docker hub](https://hub.docker.com/r/localstack/localstack)

[Localstack Github](https://github.com/localstack/localstack)


***


### 1. Redis 컨테이너 실행
```bash
  docker run --name redis -p 6379:6379 -d redis
  docker container ps -a
```

***

### 2. localstack
#### 1) 컨테이너 실행
```bash
docker run --name localstack -p 4566:4566 -p 4571:4571 -p 8080:8080 --env SERVICES=sqs --env DATA_DIR=/tmp/localstack/data localstack/localstack
```
- DATA_DIR: 영구 데이터를 저장하기 위한 로컬 디렉토리

#### 2) AWS-CLI
  - API Server 실행 시 SQS 큐가 생성되어야 오류가 발생하지 않으므로, aws-cli 로 큐와 메시지를 생성한다.
  -  Queue 생성
  ```bash
  aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs create-queue --queue-name deocks-queue
  ```
  - Message 추가
  ```bash
    aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs send-message --queue-url http://localhost:4566/000000000000/deocks-queue --message-body 'first message'
    aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs send-message --queue-url http://localhost:4566/000000000000/deocks-queue --message-body 'second message'
    aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs send-message --queue-url http://localhost:4566/000000000000/deocks-queue --message-body 'third message'
  ```

#### 3) 큐와 메시지가 저장된 컨테이너를 새로운 이미지로 생성
```bash
docker commit localstack localstack-queue
```

#### 4) 새로운 컨테이너 실행
```bash
docker container stop localstack
docker run --name localstack-queue -p 4566:4566 -p 4571:4571 -p 8080:8080 localstack-queue
```

#### 5) Docker Hub 저장
```bash
docker login --username=pageprologue
docker tag localstack-queue pageprologue/localstack-queue
docker push pageprologue/localstack-queue
```

***

### 3. API server
#### 1) Web app에서 제공하는 API
```bash
/api/sqs-listener/health

/api/redis/health
/api/redis/current-count
/api/redis/{id}
```

### 2) API server 실행
```bash
java -jar web-app-1.0.jar
```

#### 3) DockerFile 빌드
```bash
docker build --tag api-server .
docker run --link localstack-queue:sqs-host --link redis:redis-host -p 8081:8081 api-server
```

#### 4) Docker Hub 저장
```bash
docker login --username=pageprologue
docker tag api-server pageprologue/api-server
docker push pageprologue/api-server
```

***

### 4. Docker Hub
https://hub.docker.com/repository/docker/pageprologue/localstack-queue

https://hub.docker.com/repository/docker/pageprologue/api-server

***


### Hint
```
Hint 1- aws cli
Localstack을 컨테이너로 띄우는 데에 성공하였다면, sqs queue를 생성하고 메시지를 보낼 때 aws cli를 이용해야 합니다.
아래는 미션을 수행하기 위한 command 예시입니다.

새로운 SQS 생성 command :
aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs create-queue --queue-name deocks-queue

현재 생성되어있는 queue 확인:
aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs list-queues

특정 Queue에 메시지 전송
aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs send-message --queue-url http://localhost:4566/000000000000/deocks-queue --message-body 'hi deocks'

특정 Queue에서 메시지 수신
aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs receive-message --queue-url http://localhost:4566/000000000000/deocks-queue

특정 Queue의 현재 상태 조회
aws --endpoint-url=http://localhost:4566 --region ap-northeast-2 sqs get-queue-attributes --queue-url [http://localhost:4566/000000000000/deocks-queue](http://localhost:4566/000000000000/deocks-queue)

```


```
Hint 2 - Localstack container 생성 관련

Localstack에서 sqs 서비스를 이용할 수 있도록 컨테이너를 생성하려면 아래 command 활용

docker pull localstack/localstack
docker run -p 4566:4566 -p 4571:4571 -p 8080:8080 --env SERVICES=sqs {{locakstack-image-id}}
```


```
Hint 3 - web-app에 설정되어있는 sqs, redis endpoint를 app 실행시에 외부에서 주입해주고 싶다면

java -jar web-app-1.0.jar --sqs.endpoint=http://{{$sqs-host}}:{{$sqs-port}} --redis.host={{$redis-host}} --redis.port={{$redis-port}}
```
