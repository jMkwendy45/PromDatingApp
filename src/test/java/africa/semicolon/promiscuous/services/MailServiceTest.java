package africa.semicolon.promiscuous.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;



public class MailServiceTest {
    @Autowired
    MailService mailService;
    @Test
    public void testThatEmailSendingWorks(){
        String email = "xiteha3228@inkiny.com";
    }
}
