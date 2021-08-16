package kiworkshop.docker.webapp.redis.response;

import lombok.Getter;

@Getter
public class RedisResponse<T> {
    private String status;
    private T data;

    public RedisResponse(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public static <T> RedisResponse<T> successWith(T data) {
        return new RedisResponse<>("SUCCESS", data);
    }

    public static <T> RedisResponse<T> error(T data) {
        return new RedisResponse<>("ERROR", data);
    }
}
