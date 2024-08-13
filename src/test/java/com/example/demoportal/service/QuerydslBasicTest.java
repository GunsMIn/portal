package com.example.demoportal.service;

import com.example.demoportal.entity.QUser;
import com.example.demoportal.entity.User;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.QBoard;
import com.example.demoportal.entity.entity.RoleType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;


    @BeforeEach
    void setUp() {

        User userTest1 = User.builder()
                .userId(10L)
                .userName("김건우3")
                .email("test3@example.com")
                .nationality("대한민국")
                .phoneNumber("010-2323-2323")
                .roleType(RoleType.ADMIN)
                .registrationType("1")
                .nickname("건담3")
                .password("123456789")
                .build();

        User userTest2 = User.builder()
                .userId(11L)
                .userName("김민희4")
                .email("test4@example.com")
                .nationality("대한민국")
                .phoneNumber("010-2323-2324")
                .roleType(RoleType.USER)
                .registrationType("1")
                .nickname("미니4")
                .password("123456789")
                .build();
    }

    @Test
    public void queryDSLTest() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser user = QUser.user;

        User findUser = queryFactory
                .select(user)
                .from(user)
                .where(user.userName.eq("김건우"))
                .fetchOne();

        assertEquals("김건우", findUser.getUserName());
    }

    @Test
    public void queryDSLTest2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser user = QUser.user;

        User findUser = queryFactory
                .select(user)
                .from(user)
                .where(user.userName.eq("김민희")
                        .and(user.nickname.eq("minhee")))
                .fetchOne();

        assertEquals("김민희", findUser.getUserName());
    }

    @Test
    public void queryDSLTest3() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QBoard board = QBoard.board;

        List<Board> boards = queryFactory.select(board)
                .from(board)
                .orderBy(board.id.asc())
                .offset(1)
                .limit(2)
                .fetch();

        assertEquals(2, boards.size());
        assertEquals("글쓰기2",boards.get(0).getTitle());

    }
}
