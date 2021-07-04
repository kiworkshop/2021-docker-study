package kiworkshop.docker.webapp.redis.config;

import kiworkshop.docker.webapp.redis.domain.MessageHistory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisReactiveConfig {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(standaloneConfiguration);
    }

    @Bean
    public ReactiveRedisOperations<String, MessageHistory> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<MessageHistory> serializer = new Jackson2JsonRedisSerializer<>(MessageHistory.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, MessageHistory> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, MessageHistory> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
