package com.example.demoportal.repository;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Likes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndBoard(User user, Board board);

}
