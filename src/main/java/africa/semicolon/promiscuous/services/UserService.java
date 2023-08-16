package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.LoginRequest;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.model.User;

import java.util.List;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse<?> activateUserAccount(String token);
    GetUserResponse getUserId(Long id);

    void deleteAll();

   LoginResponse login(LoginRequest loginRequest );

    List<GetUserResponse> getAllUsers(int page, int pageSize);




}
