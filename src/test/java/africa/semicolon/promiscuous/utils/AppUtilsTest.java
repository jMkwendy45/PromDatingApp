package africa.semicolon.promiscuous.utils;

import africa.semicolon.promiscuous.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.promiscuous.utils.AppUtils.generateActivationLink;
import static africa.semicolon.promiscuous.utils.JwtUtils.generateToken;
import static org.assertj.core.api.Assertions.assertThat;
@Slf4j

class AppUtilsTest {
    private final AppConfig appConfig = new AppConfig();
    @Test
    public void testGenerateActivationLink(){
        String activationLink =  generateActivationLink("localhost:8080","test@gmail.com");
        log.info("activation link -> {} ", activationLink);
        assertThat(activationLink).isNotNull();
        assertThat(activationLink).contains("localhost:8080/user/activate");
    }
    @Test
    public void generateTokenTest(){
        String email = "test@gmail.com";
        String token = JwtUtils.generateToken(email);
        log.info("generated token ->{}",token);
        assertThat(token).isNotNull();
    }



    }


