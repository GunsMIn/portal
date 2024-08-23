package com.example.demoportal.entity.entity;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "boards", timeToLive = 300)
@BatchSize(size = 100)
public class Board extends BaseTime {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Long id;

        @Column(length = 50, nullable = false)
        private String title;

        @Column(length = 500, nullable = false)
        private String content;

        @Column(length = 30, nullable = false)
        private String category;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @BatchSize(size = 100)
        @OneToMany(mappedBy = "board" , fetch = LAZY , cascade = REMOVE)
        private List<Comment> comments;

        @OneToMany(mappedBy = "board", cascade = REMOVE)
        private Set<Likes> likes = new HashSet<>();

        public static Board from(BoardRequest boardRequest, User user) {

                return Board.builder()
                        .title(boardRequest.getTitle())
                        .content(boardRequest.getContent())
                        .category(boardRequest.getCategory())
                        .user(user)
                        .build();
        }

        //연관관계 편의 메서드
        public void addComments(Comment comment) {
                this.comments.add(comment);
                comment.setBoardDirectly(this);
        }
    }
