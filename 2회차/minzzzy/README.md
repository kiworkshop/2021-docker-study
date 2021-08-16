# 2회차 과제

## [redis를 이용한 간단 어플리케이션](https://github.com/minzzzy/lab/tree/feature/redis-test)
docker-compose 이용하여 컨테이너 실행 
- `docker-compose.yml`
```
version: "3.9"
services:
  app:
    build:
      context: .
      args:
        buildVersion: 1.0.0
    ports:
      - "8080:8080"
    links:
      - "cache:redis"
  cache:
    image: "redis:latest"
    hostname: redis
    ports:
      - "6379:6379"
```