package in.vishal.foodiesapi.service;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    // 1. Send message from contact form to YOU
    public void sendMessageToAdmin(String fullName, String email, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("vmailai493@gmail.com");
        message.setSubject("📨 New Contact Form Message");
        message.setText(
                "Name: " + fullName + "\n" +
                        "Email: " + email + "\n\n" +
                        "Message:\n" + messageText
        );
        mailSender.send(message);
    }

    // 2. Send welcome email to user on registration
    public void sendWelcomeEmailToUser(String userEmail, String fullName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("🎉 Welcome to AI_Powered Food Ordering Website");
        message.setText("Hi " + fullName + ",\n\nThank you for registering at Foodies!\nWe're excited to serve you.\n\n🍽️ Happy Ordering!\n— Vishal Jain");
        mailSender.send(message);
    }
}

