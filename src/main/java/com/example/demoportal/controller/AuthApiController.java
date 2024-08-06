package com.example.demoportal.controller;

import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.entity.dto.LoginRequestDto;
import com.example.demoportal.entity.dto.TokenInfoDto;
import com.example.demoportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<String> login (@RequestBody LoginRequestDto loginRequestDto) {
        String login = authService.login(loginRequestDto);
        return ApiResponse.successResponse(login);
    }
}
