package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.UserJoinResponse;
import com.example.demoportal.entity.entity.RoleType;
import com.example.demoportal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//UserService에 대한 Spring MVC 테스트 환경을 설정
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    //UserRepository의 모킹 객체를 Spring의 ApplicationContext에 주입
    @Mock
    private UserRepository userRepository;

    //UserService에 MockBean으로 주입된 UserRepository를 삽입
    @InjectMocks
    private UserService userService;


    private User userTest;

    @BeforeEach
    void setUp() {
        userTest = User.builder()
                .userId(10L)
                .userName("김건우")
                .email("test@example.com")
                .nationality("대한민국")
                .phoneNumber("010-2323-2323")
                .roleType(RoleType.ADMIN)
                .registrationType("1")
                .nickname("건담")
                .password("123456789")
                .build();
    }


    @DisplayName("새로운 회원을 저장한다.")
    @Test
    public void saveUserTest() {

        //given
        when(userRepository.save(any(User.class))).thenReturn(userTest);
        //when
        User savedUser = userRepository.save(userTest);
        //then
        assertEquals(userTest.getUserId(), savedUser.getUserId());
        assertEquals(userTest.getUserName(), savedUser.getUserName());
        assertEquals(userTest.getEmail(), savedUser.getEmail());
        assertEquals(userTest.getNationality(), savedUser.getNationality());
        assertEquals(userTest.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(userTest.getRoleType(), savedUser.getRoleType());
        assertEquals(userTest.getRegistrationType(), savedUser.getRegistrationType());
        assertEquals(userTest.getNickname(), savedUser.getNickname());
    }

    @DisplayName("새로운 회원을 저장한다.")
    @Test
    public void saveUserTest2() {

        //given 테스트 준비 단계
        when(userRepository.save(any(User.class))).thenReturn(userTest);

        //when 테스트 실행 단계
        UserJoinResponse userJoinResponse = userService.saveUser(userTest);

        //then 테스트 검증 단계
        assertEquals(userTest.getUserId(), userJoinResponse.getId());
        assertEquals(userTest.getUserName(), userJoinResponse.getUserName());
        verify(userRepository, times(1)).save(any(User.class));
    }



    @DisplayName("중복 회원일시 예외를 터뜨린다.")
    @Test
    public void saveUser_shouldThrowExceptionWhenUserAlreadyExists() {

        //given
        //메일이 "test@example.com"인 사용자가 이미 데이터베이스에 존재하는 상황을 시뮬레이션
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.ofNullable(userTest));

        //when
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> userService.saveUser(userTest));

        //then
        assertEquals(illegalStateException.getMessage(),"이미 가입된 회원입니다.");
    }


}