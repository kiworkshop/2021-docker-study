# 4회차 과제

## 과제를 통해 어떤 것을 학습할 것인가?

- Docker compose 설정을 저장할 yaml 파일 작성에 익숙해지기
- 실제로 Docker compose와 yaml파일을 사용해 컨테이너를 관리해 본다.

## 연습 과제

- 2회차에서 만들었던 Web-app, localstack sqs, redis 컨테이너를 doceker-compose 를 이용해 똑같이 구현해본다.

### 요구사항
- [ ] docker-compose.yml 파일을 만들고, 해당 파일이 위치하는 디렉터리에서 docker-compose up -d 만 입력하면 2회차의 1단계 요구사항들을 만족할 수 있도록 컨테이너들이 구축되어야 한다.
- [ ] web-app은 docker hub에 올라와있는 [dusol92/web-app](https://hub.docker.com/r/dusol92/web-app) 이미지를 활용하거나, 또는 [깃헙 과제저장소에 업로드된 Dockerfile](https://github.com/kiworkshop/2021-docker-study/tree/master/2%ED%9A%8C%EC%B0%A8/deocks/docker-web-app) 이미지를 활용한다. (자신이 직접 만든 이미지를 활용하여도 좋습니다.)
- [ ] locakstack sqs는 docker hub에 올라와있는 [dusol92/localstack-sqs](https://hub.docker.com/repository/docker/dusol92/localstack-sqs) 이미지를 활용하거나, 또는 자신이 직접 만든 이미지를 활용한다.
- [ ] redis는 docker hub [공식 redis 이미지](https://hub.docker.com/_/redis)를 활용한다.
- [ ] deprecated된 --link 옵션을 사용하지 않고 web-app에서 localstack sqs, redis와 컨테이너간 통신을 할 수 있도록 네트워크 환경을 구축한다.
- [ ] 컨테이너간 의존관계에 따라 순차적으로 컨테이너들이 구축되도록 한다.
- [ ] docker-compose.yml 파일이 있는 위치에서 ```docker-compose down -v``` 커멘드를 이용해 순차적으로 컨테이너들을 중지하고, 삭제되는 것을 확인한다.

### hint
- web-app에서 sqs 연결이 잘 되지 않는 경우 : https://howchoo.com/devops/how-to-add-a-health-check-to-your-docker-container 참고
- localstack docker container health check 예시 : https://github.com/localstack/localstack/issues/1095
- docker-compose depends_on의 condition syntax를 이용하면 의존하는 컨테이너의 라이프사이클을 이용해서 컨테이너 실행 타이밍을 조절할 수 있다. (예시 : https://github.com/docker/compose/issues/7681)
