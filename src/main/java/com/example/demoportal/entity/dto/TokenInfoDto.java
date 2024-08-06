package com.example.demoportal.entity.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDto {

    /**
     * 토큰 타입
     */
    @NotNull
    private String grantType;

    /**
     * Access Token
     */
    @NotNull
    private String accessToken;

}