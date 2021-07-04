package kiworkshop.docker.webapp.sqs;

import kiworkshop.docker.webapp.sqs.config.SqsReactiveConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SqsListenerHealthCheckController {
    private final ApplicationContext applicationContext;

    @GetMapping("api/sqs/health")
    public String health() {
        try {
            SqsListener listener = (SqsListener) applicationContext.getBean(SqsReactiveConfig.DEOCKS_MESSAGE_LISTENER);
            log.info("SQS listener: {}", listener.getQueueUrl());
            return "SQS Listener is listening " + listener.getQueueUrl();
        } catch (Exception e) {
            log.error("Health check controller exception : ", e);
            return "No connected listener";
        }
    }

}
