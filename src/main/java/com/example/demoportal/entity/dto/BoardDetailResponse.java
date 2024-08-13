package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class BoardDetailResponse {

    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String writer;
    private List<CommentResponse> commentResponseList;

    public static BoardDetailResponse from(Board board) {

        List<CommentResponse> commentResponseList = board.getComments().stream()
                .map(CommentResponse::from)
                .collect(Collectors.toList());

        return BoardDetailResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .category(board.getCategory())
                .createdAt(board.getCreatedDate())
                .updatedAt(board.getModifiedDate())
                .writer(board.getUser().getNickname())
                .commentResponseList(commentResponseList).build();
    }

}
