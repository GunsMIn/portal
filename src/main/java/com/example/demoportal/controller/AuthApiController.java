package com.example.demoportal.controller;

import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.entity.dto.LoginRequestDto;
import com.example.demoportal.entity.dto.TokenInfoDto;
import com.example.demoportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<String> login (@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.successResponse(authService.login(loginRequestDto));
    }

    @GetMapping("/security")
    public ApiResponse<String> getSecurity (Authentication authentication) {
        String userEmail = authentication.getName();
        return ApiResponse.successResponse(userEmail);
    }
}
