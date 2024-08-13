package com.example.demoportal.repository.querydsl;

import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    List<BoardUserDto> search(BoardSearchCondition condition);
    Page<BoardUserDto> searchPageSimple(BoardSearchCondition condition, Pageable pageable);
    Page<BoardUserDto> searchPageComplex(BoardSearchCondition condition, Pageable pageable);
}
