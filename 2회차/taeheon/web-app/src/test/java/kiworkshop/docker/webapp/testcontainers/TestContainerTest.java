package kiworkshop.docker.webapp.testcontainers;

import kiworkshop.docker.webapp.sqs.SqsListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Testcontainers
@SpringBootTest
public class TestContainerTest {

    @Autowired
    SqsListener messageListener;

    @Autowired
    SqsAsyncClient sqsAsyncClient;

    @Container
    LocalStackContainer container = new LocalStackContainer(DockerImageName.parse("localstack/localstack"))
        .withServices(LocalStackContainer.Service.SQS);

    @Test
    @DisplayName("Sqs 통합 테스트")
    void sqsTest() {
        String queueUrl = messageListener.findQueueUrl(sqsAsyncClient);
        System.out.println(queueUrl);
    }
}
