package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.dto.CommentRequest;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Comment;
import com.example.demoportal.entity.entity.RoleType;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.CommentRepository;
import com.example.demoportal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BoardRepository boardRepository;
    @InjectMocks
    private CommentService commentService;

    @Mock
    Authentication authentication;

    private User userTest;
    private Board boardTest;
    private BoardRequest boardRequest;
    private Comment commentTest;
    private CommentRequest commentRequest;

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

        commentRequest = CommentRequest.builder().comment("댓댓글").build();

        commentTest = Comment.builder()
                .board(boardTest)
                .comment("댓글입니다.")
                .user(userTest)
                .build();
    }

    @Test
    public void createComment() {

        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.ofNullable(userTest));
        when(boardRepository.findById(10L)).thenReturn(Optional.ofNullable(boardTest));
        when(commentRepository.save(any(Comment.class))).thenReturn(commentTest);

        boolean result = commentService.createComment(commentRequest, 10L, authentication);

        assertTrue(result);
        verify(userRepository,times(1)).findByEmail("test@example.com");
        verify(boardRepository,times(1)).findById(10L);
        verify(commentRepository,times(1)).save(any(Comment.class));
    }

    @Test
    public void createUserExceptionComment() {
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> commentService.createComment(commentRequest, 1L, authentication));

        verify(userRepository,times(1)).findByEmail("test@example.com");
        verify(boardRepository,never()).findById(10L);
        assertEquals(usernameNotFoundException.getMessage(),"이메일이 존재하지 않습니다.");
    }

    @Test
    public void createBoardExceptionComment() {
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.ofNullable(userTest));
        when(boardRepository.findById(10L)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> commentService.createComment(commentRequest, 10L, authentication));


        verify(userRepository,times(1)).findByEmail("test@example.com");
        verify(boardRepository,times(1)).findById(10L);
        verify(commentRepository,never()).save(any(Comment.class));
        assertEquals(runtimeException.getMessage(),"게시글이 존재하지 않습니다.");

    }



}