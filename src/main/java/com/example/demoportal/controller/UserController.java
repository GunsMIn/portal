package com.example.demoportal.controller;

import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.UserJoinRequest;
import com.example.demoportal.entity.dto.UserJoinResponse;
import com.example.demoportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/new")
    public ApiResponse<UserJoinResponse> createUser(@RequestBody UserJoinRequest userJoinRequest) {
        UserJoinResponse userJoinResponse = userService.saveUser(User.from(userJoinRequest, passwordEncoder));
        return ApiResponse.successResponse(userJoinResponse);
    }

}
