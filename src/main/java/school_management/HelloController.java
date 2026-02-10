package school_management;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot in Docker!";
    }

    @GetMapping("/status")
    public String status() {
        return "Application is running successfully!";
    }
}
