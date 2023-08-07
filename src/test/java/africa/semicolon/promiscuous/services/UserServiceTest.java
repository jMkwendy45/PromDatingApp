package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Autowired
private  UserService userService;
@Test
    public  void testThatUserCanRegister(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("jjj");
        registerUserRequest.setPassword("12333") ;


        RegisterUserResponse registerUserResponse =  userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }






}
