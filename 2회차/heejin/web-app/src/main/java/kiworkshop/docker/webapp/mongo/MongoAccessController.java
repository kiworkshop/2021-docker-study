package kiworkshop.docker.webapp.mongo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoAccessController {

    @GetMapping("/api/mongo/hi")
    public String hiMongo() {
        return "Hi, Mongo";
    }
}
