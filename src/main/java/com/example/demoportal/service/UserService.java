package com.example.demoportal.service;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.UserJoinResponse;
import com.example.demoportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserJoinResponse saveUser(User user) {
        //회원 유효성 검사
        validateDuplicateMember(user);
        //회원 저장
        return UserJoinResponse.fromUser(userRepository.save(user));
    }


    public void validateDuplicateMember(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(findMember -> {
                    throw new IllegalStateException("이미 가입된 회원입니다.");
                });
    }

}
