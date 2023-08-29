package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.LoginRequest;
import africa.semicolon.promiscuous.dto.request.MediaReactionRequest;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dto.request.UpdateRequest;
import africa.semicolon.promiscuous.exception.UserNotFoundException;
import africa.semicolon.promiscuous.model.User;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);
    ApiResponse<?> activateUserAccount(String token);

    User findUserById(Long id);

    GetUserResponse getUserById(Long id) throws UserNotFoundException;

    List<GetUserResponse> getAllUsers(int page, int pageSize);

    UpdateResponse updateProfile(UpdateRequest updateUserRequest, Long id) throws JsonPatchException, JsonPatchException;

    UploadMediaResponse uploadMedia(MultipartFile mediaToUpload);

    UploadMediaResponse uploadProfilePicture(MultipartFile mediaToUpload);

//    ApiResponse<?> reactToMedia(MediaReactionRequest mediaReactionRequest);

    User getUserByEmail(String email);



}
