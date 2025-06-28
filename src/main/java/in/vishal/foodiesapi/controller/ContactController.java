package in.vishal.foodiesapi.controller;

// controller/ContactController.java


import in.vishal.foodiesapi.io.ContactRequest;
import in.vishal.foodiesapi.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ContactController {

    private final MailService mailService;

    @PostMapping("/contact")
    public ResponseEntity<String> handleContact(@RequestBody ContactRequest request) {
        String fullName = request.getFirstName() + " " + request.getLastName();
        mailService.sendMessageToAdmin(fullName, request.getEmail(), request.getMessage());
        return ResponseEntity.ok("Message sent successfully");
    }
}

