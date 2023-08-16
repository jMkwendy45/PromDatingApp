package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.LoginRequest;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dto.request.UpdateRequest;
import africa.semicolon.promiscuous.model.User;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.List;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse<?> activateUserAccount(String token);
    GetUserResponse getUserId(Long id);


    void deleteAll();

   LoginResponse login(LoginRequest loginRequest );

    List<GetUserResponse> getAllUsers(int page, int pageSize);

    UpdateResponse updateProfile(UpdateRequest updateUserRequest, Long id);
   UpdateResponse updateUserProfile (JsonPatch jsonPatch, Long id);


}
