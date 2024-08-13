package com.example.demoportal.repository.querydsl;

import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;

import java.util.List;

public interface BoardRepositoryCustom {
    List<BoardUserDto> search(BoardSearchCondition condition);
}
