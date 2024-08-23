package com.example.demoportal.service;

import com.example.demoportal.entity.QUser;
import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.QBoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;
import com.example.demoportal.entity.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.demoportal.entity.QUser.*;
import static com.example.demoportal.entity.entity.QBoard.board;

@SpringBootTest
@Transactional
public class QuerydslAdvancedTest {

    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void test1() {
        BoardSearchCondition boardSearchCondition = new BoardSearchCondition();
        boardSearchCondition.setCategory("자유게시판");
        boardSearchCondition.setUserName("김민희");
        boardSearchCondition.setAgeGoe(10);
        boardSearchCondition.setAgeLoe(50);

        List<BoardUserDto> response = search(boardSearchCondition);

        System.out.println("response = " + response);
    }


    public List<BoardUserDto> search(BoardSearchCondition condition) {

        List<BoardUserDto> boardUserDtos = queryFactory
                .select(new QBoardUserDto(board.id, board.title, board.content, board.category, board.user.userName, board.user.userName, board.user.age))
                .from(board)
                .join(board.user ,user)
                .where(categoryEquals(condition.getCategory())
                        , userNameEquals(condition.getUserName())
                        , ageGoe(condition.getAgeGoe())
                        , ageLoe(condition.getAgeLoe()))
                .fetch();

        return boardUserDtos;
    }

    private BooleanExpression categoryEquals(String category) {
        return board != null ? board.category.eq(category) : null;
    }

    private BooleanExpression userNameEquals(String username) {
        return username != null ? user.userName.eq(username) : null;
    }

    private BooleanExpression ageGoe(Integer age) {
        return age != null ? user.age.goe(age) : null;
    }

    private BooleanExpression ageLoe(Integer age) {
        return age != null ? user.age.loe(age) : null;
    }


}
