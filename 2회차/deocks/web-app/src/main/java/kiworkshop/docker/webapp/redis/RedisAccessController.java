package kiworkshop.docker.webapp.redis;

import kiworkshop.docker.webapp.redis.domain.MessageHistory;
import kiworkshop.docker.webapp.redis.response.RedisResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RedisAccessController {

    private final RedisAccessService redisAccessService;

    @GetMapping("/api/redis/hi")
    public Mono<RedisResponse<String>> hiRedis() {
        return redisAccessService.countHistories()
            .map(count -> RedisResponse.successWith("Hi, I'm redis"));
    }

    @GetMapping("/api/redis/current-count")
    public Mono<RedisResponse<Integer>> countItems() {
        log.info("Request current count");
        return redisAccessService.countHistories()
            .map(RedisResponse::successWith);
    }

    @GetMapping("/api/redis/{id}")
    public Mono<RedisResponse<MessageHistory>> getItem(@PathVariable String id) {
        log.info("Request Message history " + id);
        return redisAccessService.findHistory(id)
            .map(RedisResponse::successWith);
    }
}
