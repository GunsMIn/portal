package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardDetailResponse;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.dto.BoardResponse;
import com.example.demoportal.entity.dto.BoardUserDto;
import com.example.demoportal.entity.dto.querydsl.BoardSearchCondition;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public boolean createBoard(BoardRequest dto, Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Board board = Board.from(dto, user);
        return !ObjectUtils.isEmpty(boardRepository.save(board));
    }

    public List<BoardResponse> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(BoardResponse::from)
                .collect(Collectors.toList());
    }

    //배치사이즈 테스트 용.
    public List<BoardResponse> getBoardListAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardResponse> boardResponseList = new ArrayList<>();
        for (Board board : boardList) {
            boardResponseList.add(BoardResponse.from(board));
        }
        return boardResponseList;
    }

    //배치사이즈 테스트 용.
    public List<BoardDetailResponse> getBoardListaLLV2() {

        List<Board> boardList = boardRepository.findAll();
        List<BoardDetailResponse> boardResponseList = new ArrayList<>();
        for (Board board : boardList) {
            boardResponseList.add(BoardDetailResponse.from(board));
        }
        return boardResponseList;
    }

    public Page<BoardResponse> getBoardListUsedPaging(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        Page<BoardResponse> responses = boards.map(BoardResponse::from);
        return responses;
    }

    public BoardDetailResponse getBoardListByBoardId(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

         return BoardDetailResponse.from(board);
    }


    //querydsl
    public List<BoardUserDto> searchBoardUserList(BoardSearchCondition condition) {
        List<BoardUserDto> response = boardRepository.search(condition);
        return response;
    }

    //querydsl simple
    public Page<BoardUserDto> searchBoardUserList(BoardSearchCondition condition, Pageable page) {
        Page<BoardUserDto> boardUserDtos = boardRepository.searchPageSimple(condition, page);
        return boardUserDtos;
    }

    //querydsl advanced
    //querydsl
    public Page<BoardUserDto> searchBoardUserListAdvanved(BoardSearchCondition condition, Pageable page) {
        Page<BoardUserDto> boardUserDtos = boardRepository.searchPageComplex(condition, page);
        return boardUserDtos;
    }

}
