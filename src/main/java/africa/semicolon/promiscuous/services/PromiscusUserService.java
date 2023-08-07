package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.model.User;
import africa.semicolon.promiscuous.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromiscusUserService implements UserService{

    private  final UserRepository userRepository;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {

        String email = registerUserRequest.getEmail();
        String password  = registerUserRequest.getPassword();
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User savedUser =   userRepository.save(user);
        String email1Response  = MockEmailService.sendEmail(savedUser.getEmail());
         log.info("email sending respond->{}",email1Response);

         RegisterUserResponse registerUserResponse = new RegisterUserResponse();
         registerUserResponse.setMessage("Registration successful,check your email");
        return  registerUserResponse;
    }
}
