package com.example.demoportal.controller;

import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/{boardId}")
    public ApiResponse<Boolean> addLike(@PathVariable Long boardId, Authentication authentication) {
        return ApiResponse.successResponse(likeService.addLike(authentication,boardId));
    }

}
