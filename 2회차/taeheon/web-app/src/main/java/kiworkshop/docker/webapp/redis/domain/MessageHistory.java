package kiworkshop.docker.webapp.redis.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageHistory {
    private String id;
    private String body;

    public MessageHistory(String id, String body) {
        this.id = id;
        this.body = body;
    }
}
