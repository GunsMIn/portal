package com.example.demoportal.repository;

import com.example.demoportal.entity.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
