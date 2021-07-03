package kiworkshop.docker.webapp.redis;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisAccessController {

    @GetMapping("/api/redis/hi")
    public String hiRedis() {
        return "Hi, redis";
    }
}
