package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.RoleType;
import com.example.demoportal.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class CustomUserInfoDto {

    private Long userId;

    private String email;

    private String name;

    private String password;

    private RoleType role;

    public static CustomUserInfoDto createCustomUserInfoDto(User user) {
        return CustomUserInfoDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getUserName())
                .password(user.getPassword())
                .role(user.getRoleType())
                .build();
    }
}
