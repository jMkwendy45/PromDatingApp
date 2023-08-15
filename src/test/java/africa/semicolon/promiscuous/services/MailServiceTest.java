package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.request.EmailNotificationRequest;
import africa.semicolon.promiscuous.dto.reponse.EmailNotificationResponse;
import africa.semicolon.promiscuous.dto.request.Recipients;
import africa.semicolon.promiscuous.dto.request.Sender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MailServiceTest {
    @Autowired
    MailService mailService;
    @Test
    public void testThatEmailSendingWorks(){
        EmailNotificationRequest request = getEmailNotificationRequest();
        EmailNotificationResponse emailNotificationResponse = mailService.send(request);
      assertNotNull(emailNotificationResponse);
    }

    private static EmailNotificationRequest getEmailNotificationRequest() {
        String emailRecipients = "jobaw17207@touchend.com";
        String message ="testing our mail service";
        String subject="test email";

        Recipients recepitent = new Recipients();
        recepitent.setEmail(emailRecipients);
        List<Recipients>recipients = new ArrayList<>();
        recipients.add(recepitent);


//        Sender sender1 = new Sender();



        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setMailContents(message);
        request.setRecipients(recipients);
        request.setSubject(subject);

        return request;
    }
}
