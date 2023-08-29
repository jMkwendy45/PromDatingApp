package africa.semicolon.promiscuous.services;

import africa.semicolon.promiscuous.config.AppConfig;
import africa.semicolon.promiscuous.dto.reponse.*;
import africa.semicolon.promiscuous.dto.request.*;
import africa.semicolon.promiscuous.enums.ExceptionMessage;
import africa.semicolon.promiscuous.enums.Interest;
import africa.semicolon.promiscuous.exception.AccountActivationFailedException;
import africa.semicolon.promiscuous.exception.BadCredentialException;
import africa.semicolon.promiscuous.exception.PromiscuousException;
import africa.semicolon.promiscuous.exception.UserNotFoundException;
import africa.semicolon.promiscuous.model.Address;
import africa.semicolon.promiscuous.model.User;
import africa.semicolon.promiscuous.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static africa.semicolon.promiscuous.dto.reponse.ResponseMessage.*;
import static africa.semicolon.promiscuous.enums.ExceptionMessage.*;
import static africa.semicolon.promiscuous.model.Role.CUSTOMER;
import static africa.semicolon.promiscuous.utils.AppUtils.*;
import static africa.semicolon.promiscuous.utils.JwtUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromiscusUserService implements UserService{
     private  final MailService mailService;
    private  final UserRepository userRepository;
      private final AppConfig appConfig;
    private final CloudService cloudService;
    private final MediaService mediaService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        String email = registerUserRequest.getEmail();
        String password = registerUserRequest.getPassword();
        String passwordHash = passwordEncoder.encode(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordHash);
        user.setAddress(new Address());
        user.setRole(CUSTOMER);
        User savedUser = userRepository.save(user);
        EmailNotificationRequest request = buildEmailRequest(savedUser);
        mailService.send(request);
        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
        registerUserResponse.setMessage(USER_REGISTRATION_SUCCESSFUL.name());
        return registerUserResponse;
    }




    @Override
    public ApiResponse<?> activateUserAccount(String token) {
        boolean isTestToken = token.equals(appConfig.getTestToken());
        if (isTestToken) return activateTestAccount();
        boolean isValidJwt = isValidJwt(token);
        if (isValidJwt) return activateAccount(token);
        throw new AccountActivationFailedException(
                ACCOUNT_ACTIVATION_FAILED_EXCEPTION.getMessage());
    }

    @Override
    public GetUserResponse getUserById(Long id) throws UserNotFoundException{
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(
                ()->new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage())
        );
        GetUserResponse getUserResponse = buildUserResponse(user);
        return getUserResponse;
    }

    @Override
    public List<GetUserResponse> getAllUsers(int page, int pageSize) {
        Pageable pageable = buildPageRequest(page, pageSize);
        Page<User> usersPage = userRepository.findAll(pageable);
        List<User> foundUsers = usersPage.getContent();
        return foundUsers.stream()
                .map(user-> buildUserResponse(user))
                .toList();
    }



    @Override
    public UpdateResponse updateProfile(UpdateRequest updateUserRequest, Long id) throws JsonPatchException {
        ModelMapper modelMapper = new ModelMapper();
        String url = uploadImage(updateUserRequest.getProfileImage());

        User user = findUserById(id);

        Set<String> userInterests = updateUserRequest.getInterests();
        Set<Interest> interests = parseInterestsFrom(userInterests);
        user.setInterests(interests);


        Address userAddress = user.getAddress();
        modelMapper.map(updateUserRequest, userAddress);
        user.setAddress(userAddress);
        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
        return applyPatch(updatePatch, user);
    }

    @Override
    public UploadMediaResponse uploadMedia(MultipartFile mediaToUpload) {
        return mediaService.uploadMedia(mediaToUpload);
    }

    @Override
    public UploadMediaResponse uploadProfilePicture(MultipartFile mediaToUpload) {
        return mediaService.uploadProfilePicture(mediaToUpload);
    }

//    @Override
//    public ApiResponse<?> reactToMedia(MediaReactionRequest mediaReactionRequest) {
//        String response = mediaService.(mediaReactionRequest);
//        return ApiResponse.builder().data(response).build();
//    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.readByEmail(email).orElseThrow(
                ()->new UserNotFoundException(
                        String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)
                )
        );
    }

    private String uploadImage(MultipartFile profileImage) {
        boolean isFormWithProfileImage = profileImage !=null;
        if (isFormWithProfileImage) return cloudService.upload(profileImage);
        throw new RuntimeException("image upload failed");
    }

    private static Set<Interest> parseInterestsFrom(Set<String> interests){
        Set<Interest> userInterests =interests.stream()
                .map(interest->Interest.valueOf(interest.toUpperCase()))
                .collect(Collectors.toSet());
        return userInterests;
    }

    private UpdateResponse applyPatch(JsonPatch updatePatch, User user) throws JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        //1. Convert user to JsonNode
        JsonNode userNode = objectMapper.convertValue(user, JsonNode.class);

        //2. Apply patch to JsonNode from step 1
        JsonNode updatedNode = updatePatch.apply(userNode);
        //3. Convert updatedNode to user
        user = objectMapper.convertValue(updatedNode, User.class);
        log.info("user-->{}", user);
        //4. Save updatedUser from step 3 in the DB
        var savedUser=userRepository.save(user);
        log.info("user-->{}", savedUser);
        return new UpdateResponse(PROFILE_UPDATE_SUCCESSFUL.name());

    }

    private JsonPatch buildUpdatePatch(UpdateRequest updateUserRequest) {
        Field[] fields = updateUserRequest.getClass().getDeclaredFields();
        List<ReplaceOperation> operations = Arrays.stream(fields)
                .filter(field ->  validateField(updateUserRequest, field))
                .map(field->buildReplaceOperation(updateUserRequest, field))
                .toList();
        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
        return new JsonPatch(patchOperations);
    }

    private static boolean validateField(UpdateRequest updateUserRequest, Field field) {
        List<String> list = List.of("interests", "street", "houseNumber", "country", "state", "gender", "profileImage");
        field.setAccessible(true);
        try {
            return field.get(updateUserRequest)!=null &&!list.contains(field.getName());
        } catch (IllegalAccessException e) {
            throw new PromiscuousException(e.getMessage());
        }
    }

    private static ReplaceOperation buildReplaceOperation(UpdateRequest updateUserRequest, Field field) {
        field.setAccessible(true);
        try {
            String path = JSON_PATCH_PATH_PREFIX+field.getName();
            JsonPointer pointer = new JsonPointer(path);
            var value = field.get(updateUserRequest);
            TextNode node = new TextNode(value.toString());
            return new ReplaceOperation(pointer, node);
        } catch (Exception exception) {
            throw new PromiscuousException(exception.getMessage());
        }
    }

    public User findUserById(Long id){
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        return user;
    }


    private Pageable buildPageRequest(int page, int pageSize) {
        if (page<1&&pageSize<1)return PageRequest.of(0, 10);
        if (page<1)return PageRequest.of(0, pageSize);
        if (pageSize<1) return PageRequest.of(page, pageSize);
        return PageRequest.of(page-1, pageSize);
    }


    private ApiResponse<?> activateAccount(String token) {
        String email = extractEmailFrom(token);
        Optional<User> user = userRepository.readByEmail(email);
        User foundUser = user.orElseThrow(()->new UserNotFoundException(
                String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(), email)
        ));
        foundUser.setActive(true);
        User savedUser = userRepository.save(foundUser);
        GetUserResponse userResponse = buildUserResponse(savedUser);
        var activateUserResponse = buildActivateUserResponse(userResponse);
        return ApiResponse.builder().data(activateUserResponse).build();
    }

    private static ActivateAccountResponse buildActivateUserResponse(GetUserResponse userResponse) {
        return ActivateAccountResponse.builder()
                .message(ACCOUNT_ACTIVATION_SUCCESSFUL.name())
                .user(userResponse)
                .build();
    }

    private static GetUserResponse buildUserResponse(User savedUser) {
        return GetUserResponse.builder()
                .id(savedUser.getId())
                .address(savedUser.getAddress().toString())
                .fullName(getFullName(savedUser))
                .phoneNumber(savedUser.getPhoneNumber())
                .email(savedUser.getEmail())
                .build();
    }

    private static String getFullName(User savedUser) {
        return savedUser.getFirstName() + BLANK_SPACE + savedUser.getLastName();
    }

    private static ApiResponse<?> activateTestAccount() {
        return ApiResponse.builder()
                .build();
    }


    private EmailNotificationRequest buildEmailRequest(User savedUser){
        EmailNotificationRequest request =new EmailNotificationRequest();
        List<Recipients> recipients = new ArrayList<>();
        Recipients recipient = new Recipients(savedUser.getEmail());
        recipients.add(recipient);
        request.setRecipients(recipients);
        request.setSubject(WELCOME_MAIL_SUBJECT);
        String activationLink =
                generateActivationLink(appConfig.getBaseUrl(), savedUser.getEmail());
        String emailTemplate = getMailTemplate();
        String mailContent = String.format(emailTemplate, activationLink);
        request.setMailContents(mailContent);
        return request;
    }


//    @Override
//    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
//        //1. extract registration details from the registration form(registerUserRequest)
//        String email = registerUserRequest.getEmail();
//        String password = registerUserRequest.getPassword();
//        //2. create a user profile with the registration details
//        User user = new User();
//        user.setEmail(email);
//
//        String passwordHash = passwordEncoder.encode(password);
//        user.setPassword(passwordHash);
//        user.setAddress(new Address());
//        user.setRole(CUSTOMER);
//        //3. save that users profile in the Database
//        User savedUser = userRepository.save(user);
//        log.info("saved guy-->{}", savedUser);
//        //4. send verification token to the users email
//        EmailNotificationRequest request = buildEmailRequest(savedUser);
//        mailService.send(request);
//        //5. return a response
//        RegisterUserResponse registerUserResponse = new RegisterUserResponse();
//        registerUserResponse.setMessage(ResponseMessage.USER_REGISTRATION_SUCCESSFUL.name());
//
//        return registerUserResponse;
//    }
//    @Override
//    public ApiResponse<?> activateUserAccount(String token) {
//        boolean isTestToken = token.equals(appConfig.getTestToken());
//        if (isTestToken ) return activateTestAccount();
//
//        boolean isValidJwt = validateToken(token);
//        if (isValidJwt) return activateAccount(token);
//
//        throw new AccountActivationFailedException(ExceptionMessage.ACCOUNT_ACTIVATION_FAILED_EXCEPTION.getMessage());
//    }
//
//    @Override
//    public GetUserResponse getUserId(Long id) {
//        Optional<User> foundUser = userRepository.findById(id);
//        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
//        GetUserResponse getUserResponse = buildGetUserResponse(user);
//        return getUserResponse;
//    }
//    @Override
//    public void deleteAll() {
//        userRepository.deleteAll();
//    }
//
////    @Override
////    public LoginResponse login(LoginRequest loginRequest) {
////        String email = loginRequest.getEmail();
////        String password =loginRequest.getPassword();
////
////
////        Optional<User>foundUser = userRepository.readByEmail(email);
////        User user = foundUser.orElseThrow(()->new UserNotFoundException(
////                String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(),email)
////        ));
////        boolean isValidPassword = matches(user.getPassword(),password);
////        if (isValidPassword) return buildLoginResponse(email);
////        throw  new BadCredentialException(INVALID_CREDENTIAL_EXCEPTION.getMessage());
////    }
//
////    private static LoginResponse buildLoginResponse(String email) {
////        String accessToken = generateToken(email);
////        LoginResponse loginResponse = new LoginResponse();
////        loginResponse.setAccessToken(accessToken);
////        return loginResponse;
////    }
//
//    @Override
//    public List<GetUserResponse> getAllUsers(int page, int pageSize) {
//        List<GetUserResponse> users = new ArrayList<>();
//
//        Pageable pageable = buildPageRequest(page, pageSize);
//        Page<User> usersPage = userRepository.findAll(pageable);
//        List<User> foundUsers = usersPage.getContent();
//
//        for(User user : foundUsers){
//            GetUserResponse getUserResponse = buildGetUserResponse(user);
//            users.add(getUserResponse);
//        }
//        return users;
//
//        //OR USE LAMBDA
////        return foundUsers.stream()
////                .map(PromiscuousUserService::buildGetUserResponse)
////                .map(buildGetUserResponse)
////                .toList();
//
//    }
//    @Override
//    public UpdateResponse updateProfile(UpdateRequest updateUserRequest, Long id) {
//
//
//        ModelMapper modelMapper = new ModelMapper();
//        String url =  uploadImage(updateUserRequest.getProfileImage());
//
//        User user = findUserById(id);
//
//        Set<String> userInterests = updateUserRequest.getInterests();
//        Set<Interest> interests = parseInterestFrom(userInterests);
//        user.setInterests(interests);
//
//        Address userAddress = user.getAddress();
//        modelMapper.map(updateUserRequest, userAddress);
//        user.setAddress(userAddress);
//        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
//        return applyPatch(updatePatch, user);
////        ModelMapper modelMapper = new ModelMapper();
////        String url =  uploadImage(updateUserRequest.getProfileImage());
////
////        User user = findUserById(id);
////
////        Set<String> userInterests = updateUserRequest.getInterests();
////        Set<Interest> interests = parseInterestFrom(userInterests);
////        user.setInterests(interests);
////
////        Address userAddress = user.getAddress();
////        modelMapper.map(updateUserRequest, userAddress);
////        user.setAddress(userAddress);
////        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
////        return applyPatch(updatePatch, user);
//    }
//
//    @Override
//    public User getUserByUserName(String email) {
//        return userRepository.readByEmail(email).orElseThrow(
//                ()->new UserNotFoundException(
//                        String.format(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(),email)
//                ));
//    }
//
//    private static Set<Interest>parseInterestFrom(Set<String> interests){
//        Set<Interest> userInterests =  interests.stream()
//                .map(interest -> Interest.valueOf(interest.toUpperCase()))
//                .collect(Collectors.toSet());
//
//        return userInterests;
//    }
//
//
//    private String uploadImage(MultipartFile newProfileImage) {
//        boolean isFormWithProfileImage = newProfileImage != null;
//        if(isFormWithProfileImage) return cloudService.upload(newProfileImage);
//        throw new RuntimeException("Image upload failed");
//    }
//    private UpdateResponse applyPatch(JsonPatch updatePatch,User user){
//        ObjectMapper objectMapper = new ObjectMapper();
//        //1. Convert user to JsonNode
//        JsonNode userNode = objectMapper.convertValue(user, JsonNode.class);
//        try {
//            //2. Apply patch to JsonNode from step 1
//            JsonNode updatedNode = updatePatch.apply(userNode);
//            //3. Convert updatedNode back to user
//            User updatedUser = objectMapper.convertValue(updatedNode, User.class);
//            //4. Save updated User
//            userRepository.save(updatedUser);
//            return  new UpdateResponse(PROFILE_UPDATE_SUCCESSFUL.name());
//        }catch (JsonPatchException exception){
//            throw new PromiscuousException(exception.getMessage());
//        }
//    }
////    @Override
////    public UpdateResponse updateUserProfile(JsonPatch jsonPatch, Long id) {
////        ObjectMapper mapper = new ObjectMapper();
////        User user = findUserById(id);
////        JsonNode node = mapper.convertValue(user, JsonNode.class);
////        try {
////            JsonNode updateNode = jsonPatch.apply(node);
////            User updateUser = mapper.convertValue(updateNode, User.class);
////            userRepository.save(updateUser);
////
////            UpdateResponse response = new UpdateResponse();
////            response.setMessage("update sucessfull");
////            return response;
////        } catch (JsonPatchException exception) {
////            throw new PromiscuousException(":(");
////        }
////    }
//private JsonPatch buildUpdatePatch(UpdateRequest updateUserRequest) {
//    Field[] fields = updateUserRequest.getClass().getDeclaredFields();
//
//    List<ReplaceOperation> operations = Arrays.stream(fields)
//            .filter(field -> isFieldWithValue(updateUserRequest, field))
//            .map(field->buildReplaceOperation(updateUserRequest, field))
//            .toList();
//
//    List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
//    return new JsonPatch(patchOperations);
//
//}
//    private static boolean validateFields(UpdateRequest updateUserRequest, Field field) {
//        List<String> list = List.of("interests","street","houseNumber","country","state");
//        field.setAccessible(true);
//        try {
//            return field.get(updateUserRequest) != null && !list.contains(field.getName());
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//    private static  ReplaceOperation buildReplaceOperation(UpdateRequest updateUserRequest, Field field) {
//        field.setAccessible(true);
//        try {
//            String path = JSON_PATCH_PATH_PREFIX + field.getName();
//            JsonPointer pointer = new JsonPointer(path);
//            String value = field.get(updateUserRequest).toString();
//            TextNode node = new TextNode(value);
//            ReplaceOperation operation = new ReplaceOperation(pointer, node);
//            return operation;
//        } catch (Exception exception) {
//            throw new RuntimeException(exception);
//        }
//    }
//
//    private static boolean isFieldWithValue(UpdateRequest updateRequest,Field field){
//    try {
//        field.setAccessible(true);
//        return field.get(updateRequest) != null;
//    } catch (IllegalAccessException e) {
//        throw new RuntimeException(e);
//    }
//}
////    }
//
//    private User findUserById( Long id) {
//        Optional<User> foundUser = userRepository.findById(id);
//        User user = foundUser.orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
//        return user;
//
//    }
//
//    private Pageable buildPageRequest(int page, int pageSize) {
//        if(page < 1 && pageSize < 1) return PageRequest.of(0, 10);
//        if(page < 1) return PageRequest.of(0, 10);
//        if(pageSize < 1) return PageRequest.of(page,pageSize);
//        return PageRequest.of(page - 1,pageSize);
//    }
//
////    @Override
////    public List<GetUserResponse> getAllUsers(int page, int pageSize) {
////        return null;
////    }
//
//    private ApiResponse<?> activateAccount(String token) {
//        String email = extractEmailFrom(token);
//        Optional<User> user = userRepository.readByEmail(email);
//        User foundUser = user.orElseThrow(() ->new UserNotFoundException(
//                String.format(ExceptionMessage.USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage(),email)));
//        foundUser.setActive(true);
//        User savedUser = userRepository.save(foundUser);
//        GetUserResponse userResponse = buildGetUserResponse(savedUser);
//        var activateUserResponse = buildActivateUserResponse(userResponse);
//        return ApiResponse .builder().data(activateUserResponse).build();
//    }
//
//
//    private static ActivateAccountResponse buildActivateUserResponse(GetUserResponse userResponse) {
//        return ActivateAccountResponse.builder()
//                .message(ResponseMessage.ACCOUNT_ACTIVATION_SUCCESSFUL.name())
//                .user(userResponse)
//                .build();
//    }
//
//    private static GetUserResponse buildGetUserResponse(User savedUser) {
//        return GetUserResponse.builder()
//                .id(savedUser.getId())
//                .address(savedUser.getAddress().toString())
//                .fullName(getFullName(savedUser))
//                .phoneNumber(savedUser.getPhoneNumber())
//                .email(savedUser.getEmail())
//                .build();
//    }
//
//    private static String getFullName(User savedUser) {
//        return savedUser.getFirstName() + BLANK_SPACE + savedUser.getLastName();
//    }
//
//    private static ApiResponse<?> activateTestAccount() {
//        ApiResponse<?> activateAccountResponse =
//                ApiResponse
//                        .builder()
//                        .build();
//        return activateAccountResponse;
//    }
//
//    private EmailNotificationRequest buildEmailRequest(User savedUser) {
//        EmailNotificationRequest request = new EmailNotificationRequest();
//        List<Recipients> recipients = new ArrayList<>();
//        Recipients recipient = new Recipients(savedUser.getEmail());
//        recipients.add(recipient);
//        request.setRecipients(recipients);
//        request.setSubject(WELCOME_MAIL_SUBJECT);
//        String activationLink = generateActivationLink(appConfig.getBaseUrl(), savedUser.getEmail());
//        String emailTemplate = getMailTemplate();
//
//        String mailContent = String.format(emailTemplate, activationLink);
//        request.setMailContents(mailContent);
//
//        return request;
//    }
}












