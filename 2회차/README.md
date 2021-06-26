# 2회차 과제

## 과제를 통해 어떤 것을 실습해볼 것인가?
- 격리된 컨테이너 환경에서 실행할 필요가 있는 무언가를 실제 컨테이너로 만들어 띄워보자.
- 위에서 띄운 컨테이너에서 변경사항을 만들고, 작업한 변경사항을 이미지로 커밋하고, 푸시해보자.
- 푸시한 이미지를 누구나 사용할 수 있도록 간단한 문서를 만들어보자.

## 실습 과제
- localstack docker image를 이용해 로컬 환경에서 가상의 클라우드 환경을 구축해보자.

### stage 1
- 공식 Localstack docker image를 이용해 container를 생성한다.
- Sqs, dynamoDB, Elasticache cluster, DocDB cluster 중 최소 한 가지 이상의 리소스를 생성한다.
- 위에서 리소스를 생성할 때마다 Localstack 도커 이미지를 커밋하여 container에 작업한 내용을 스냅샷으로 남길 수 있도록 한다.
- 작업이 완료되고 locakstack을 base image로 하는 새로운 이미지가 완성되면 해당 이미지를 docker hub에 배포한다.
### stage 2
- 스프링 웹 어플리케이션 서버를 하나 만들고, 위에서 생성한 자원들과 연동하여 동작할 수 있는 간단한 api를 만들어본다. (ex: 간단한 crud, event listener/consumer 등)
- 작업한 웹 어플리케이션 서버를 Dockefile을 활용해 이미지로 만들어본다.
- 위에서 만든 localstack 이미지를 컨테이너로 띄우고, 이미지로 만든 웹 어플리케이션을 localstack 컨테이너에 연결되는 또다른 컨테이너로 띄울 수 있도록 해 본다.
- 스프링 웹 어플리케이션 컨테이너를 docker hub에 배포한다. locakstack 컨테이너와 연동하여 띄워본 경험을 바탕으로 어떻게 두 컨테이너를 연동하여 동작시킬 수 있는지 문서화 해본다.
