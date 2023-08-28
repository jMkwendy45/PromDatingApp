package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dto.reponse.GetUserResponse;
import africa.semicolon.promiscuous.dto.reponse.RegisterUserResponse;
import africa.semicolon.promiscuous.dto.reponse.UpdateResponse;
import africa.semicolon.promiscuous.dto.request.RegisterUserRequest;
import africa.semicolon.promiscuous.dto.request.UpdateRequest;
import africa.semicolon.promiscuous.exception.UserNotFoundException;
import africa.semicolon.promiscuous.services.UserService;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController { ;

        private final UserService userService;

        @PostMapping
        public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest){
            RegisterUserResponse response = userService.register(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<GetUserResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
            GetUserResponse user = userService.getUserId(id);
            return ResponseEntity.ok().body(user);
        }

        @PutMapping("/{id}")
        public ResponseEntity<UpdateResponse> updateUserProfile(@ModelAttribute UpdateRequest updateUserRequest, @PathVariable Long id) throws JsonPatchException {
            UpdateResponse response = userService.updateProfile(updateUserRequest, id);
            return ResponseEntity.ok(response);
        }
//        @PostMapping("/uploadMedia")
//        public ResponseEntity<UploadMediaResponse> uploadMedia(@ModelAttribute UploadMediaRequest mediaRequest){
//            MultipartFile mediaToUpload = mediaRequest.getMedia();
//            UploadMediaResponse response = userService.uploadMedia(mediaToUpload);
//            return ResponseEntity.ok(response);
//        }
//        @PostMapping("/uploadProfilePicture")
//        public ResponseEntity<UploadMediaResponse> uploadProfilePicture(@ModelAttribute UploadMediaRequest mediaRequest){
//            MultipartFile mediaToUpload = mediaRequest.getMedia();
//            UploadMediaResponse response = userService.uploadProfilePicture(mediaToUpload);
//            return ResponseEntity.ok(response);
//        }
//        @PostMapping("/react/{id}")
//        public ResponseEntity<?> reactToMedia(@RequestBody MediaReactionRequest mediaReactionRequest){
//            ApiResponse<?> response = userService.reactToMedia(mediaReactionRequest);
//            return ResponseEntity.ok(response);
//        }

    }