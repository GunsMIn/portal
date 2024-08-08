package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.RoleType;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private BoardService boardService;

    @Mock
    Authentication authentication;

    private User userTest;
    private Board boardTest;
    private BoardRequest boardRequest;

    @BeforeEach
    void setUp() {
        userTest = User.builder().userId(10L).userName("김건우").email("test@example.com").nationality("대한민국").phoneNumber("010-2323-2323")
                .roleType(RoleType.ADMIN).registrationType("1").nickname("건담").password("123456789").build();

        boardRequest = BoardRequest.builder()
                .title("테스트제목dto")
                .content("테스트내용dto")
                .category("자유게시판")
                .build();

        boardTest = Board.builder().id(10L).user(userTest).title("테스트 제목").content("테스트 내용").category("자유게시판").build();
    }

    @Test
    public void boardTest() {

        //when
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.ofNullable(userTest));
        when(boardRepository.save(any(Board.class))).thenReturn(boardTest);

        boolean board = boardService.createBoard(boardRequest, authentication);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(boardRepository, times(1)).save(any(Board.class));

        assertTrue(board);
    }

    @Test
    public void boardCreateExceptionTest() {
        //WHEN
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> boardService.createBoard(boardRequest, authentication));

       assertEquals(usernameNotFoundException.getMessage(),"이메일이 존재하지 않습니다.");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(boardRepository, never()).save(any(Board.class));

    }





}