package in.vishal.foodiesapi.service;

import in.vishal.foodiesapi.io.UserRequest;
import in.vishal.foodiesapi.io.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findByUserId();
}
