package com.example.demoportal.service;

import com.example.demoportal.entity.dto.LoginRequestDto;
import com.example.demoportal.entity.dto.TokenInfoDto;

public interface AuthService {
    String login(LoginRequestDto dto);
}
