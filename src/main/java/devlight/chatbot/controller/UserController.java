package devlight.chatbot.controller;

import devlight.chatbot.model.dto.UpdateProfile;
import devlight.chatbot.model.dto.UserProfile;
import devlight.chatbot.model.payload.LoginRequest;
import devlight.chatbot.model.payload.SignUpRequest;
import devlight.chatbot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usersapi/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username,
                                      @RequestHeader("Authorization") String header
    ) {
        return userService.getUserProfile(username, header);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest
    ) {
      return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.registerUser(signUpRequest);
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity deleteUser(@PathVariable(value = "username") String username
    ) {
        return userService.deleteUser(username);
    }

    @PatchMapping("/users/{username}")
    public ResponseEntity updateAccount(@PathVariable(value = "username") String username,
                                        @RequestBody UpdateProfile updateProfile
    ){
        return userService.updateAccount(username, updateProfile);
    }
}
