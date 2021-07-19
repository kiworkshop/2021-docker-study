package kiworkshop.docker.webapp.redis.config;

import kiworkshop.docker.webapp.redis.domain.MessageHistory;
import kiworkshop.docker.webapp.sqs.config.SqsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(RedisProperties.class)
@RequiredArgsConstructor
public class RedisReactiveConfig {

    private final RedisProperties redisProperties;

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
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
