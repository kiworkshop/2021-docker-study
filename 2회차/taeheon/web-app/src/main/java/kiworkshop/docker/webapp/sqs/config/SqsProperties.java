package kiworkshop.docker.webapp.sqs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sqs")
public class SqsProperties {
    private String endpoint;
}
