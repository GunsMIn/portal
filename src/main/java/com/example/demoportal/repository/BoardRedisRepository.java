package com.example.demoportal.repository;

import com.example.demoportal.entity.entity.Board;
import org.springframework.data.repository.CrudRepository;

public interface BoardRedisRepository extends CrudRepository<Board, Long> {

}
