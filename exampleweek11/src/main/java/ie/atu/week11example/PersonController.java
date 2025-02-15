package ie.atu.week11example;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/person")
@RestController
public class PersonController {
    private final PersonService personService;
    private final PaymentClient paymentClient;
    private final LoginClient loginClient;
    private final AuthClient authClient;

    public PersonController(PersonService personService, PaymentClient paymentClient, LoginClient loginClient, AuthClient authClient) {
        this.personService = personService;
        this.paymentClient = paymentClient;
        this.loginClient = loginClient;
        this.authClient = authClient;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getPerson(@PathVariable String employeeId) {
        if (employeeId.length() > 5 || employeeId.isBlank()) {
            return ResponseEntity.badRequest().body("EmployeeId is invalid");
        }

        Person person = personService.getPersonByEmployeeId(employeeId);

        if (person == null) {
            return ResponseEntity.notFound().build();
        }
        String details = authClient.makeLogin(person);
        System.out.println(details);

        return ResponseEntity.ok(person);
    }

    @PostMapping("/createPerson")
    public ResponseEntity<String>create(@Valid @RequestBody Person person) {
        personService.savePerson(person);
        String details = authClient.makeSignUp(person);
        System.out.println(details);

        return new ResponseEntity<>("Person created successfully", HttpStatus.OK);
    }
}
