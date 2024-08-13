package com.example.demoportal.repository;

import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.repository.querydsl.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @EntityGraph(attributePaths = "user")
    List<Board> findAll();
}
