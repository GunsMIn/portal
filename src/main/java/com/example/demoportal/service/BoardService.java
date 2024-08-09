package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.BoardRequest;
import com.example.demoportal.entity.dto.BoardResponse;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

}
