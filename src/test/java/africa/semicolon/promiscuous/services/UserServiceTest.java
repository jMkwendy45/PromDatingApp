package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.LoginRequest;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dto.request.UpdateRequest;
import africa.semicolon.promiscuous.exception.BadCredentialException;
import africa.semicolon.promiscuous.exception.PromiscuousException;
import ch.qos.logback.core.pattern.color.BlackCompositeConverter;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static africa.semicolon.promiscuous.utils.AppUtils.BLANK_SPACE;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void testTHatUsersCanLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password");


        LoginResponse response = userService.login(loginRequest);
        assertThat(response).isNotNull();

        String accessToken = response.getAccessToken();
        assertThat(accessToken).isNotNull();


    }

    @Test
    public void testThateExceptionIsThrown() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("bad_sword");


        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(BadCredentialException.class);


    }
//    @Test
//    public void testThatUserCanUpdateAccount(){
//        UpdateRequest updateUserRequest = buildUpdateRequest();
//
//            UpdateResponse response = userService.updateProfile(updateUserRequest, 500L);
//
//            assertThat(response).isNotNull();
//            GetUserResponse userResponse = userService.getUserId(500L);
//
//            String fullName = userResponse.getFullName();
//            String expectedFullName = new StringBuilder()
//                    .append(updateUserRequest.getFirstName())
//                    .append(BLANK_SPACE)
//                    .append(updateUserRequest.getLastName()).toString();
//
//            assertThat(fullName).isEqualTo(expectedFullName);
//
//    }
//
//    private UpdateRequest buildUpdateRequest() {
//        Set<String> interests = Set.of("Swimming", "Sports", "Cooking");
//        UpdateRequest updateUserRequest =  new UpdateRequest();
//        updateUserRequest.setDateOfBirth(String.valueOf(LocalDate.of(2005, Month.AUGUST.ordinal(), 7)));
//        updateUserRequest.setFirstName("Sheriff");
//        updateUserRequest.setLastName("Awofiranye");
//        MultipartFile testImage = getTestImage();
//        updateUserRequest.setProfileImage(testImage);
//        updateUserRequest.setInterest(interests);
//        return updateUserRequest;
//    }


//        private MultipartFile getTestImage(){
//            //obtain a path that points to the image
//            Path path = Paths.get("C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\airplane_cartoon.png");
//            //create stream that can read bytes from file pointed to by path
//            try (var inputStream = Files.newInputStream(path)){
//                //create a multipartFile using bytes from the inputStream obtained from the path
//                MultipartFile image = new MockMultipartFile("test_image",inputStream);
//                return image;
//            } catch (Exception exception){
//                throw new PromiscuousException(exception.getMessage());
//            }
//        }






//    private MultipartFile getTestImage(){
//        //obtain a path that points to the image
//        Path path = Paths.get("C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\airplane_cartoon.png");
//        //create stream that can read bytes from file pointed to by path
//        try (var inputStream = Files.newInputStream(path)){
//            //create a multipartFile using bytes from the inputStream obtained from the path
//            MultipartFile image = new MockMultipartFile("test_image",inputStream);
//            return image;
//        } catch (Exception exception){
//            throw new PromiscuousException(exception.getMessage());
//        }
//    }

//    @Test
//    public void testThatUserCanUpdatePassword() {
//        Set<String>interests = Set.of("Swimming","Sports","Cooking");
//
//
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.setDateOfBirth(String.valueOf(LocalDate.of(2005, Month.AUGUST.ordinal(), 7)));
//        updateRequest.setFirstName("shefiff");
//        updateRequest.setLastName("Awof");
//        updateRequest.setId(500L);
//      MultipartFile testImage=  getTestImage();
//        updateRequest.setProfileImage(testImage);
//        updateRequest.setInterest(interests);
//      UpdateResponse response = userService.updateProfile(updateRequest,);
//      assertThat(response).isNotNull();
//
//      GetUserResponse userResponse = userService.getUserId(500L);
//
//      String fullName = userResponse.getFullName();
//      String expectedFullName = new StringBuilder().append(updateRequest.getFirstName())
//                      .append(BLANK_SPACE)
//                              .append(updateRequest.getLastName())
//                                      .toString();
//
//      assertThat(fullName);
//
//      assertThat(fullName).isEqualTo(expectedFullName);
//
//    }
//
//    private MultipartFile getTestImage() {
//        //obatin a part that points to
//        Path path = Paths.get("C:\\Users\\USER\\IdeaProjects\\SpringProjects\\promiscuous\\src\\test\\java\\africa\\semicolon\\promiscuous\\resources\\images\\2.jpg");
//        //create streams that caan read from files pointed to by path
//        try (InputStream inputStream = Files.newInputStream(path)) {
//            //create a multipartfile using bytes from file pointed to by path
//            MultipartFile image = new MockMultipartFile("test_image", inputStream);
//            return image;
//        } catch (Exception exception) {
//            throw new PromiscuousException(exception.getMessage());
//        }
//
//    }
@Test
public void testThatUserCanUpdateAccount(){
    UpdateUserRequest updateUserRequest = buildUpdateRequest();

    UpdateUserResponse response = userService.updateProfile(updateUserRequest, 500L);

    assertThat(response).isNotNull();
    GetUserResponse userResponse = userService.getUserById(500L);

    String fullName = userResponse.getFullName();
    String expectedFullName = new StringBuilder()
            .append(updateUserRequest.getFirstName())
            .append(BLANK_SPACE)
            .append(updateUserRequest.getLastName()).toString();

    assertThat(fullName).isEqualTo(expectedFullName);
}

    private UpdateUserRequest buildUpdateRequest() {
        Set<String> interests = Set.of("Swimming", "Sports", "Cooking");
//        Set<Interest> interests = Set.of(Interest.SWIMMING,Interest.COOKING,Interest.SPORTS);
        UpdateUserRequest updateUserRequest =  new UpdateUserRequest();
        updateUserRequest.setDateOfBirth(LocalDate.of(2005, Month.NOVEMBER.ordinal(),25));
        updateUserRequest.setFirstName("Sheriff");
        updateUserRequest.setLastName("Awofiranye");
        updateUserRequest.setPassword("newPassword");
        updateUserRequest.setCountry("Nigeria");
        updateUserRequest.setInterests(interests);

        //TODO: FIX THIS MESS
//        MultipartFile testImage = getTestImage();
//        updateUserRequest.setProfileImage(testImage);
//        updateUserRequest.setInterests(interests);
        return updateUserRequest;
    }

    private MultipartFile getTestImage(){
        //obtain a path that points to the image
        Path path = Paths.get("C:\\Users\\USER\\Desktop\\SPRINGBOOT\\promiscuous\\src\\test\\resources\\images\\airplane_cartoon.png");
        //create stream that can read bytes from file pointed to by path
        try (var inputStream = Files.newInputStream(path)){
            //create a multipartFile using bytes from the inputStream obtained from the path
            MultipartFile image = new MockMultipartFile("test_image",inputStream);
            return image;
        } catch (Exception exception){
            throw new PromiscuousBaseException(exception.getMessage());
        }
    }

}