package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardRequest {

    @NotBlank(message = "Title cannot be null or empty")
    @Size(max = 50, message = "Title must be less than or equal to 50 characters")
    private String title;

    @NotBlank(message = "Content cannot be null or empty")
    @Size(max = 500, message = "Content must be less than or equal to 500 characters")
    private String content;

    private String category;

    private String writer;

}
