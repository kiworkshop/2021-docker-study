package kiworkshop.docker.webapp.redis;

import kiworkshop.docker.webapp.redis.domain.MessageHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 현재 Embedded Redis 주입받지 않은 상태라 테스트 정상적으로 수행되지 않음
 * Redis container를 실제로 띄우고 연결하여 수행하여야 동작
 * Command 확인용으로 사용
 */
@SpringBootTest
class RedisAccessServiceTest {
    @Autowired
    private RedisAccessService redisAccessService;

    @Test
    @DisplayName("MessageHistory를 저장한다.")
    void save() {
        MessageHistory messageHistory = new MessageHistory("id", "body");
        String result = redisAccessService.saveHistory(messageHistory).block();

        System.out.println(result);
    }

    @Test
    @DisplayName("MessageHistory를 조회한다.")
    void find() {
        MessageHistory history = redisAccessService.findHistory("id").block();

        System.out.println(history.getBody());
    }

    @Test
    @DisplayName("저장된 MessageHistory 수를 조회한다.")
    void count() {
        int count = redisAccessService.countHistories().block();

        System.out.println(count);
    }
}