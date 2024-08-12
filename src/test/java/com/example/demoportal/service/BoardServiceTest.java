package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardDetailResponse;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.dto.BoardResponse;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Comment;
import com.example.demoportal.entity.entity.RoleType;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
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
    private Board boardResponse;
    private BoardRequest boardRequest;
    private List<Comment> comments;

    @BeforeEach
    void setUp() {
        userTest = User.builder().userId(10L).userName("김건우").email("test@example.com").nationality("대한민국").phoneNumber("010-2323-2323")
                .roleType(RoleType.ADMIN).registrationType("1").nickname("건담").password("123456789").build();

        boardRequest = BoardRequest.builder()
                .title("테스트제목dto")
                .content("테스트내용dto")
                .category("자유게시판")
                .build();

        Comment comment1 = Comment.builder()
                .id(20L)
                .user(userTest)
                .board(boardResponse)
                .comment("댓글 내용입니다1.").build();

        Comment comment2 = Comment.builder()
                .id(20L)
                .user(userTest)
                .board(boardResponse)
                .comment("댓글 내용입니다2.").build();

        comments = List.of(comment1, comment2);

        boardResponse = Board.builder().
                id(10L)
                .user(userTest)
                .title("테스트 제목")
                .content("테스트 내용")
                .category("자유게시판")
                .comments(comments)
                .build();




    }

    @DisplayName("글 쓰기 성공 테스트")
    @Test
    public void boardTest() {

        //when
        when(authentication.getName()).thenReturn("test@example.com");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.ofNullable(userTest));
        when(boardRepository.save(any(Board.class))).thenReturn(boardResponse);

        boolean board = boardService.createBoard(boardRequest, authentication);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(boardRepository, times(1)).save(any(Board.class));

        assertTrue(board);
    }

    @DisplayName("글 쓰기 에러 테스트 (이메잃 미존재)")
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

    @DisplayName("글쓰기 목록 조회 테스트")
    @Test
    public void boardList() {
        //given
        Board board2 = Board.builder()
                .id(11L)
                .title("글쓰기 제목1")
                .content("글쓰기 내용1")
                .category("자유게시판")
                .user(userTest)
                .build();

        Board board3 = Board.builder()
                .id(12L)
                .title("글쓰기 제목2")
                .content("글쓰기 내용2")
                .category("자유게시판")
                .user(userTest)
                .build();

        //when
        when(boardRepository.findAll()).thenReturn(List.of(board2, board3));
        List<BoardResponse> boardList = boardService.getBoardList();

        //then
        assertNotNull(boardList);
        assertEquals(2, boardList.size());
        assertEquals("글쓰기 제목1", boardList.get(0).getTitle());
        assertEquals("글쓰기 내용1", boardList.get(0).getContent());
        assertEquals("자유게시판", boardList.get(0).getCategory());
        assertEquals("글쓰기 제목2", boardList.get(1).getTitle());
        assertEquals("글쓰기 내용2", boardList.get(1).getContent());
        assertEquals("자유게시판",boardList.get(1).getCategory());
    }


    @DisplayName("글 Id로 조히 테스트")
    @Test
    public void testgetBoardListByBoardId() {
        //when
        when(boardRepository.findById(anyLong())).thenReturn(Optional.ofNullable(boardResponse));
        BoardDetailResponse response = boardService.getBoardListByBoardId(10L);

        //then
        assertNotNull(response);
        assertEquals("자유게시판", response.getCategory());
        assertEquals("테스트 제목",response.getTitle());
        assertEquals("테스트 내용",response.getContent());
        assertEquals("건담", response.getWriter());
        assertEquals("댓글 내용입니다1.",response.getCommentResponseList().get(0).getComment());
        assertEquals(20L,response.getCommentResponseList().get(0).getCommentId());
        assertEquals(2, response.getCommentResponseList().size());
    }

    @Test
    public void testgetBoardListByBoardId_exceptionTest() {

        //when
        when(boardRepository.findById(anyLong())).thenReturn(Optional.empty());

        //boardService.getBoardListByBoardId(anyLong()) 실행시 RuntimeException이 발생하기를 기대한다.
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> boardService.getBoardListByBoardId(anyLong()));

        //then
        assertEquals("게시글이 존재하지 않습니다.", runtimeException.getMessage());
    }

}