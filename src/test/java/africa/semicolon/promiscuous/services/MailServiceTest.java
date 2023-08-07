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
        String emailRecipients = "xiteha3228@inkiny.com";
        String message ="testing our mail service";
         String mailSender ="noreply@promisicus.com";
         String subject="test email";

        Recipients recepitent = new Recipients();
        recepitent.setRecipientEmail(emailRecipients);
        List<Recipients>recipients = new ArrayList<>();
        recipients.add(recepitent);


        Sender sender = new Sender();
        sender.setEmail(mailSender);


        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setSubject(subject);
        request.setSender(mailSender);
        request.setRecipients();
        request.setTextContents();
      EmailNotificationResponse emailNotificationResponse = mailService.send(request);

      assertNotNull(emailNotificationResponse);
    }
}
