package ie.atu.week11example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="auth-service", url = "http://localhost:8083")

public interface AuthClient {
    @PostMapping("/login")
    public String makeLogin(Person person);

    @PostMapping("/signup")
    public String makeSignUp(Person person);
}
