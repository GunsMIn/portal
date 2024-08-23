package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.entity.Board;
import com.example.demoportal.entity.entity.Likes;
import com.example.demoportal.repository.BoardRepository;
import com.example.demoportal.repository.LikeRepository;
import com.example.demoportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public boolean addLike(Authentication authentication, Long boardId) {
        boolean result = false;

        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        //좋아요 방지
        if (isNotAlreadytLike(user, board)) {
            //좋아요를 누르지 않았다면 좋아요 등록
            likeRepository.save(Likes.create(board, user));
            result = true;
        }
        return result;
    }


    //사용자가 이미 좋아요를 눌렀는 지 체크
    private boolean isNotAlreadytLike(User user, Board board) {
        return likeRepository.findByUserAndBoard(user, board).isEmpty();
    }

}
