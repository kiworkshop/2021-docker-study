package kiworkshop.docker.webapp.sqs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.*;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class SqsListener {
    private static final String QUEUE_NAME = "deocks-queue";

    private final SqsAsyncClient sqsAsyncClient;

    @Getter
    private final String queueUrl;

    public SqsListener(SqsAsyncClient sqsAsyncClient) {
        this.sqsAsyncClient = sqsAsyncClient;
        this.queueUrl = findQueueUrl(sqsAsyncClient);
    }

    private String findQueueUrl(SqsAsyncClient sqsAsyncClient) {
        GetQueueUrlRequest request = GetQueueUrlRequest.builder()
            .queueName(QUEUE_NAME)
            .build();
        CompletableFuture<GetQueueUrlResponse> response = sqsAsyncClient.getQueueUrl(request);

        return Mono.fromFuture(response)
            .blockOptional()
            .orElseThrow(() -> new RuntimeException("Failed to fetch queue name"))
            .queueUrl();
    }

    @PostConstruct
    public void continuousListener() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
            .maxNumberOfMessages(5)
            .queueUrl(queueUrl)
            .waitTimeSeconds(10)
            .visibilityTimeout(30)
            .build();

        Mono.fromFuture(() -> sqsAsyncClient.receiveMessage(request))
            .repeat()
            .retry()
            .map(ReceiveMessageResponse::messages)
            .flatMap(Flux::fromIterable)
            .subscribe(message -> {
                log.info("message body: {}", message.body());
                delete(message);
            });
    }

    private void delete(Message message) {
        String receiptHandle = message.receiptHandle();
        DeleteMessageRequest request = DeleteMessageRequest.builder()
            .queueUrl(queueUrl)
            .receiptHandle(receiptHandle)
            .build();

        Mono.fromFuture(sqsAsyncClient.deleteMessage(request))
            .subscribe(response -> log.info("deleted message with handle : {}", receiptHandle));
    }
}
