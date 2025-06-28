package in.vishal.foodiesapi.controller;

import in.vishal.foodiesapi.io.UserRequest;
import in.vishal.foodiesapi.io.UserResponse;
import in.vishal.foodiesapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest userRequest) {

      return  userService.registerUser(userRequest);

    }
}
