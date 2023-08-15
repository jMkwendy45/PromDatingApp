package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegisterUserResponse>register(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse response = userService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}


//public class UserController {
//
//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request){
//        RegisterUserResponse response = userService.register(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    @GetMapping("/findById")
//    public ResponseEntity<GetUserResponse> getUserById(@RequestBody FindUserRequest request){
//        long id = request.getId();
//        GetUserResponse response = userService.getUserById(id);
//        return ResponseEntity.status(HttpStatus.FOUND).body(response);
//    }
//    @GetMapping("/getAllUsers")
//    public ResponseEntity<List<GetUserResponse>> getAllUser(@RequestBody FindUserRequest request){
//        int page = request.getPage();
//        int pageSize = request.getPageSize();
//        List<GetUserResponse> response = userService.getAllUsers(page,pageSize);
//        return ResponseEntity.status(HttpStatus.FOUND).body(response);
//    }
//}