package com.example.demoportal.entity.entity;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

        public static Board from(BoardRequest boardRequest , User user) {
            return Board.builder()
                    .title(boardRequest.getTitle())
                    .content(boardRequest.getContent())
                    .category(boardRequest.getCategory())
                    .user(user)
                    .build();
        }
    }
