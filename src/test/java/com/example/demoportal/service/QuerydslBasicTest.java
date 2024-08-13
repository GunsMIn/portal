package com.example.demoportal.service;

import com.example.demoportal.entity.QUser;
import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.QUserInfoResponse;
import com.example.demoportal.entity.dto.UserInfoResponse;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.QBoard;
import com.example.demoportal.entity.entity.RoleType;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeEach
    void setUp() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void queryDSLTest() {

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

        QBoard board = QBoard.board;

        List<Board> boards = queryFactory.select(board)
                .from(board)
                .orderBy(board.id.asc())
                .offset(1)
                .limit(2)
                .fetch();

        assertEquals(2, boards.size());
        assertEquals("글쓰기2", boards.get(0).getTitle());
    }

    @DisplayName("조인테스트")
    @Test
    public void queryDSLTest4() {

        QBoard board = QBoard.board;
        QUser user = QUser.user;

        List<Board> boards = queryFactory.selectFrom(board)
                .join(board.user, user)
                .on(board.user.userId.eq(1L))
                .fetch();

        assertEquals(54, boards.size());
    }



    @DisplayName("페치 조인테스트")
    @Test
    public void queryDslTest5() {

        QBoard board = QBoard.board;
        QUser user = QUser.user;

        Board one = queryFactory.selectFrom(board)
                .join(board.user, user).fetchJoin()
                .where(board.user.userId.eq(2L))
                .fetchOne();

        assertEquals("글쓰기 미니 323 ",one.getTitle());
        assertEquals(55L,one.getId());
        assertEquals("김민희",one.getUser().getUserName());
    }

    /**
    * 나이가 가장 많은 회원 조회 (서브쿼리 사용 where 절 사용)
    */
    @Test
    public void queryDSLTest6() {

        QUser user = QUser.user;

        JPQLQuery<Integer> maxAge = JPAExpressions
                .select(user.age.max())
                .from(user);

        User result = queryFactory.selectFrom(user)
                .where(user.age.eq(maxAge))
                .fetchOne();

        assertEquals("손흥민",result.getUserName());
    }

    /**
     * 튜플
     */
    @Test
    public void queryDSLTest7() {
        QUser user = QUser.user;

        List<Tuple> result = queryFactory
                .select(user.userName, user.nickname, user.age)
                .from(user)
                .fetch();

        for (Tuple tuple : result) {
            String userName = tuple.get(user.userName);
            String nickname = tuple.get(user.nickname);
            Integer age = tuple.get(user.age);

            System.out.println("userName = " + userName);
            System.out.println("nickname = " + nickname);
            System.out.println("age = " + age);
        }
    }

    /**
     * 튜플 생성자 접근
     */
    @Test
    public void queryDSLTest8() {
        QUser user = QUser.user;

        List<UserInfoResponse> result = queryFactory
                .select(Projections.constructor(UserInfoResponse.class,user.userName, user.nickname, user.age))
                .from(user)
                .fetch();

        System.out.println("result = " + result);
    }


    /**
     * 쿼리프로젝션 사용
     */
    @Test
    public void queryDSLTest9() {
        QUser user = QUser.user;

        List<UserInfoResponse> result = queryFactory
                .select(new QUserInfoResponse(user.userName, user.nickname, user.age))
                .from(user)
                .fetch();

        System.out.println("result = " + result);
    }


    @Test
    public void 동적쿼리_queryDSLTest10() {
        String userNameParam = "김건우";
        Integer ageParam = 28;

        List<User> users = searchMember(userNameParam, ageParam);
        System.out.println("users = " + users);

    }


    private List<User> searchMember(String userNameParam, Integer ageParam) {
        QUser user = QUser.user;

        return queryFactory
                .select(user)
                .from(user)
                .where(findUserName(userNameParam), findAgeParam(ageParam))
                .fetch();
    }

    //User 엔티티 이름으로 검색
    private BooleanExpression findUserName(String userNameParam) {
        QUser user = QUser.user;
        return user.userName != null ? user.userName.eq(userNameParam) : null;
    }

    //User 엔티티 나이로 검색
    private BooleanExpression findAgeParam(Integer age) {
        QUser user = QUser.user;
        return user.age != null ? user.age.eq(age) : null;
    }




}
