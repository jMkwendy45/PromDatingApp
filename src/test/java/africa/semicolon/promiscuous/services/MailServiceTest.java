package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.EmailNotificationRequest;
import africa.semicolon.promiscuous.dto.request.EmailNotificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    MailService mailService;
    @Test
    public void testThatEmailSendingWorks(){
        String email = "xiteha3228@inkiny.com";
        EmailNotificationRequest request = new EmailNotificationRequest();
      EmailNotificationResponse emailNotificationResponse = mailService.send(request);

      assertNotNull(emailNotificationResponse);
    }
}
