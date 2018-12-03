package devlight.chatbot.controller;

import devlight.chatbot.exception.AppException;
import devlight.chatbot.exception.ResourceNotFoundException;
import devlight.chatbot.model.Role;
import devlight.chatbot.model.RoleName;
import devlight.chatbot.model.User;
import devlight.chatbot.model.dto.UpdateProfile;
import devlight.chatbot.model.dto.UserProfile;
import devlight.chatbot.model.payload.ApiResponse;
import devlight.chatbot.model.payload.JwtAuthenticationResponse;
import devlight.chatbot.model.payload.LoginRequest;
import devlight.chatbot.model.payload.SignUpRequest;
import devlight.chatbot.repository.RoleRepository;
import devlight.chatbot.repository.UserRepository;
import devlight.chatbot.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;


@RestController
@RequestMapping("/api/adminapi/v1")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("admins/{username}")
    public UserProfile getAdminProfile(@PathVariable(value = "username") String username,
                                       @RequestHeader("Authorization") String header
    ) {
        String usr = tokenProvider.getUsernameFromJWT(header.split(" ")[1]);
        User user = null;

        if(username.equals("myself")){
            user = userRepository.findOneByUsername(usr)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        } else{
            user = userRepository.findOneByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        }

        String roleFromJwt = tokenProvider.getRoleFromJWT(header.split(" ")[1]);


        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getEmail());

        if(roleFromJwt.equals("ROLE_ADMIN")){
            return userProfile;
        }
        return null;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.CONFLICT);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.CONFLICT);
        }

        // Creating user's account
        User admin = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("Admin Role not set."));

        admin.setRoles(Collections.singleton(adminRole));

        User result = userRepository.save(admin);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/adminapi/v1/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Admin registered successfully"));
    }
    @DeleteMapping("/admins/{username}")
    public ResponseEntity deleteUser(@PathVariable(value = "username") String username) {

        Optional<User> users  = userRepository.findOneByUsername(username);
        userRepository.delete(users.get());

        return new ResponseEntity(username, HttpStatus.OK);
    }

    @PatchMapping("/admins/{username}")
    public ResponseEntity updateAccount(@PathVariable(value = "username") String username,
                                        @RequestBody UpdateProfile updateProfile){

        Optional<User> users  = userRepository.findOneByUsername(username);

        users.get().setUsername(updateProfile.getUsername());
        users.get().setPassword(updateProfile.getPassword());
        users.get().setName(updateProfile.getName());

        userRepository.save(users.get());

        return new ResponseEntity(username, HttpStatus.OK);
    }
}
