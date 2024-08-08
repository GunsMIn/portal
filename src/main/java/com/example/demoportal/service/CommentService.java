package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.CommentRequest;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Comment;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.CommentRepository;
import com.example.demoportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public boolean createComment(CommentRequest request,Long boardId ,Authentication authentication) {

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        return !ObjectUtils.isEmpty(commentRepository.save(Comment.of(board, user, request.getComment())));

    }
}
