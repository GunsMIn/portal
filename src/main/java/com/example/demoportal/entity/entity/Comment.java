package com.example.demoportal.entity.entity;

import com.example.demoportal.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "comments")
public class Comment extends BaseTime {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String comment; // 댓글내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Comment of(Board board, User user, String comment) {
        return Comment.builder()
                .board(board)
                .user(user)
                .comment(comment)
                .build();
    }
}
