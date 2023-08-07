package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

}
