package com.example.demoportal.repository.querydsl;

import com.example.demoportal.entity.QUser;
import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.QBoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;
import com.example.demoportal.entity.entity.QBoard;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.demoportal.entity.QUser.*;
import static com.example.demoportal.entity.entity.QBoard.*;

public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
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

    @Override
    public Page<BoardUserDto> searchPageSimple(BoardSearchCondition condition, Pageable pageable) {
        QueryResults<BoardUserDto> result = queryFactory
                .select(new QBoardUserDto(
                        board.id
                        , board.title
                        , board.content
                        , board.category
                        , user.userName
                        , user.nickname
                        , user.age
                ))
                .from(board)
                .leftJoin(board.user, user)
                .where(categoryEquals(condition.getCategory())
                        , userNameEquals(condition.getUserName())
                        , ageGoe(condition.getAgeGoe())
                        , ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardUserDto> content = result.getResults();

        long total = result.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<BoardUserDto> searchPageComplex(BoardSearchCondition condition, Pageable pageable) {
        List<BoardUserDto> responses = queryFactory
                .select(new QBoardUserDto(
                        board.id
                        , board.title
                        , board.content
                        , board.category
                        , user.userName
                        , user.nickname
                        , user.age
                ))
                .from(board)
                .leftJoin(board.user, user)
                .where(categoryEquals(condition.getCategory())
                        , userNameEquals(condition.getUserName())
                        , ageGoe(condition.getAgeGoe())
                        , ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(responses,pageable,responses.size());

    }


    private BooleanExpression categoryEquals(String category) {
        QBoard board = QBoard.board;
        return category != null ? board.category.eq(category) : null;
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
