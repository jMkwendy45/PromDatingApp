package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.ActivateAccountResponse;
import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import africa.semicolon.promiscuous.dto.reponse.GetUserResponse;
import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    private RegisterUserRequest registerUserRequest;
    private RegisterUserResponse registerUserResponse;

    @BeforeEach
    void setUp() {
        registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("xogov39629@vreaa.com");
        registerUserRequest.setPassword("password");
    }


    @Test
    public void testThatUserCanRegister() {
        registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }

    @Test
    public void testActivateUserAccount() {
        registerUserRequest.setEmail("xogov39629@vreaa.com");
        registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);

        ApiResponse<?> activateUserAccountResponse = userService.activateUserAccount("abc1234.erytuuoi.67t75646");
        assertThat(activateUserAccountResponse).isNotNull();
    }
    @Test
    public void getUserByIdTest() {
        userService.register(registerUserRequest);
        GetUserResponse response = userService.getUserId(1L);
        assertThat(response).isNotNull();
        assertThat(response.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }
    @Test
    public void getAllUsers() {
        registerTestUsers();
        List<GetUserResponse> users = userService.getAllUsers(1, 5);
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(5);
    }
    private void registerTestUsers() {
        RegisterUserRequest firstRequest = new RegisterUserRequest();
        firstRequest.setEmail("john@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);

        firstRequest.setEmail("jane@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);

        firstRequest.setEmail("jerry@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);

        firstRequest.setEmail("johnny@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);

        firstRequest.setEmail("jeoy@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);

        firstRequest.setEmail("zaza@gmail.com");
        firstRequest.setPassword("password");
        userService.register(firstRequest);


    }
}