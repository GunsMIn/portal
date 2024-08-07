package com.example.demoportal.entity.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
public class ErrorResponseDto {

    private int value;
    private String message;
    private LocalDateTime date;
}
