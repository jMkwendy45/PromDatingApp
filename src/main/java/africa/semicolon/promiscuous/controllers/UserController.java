package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dto.reponse.GetUserResponse;
import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.reponse.UpdateResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.services.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<RegisterUserResponse>register(@RequestBody RegisterUserRequest registerUserRequest){
        RegisterUserResponse response = userService.register(registerUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{id}")
    public  ResponseEntity<GetUserResponse>getUserById(@PathVariable Long id){
        GetUserResponse user =userService.getUserId(id);
                return ResponseEntity.ok().body(user);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?>updateUserAccount(@RequestBody JsonPatch jsonPatch,@PathVariable Long id){
        UpdateResponse response = userService.updateUserProfile(jsonPatch,id);
                return ResponseEntity.ok(response);
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