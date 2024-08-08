package com.example.demoportal.repository;

import com.example.demoportal.entity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
