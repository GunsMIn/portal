package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long commentId;
    private String comment;
    private String commentWriter;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {

        return CommentResponse
                .builder()
                .commentId(comment.getId())
                .comment(comment.getComment())
                .commentWriter(comment.getUser().getNickname())
                .createdAt(comment.getCreatedDate())
                .build();
    }
}
