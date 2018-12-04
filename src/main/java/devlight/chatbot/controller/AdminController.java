package devlight.chatbot.controller;

import devlight.chatbot.model.dto.UpdateProfile;
import devlight.chatbot.model.dto.UserProfile;
import devlight.chatbot.model.payload.LoginRequest;
import devlight.chatbot.model.payload.SignUpRequest;
import devlight.chatbot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/adminapi/v1")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("admins/{username}")
    public UserProfile getAdminProfile(@PathVariable(value = "username") String username,
                                       @RequestHeader("Authorization") String header
    ) {
        return adminService.getAdminProfile(username, header);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        return adminService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest)
    {
        return adminService.registerUser(signUpRequest);
    }
    @DeleteMapping("/admins/{username}")
    public ResponseEntity deleteUser(@PathVariable(value = "username") String username)
    {
        return adminService.deleteUser(username);
    }

    @PatchMapping("/admins/{username}")
    public ResponseEntity updateAccount(@PathVariable(value = "username") String username,
                                        @RequestBody UpdateProfile updateProfile)
    {
        return adminService.updateAccount(username, updateProfile);
    }
}
