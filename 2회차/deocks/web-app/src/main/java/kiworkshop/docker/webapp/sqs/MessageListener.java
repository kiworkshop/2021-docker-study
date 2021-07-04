package kiworkshop.docker.webapp.sqs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

@Slf4j
public class MessageListener {
    private static final String QUEUE_NAME = "deocks-queue";

    private final SqsAsyncClient sqsAsyncClient;

    @Getter
    private final String queueUrl;

    public MessageListener(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
        this.queueUrl = getQueueUrl(sqsAsyncClient);
    }

    private String getQueueUrl(SqsAsyncClient sqsAsyncClient) {
        GetQueueUrlResponse response = Mono.fromFuture(
            sqsAsyncClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(QUEUE_NAME)
                .build())
        ).block();
        return response.queueUrl();
    }

}
