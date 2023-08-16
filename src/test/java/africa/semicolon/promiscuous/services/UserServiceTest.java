package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.LoginRequest;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql(scripts = {"/db/insert.sql"})
public class UserServiceTest {
    @Autowired
    private UserService userService;

//    private RegisterUserRequest registerUserRequest;
//    private  RegisterUserResponse registerUserResponse;


//    @BeforeEach
//    void setUp() {
//        registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setEmail("xogov39629@vreaa.com");
//        registerUserRequest.setPassword("password");
//    }


    @Test
    public void testThatUserCanRegister() {
         RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("xogov39629@vreaa.com");
        registerUserRequest.setPassword("password");
       var registerUserResponse = userService.register(registerUserRequest);
        assertNotNull(registerUserResponse);
        assertNotNull(registerUserResponse.getMessage());
    }

    @Test
    public void testActivateUserAccount() {
//        registerUserRequest.setEmail("xogov39629@vreaa.com");
//        registerUserResponse = userService.register(registerUserRequest);
//        assertNotNull(registerUserResponse);

        ApiResponse<?> activateUserAccountResponse = userService.activateUserAccount("abc1234.erytuuoi.67t75646");
        assertThat(activateUserAccountResponse).isNotNull();
    }
    @Test
    public void getUserByIdTest() {
//        userService.register(registerUserRequest);
        GetUserResponse response = userService.getUserId(501L);
        assertThat(response).isNotNull();
//        assertThat(response.getEmail()).isEqualTo(registerUserRequest.getEmail());
    }
    @Test
    public void getAllUsers() {
//        registerTestUsers();
        List<GetUserResponse> users = userService.getAllUsers(1, 5);
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(5);
    }
 @Test
 public void testTHatUsersCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password");


        LoginResponse response =userService.login(loginRequest);
        assertThat(response).isNotNull();

        String accessToken =response.getAccessToken();
        assertThat(accessToken).isNotNull();


 }

}