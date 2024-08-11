package com.example.demoportal.repository;

import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Comment> findAllByBoard(Board board);

}
