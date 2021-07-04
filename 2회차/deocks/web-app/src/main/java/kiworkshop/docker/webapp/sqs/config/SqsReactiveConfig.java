package kiworkshop.docker.webapp.sqs.config;

import kiworkshop.docker.webapp.sqs.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Slf4j
@Configuration
@EnableConfigurationProperties(SqsProperties.class)
@RequiredArgsConstructor
public class SqsReactiveConfig {
    public static final String DEOCKS_MESSAGE_LISTENER = "deocks-listener";

    private final SqsProperties sqsProperties;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
            .endpointOverride(URI.create(sqsProperties.getEndpoint()))
            .region(Region.AP_NORTHEAST_2)
            .build();
    }

    @Bean(DEOCKS_MESSAGE_LISTENER)
    public SqsListener messageListener(SqsAsyncClient sqsAsyncClient) {
        try {
            return new SqsListener(sqsAsyncClient);
        } catch (Exception e) {
            log.error("MessageListener creation exception : ", e);
            return null;
        }
    }
}
