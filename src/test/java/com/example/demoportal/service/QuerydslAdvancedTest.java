package com.example.demoportal.service;

import com.example.demoportal.entity.QUser;
import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.QBoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;
import com.example.demoportal.entity.entity.Board;
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
        boardSearchCondition.setId(15L);
        boardSearchCondition.setUserName("김민희");
        boardSearchCondition.setAgeGoe(10);
        boardSearchCondition.setAgeLoe(50);

        List<BoardUserDto> response = search(boardSearchCondition);

        System.out.println("response = " + response);
    }


    public List<BoardUserDto> search(BoardSearchCondition condition) {

        QBoard board = QBoard.board;
        QUser user = QUser.user;

        List<BoardUserDto> boardUserDtos = queryFactory
                .select(new QBoardUserDto(board.id, board.title, board.content, board.category, board.user.userName, board.user.userName, board.user.age))
                .from(board)
                .join(board.user ,user)
                .where(boardIdEquals(condition.getId())
                        , userNameEquals(condition.getUserName())
                        , ageGoe(condition.getAgeGoe())
                        , ageLoe(condition.getAgeLoe()))
                .fetch();

        return boardUserDtos;
    }

    private BooleanExpression boardIdEquals(Long boardId) {
        QBoard board = QBoard.board;
        return board != null ? board.id.eq(board.id) : null;
    }

    private BooleanExpression userNameEquals(String username) {
        QUser user = QUser.user;
        return username != null ? user.userName.eq(username) : null;
    }

    private BooleanExpression ageGoe(Integer age) {
        QUser user = QUser.user;
        return age != null ? user.age.goe(age) : null;
    }

    private BooleanExpression ageLoe(Integer age) {
        QUser user = QUser.user;
        return age != null ? user.age.loe(age) : null;
    }


}
