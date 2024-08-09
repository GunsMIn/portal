package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {

    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String writer;

    public static BoardResponse from(Board board) {
        BoardResponse boardResponse = BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .createdAt(board.getCreatedDate())
                .updatedAt(board.getModifiedDate())
                .writer(board.getUser().getNickname())
                .build();

        return boardResponse;
    }

}
