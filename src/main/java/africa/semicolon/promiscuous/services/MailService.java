package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.EmailNotificationRequest;
import africa.semicolon.promiscuous.dto.request.EmailNotificationResponse;

public interface MailService {
    EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest);
}
