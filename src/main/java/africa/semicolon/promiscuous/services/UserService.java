package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.ActivateAccountResponse;
import africa.semicolon.promiscuous.dto.reponse.ApiResponse;
import africa.semicolon.promiscuous.dto.reponse.GetUserResponse;
import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.model.User;

import java.util.List;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse<?> activateUserAccount(String token);
    GetUserResponse getUserId(Long id);

    List<GetUserResponse> getAllUsers(int page, int pageSize);

}
