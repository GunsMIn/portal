package com.example.demoportal.controller;


import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.entity.dto.CommentRequest;
import com.example.demoportal.entity.dto.CommentResponse;
import com.example.demoportal.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/new")
    private ApiResponse<Boolean> createComment(@RequestBody CommentRequest commentRequest, @PathVariable Long boardId, Authentication authentication) {
        return ApiResponse.successResponse(commentService.createComment(commentRequest, boardId, authentication));
    }
}
