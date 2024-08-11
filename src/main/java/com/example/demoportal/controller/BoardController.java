package com.example.demoportal.controller;

import com.example.demoportal.common.ApiResponse;
import com.example.demoportal.entity.dto.BoardDetailResponse;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.dto.BoardResponse;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boards")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/new")
    public ApiResponse<Boolean> newBoard(@RequestBody BoardRequest boardRequest, Authentication authentication) {
        return ApiResponse.successResponse(boardService.createBoard(boardRequest, authentication));
    }

    @GetMapping("/list")
    public ApiResponse<List<BoardResponse>> getBoards() {
        return ApiResponse.successResponse(boardService.getBoardList());
    }

    @GetMapping("/list/V2")
    public ApiResponse<List<Board>> getBoards2() {
        return ApiResponse.successResponse(boardService.getBoardListaLL());
    }

    @GetMapping("/{boardId}")
    public ApiResponse<BoardDetailResponse> getBoards(@PathVariable Long boardId) {
        return ApiResponse.successResponse(boardService.getBoardListByBoardId(boardId));
    }

}
