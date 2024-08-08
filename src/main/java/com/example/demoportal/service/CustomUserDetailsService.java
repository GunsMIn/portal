package com.example.demoportal.service;

import com.example.demoportal.entity.dto.CustomUserInfoDto;
import com.example.demoportal.entity.security.CustomUserDetails;
import com.example.demoportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        CustomUserInfoDto customUserInfoDto = userRepository.findByEmail(username)
                .map(CustomUserInfoDto::createCustomUserInfoDto)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));

        return new CustomUserDetails(customUserInfoDto);
    }


}
