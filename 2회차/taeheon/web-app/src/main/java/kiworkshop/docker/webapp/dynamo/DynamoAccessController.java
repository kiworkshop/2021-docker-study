package kiworkshop.docker.webapp.dynamo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamoAccessController {

    @GetMapping("/api/dynamo/hi")
    public String hiDynamo() {
        return "Hi, Dynamo";
    }
}
