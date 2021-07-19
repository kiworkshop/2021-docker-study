package kiworkshop.docker.webapp.redis;

import kiworkshop.docker.webapp.redis.domain.MessageHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisAccessService {
    private static final Duration TIMEOUT = Duration.ofMillis(1000);

    private final ReactiveRedisOperations<String, MessageHistory> messageHistoryOps;

    public Mono<String> saveHistory(MessageHistory message) {
        return messageHistoryOps.opsForValue().set(message.getId(), message)
            .doOnError(throwable -> log.error("Failed to save history : ", throwable))
            .thenReturn(message.getId())
            .timeout(TIMEOUT);
    }

    public Mono<MessageHistory> findHistory(String id) {
        return messageHistoryOps.opsForValue().get(id)
            .timeout(TIMEOUT);
    }

    public Mono<Integer> countHistories() {
        return messageHistoryOps.keys("*").collectList()
            .map(List::size)
            .timeout(TIMEOUT);
    }
}
