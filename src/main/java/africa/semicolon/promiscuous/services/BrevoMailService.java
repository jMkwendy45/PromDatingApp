package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.request.EmailNotificationRequest;
import africa.semicolon.promiscuous.dto.reponse.EmailNotificationResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BrevoMailService  implements MailService{
    @Override
    public EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest) {
       String brevoMailAddress = "https://api.brevo.com/v3/smtp/email";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders  headers = new HttpHeaders();


        headers.set("api-key","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");


        HttpEntity<EmailNotificationRequest>request =
                new HttpEntity<>(emailNotificationRequest,headers);
        ResponseEntity<EmailNotificationResponse>response=
                restTemplate.postForEntity(brevoMailAddress,request,EmailNotificationResponse.class);
        EmailNotificationResponse emailNotificationResponse=response.getBody();
        return emailNotificationResponse;
    }
}
