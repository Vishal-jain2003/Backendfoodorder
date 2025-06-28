package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.entity.UserEntity;
import in.vishal.foodiesapi.io.UserRequest;
import in.vishal.foodiesapi.io.UserResponse;
import in.vishal.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final MailService mailService;


    @Override
    public UserResponse registerUser(UserRequest request) {
       UserEntity newUser =  convertToEntity(request);
       newUser = userRepository.save(newUser);
        mailService.sendWelcomeEmailToUser(newUser.getEmail(), newUser.getName());
         return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {
       String loggedInUserEmail =  authenticationFacade.getAuthentication().getName();
         UserEntity user = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
         return user.getId();



    }

    private UserEntity convertToEntity(UserRequest request) {
      return  UserEntity.builder()
               .email(request.getEmail())
               .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();
    }

    private UserResponse convertToResponse(UserEntity registeredUser) {
       return  UserResponse.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .build();
    }
}
