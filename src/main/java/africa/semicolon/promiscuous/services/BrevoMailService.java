package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.request.EmailNotificationRequest;
import africa.semicolon.promiscuous.dto.reponse.EmailNotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class BrevoMailService  implements MailService{
    @Override
    public EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest) {

        return null;
    }
}
