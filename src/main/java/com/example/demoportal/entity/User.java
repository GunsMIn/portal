package com.example.demoportal.entity;

import com.example.demoportal.entity.dto.UserJoinRequest;
import com.example.demoportal.entity.entity.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false , length = 50, unique = true)
    private String email;

    @Column(nullable = false , length = 100)
    private String password;

    @Column(nullable = false , length = 20)
    private String userName;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Column(nullable = false, length = 30)
    private String nationality;

    @Column(nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(nullable = false, length = 15, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 20)
    private String registrationType;

    public static User from(UserJoinRequest userJoinRequest, PasswordEncoder passwordEncoder) {
        String encodePassword = passwordEncoder.encode(userJoinRequest.getPassword());
        System.out.println("encodePassword = " + encodePassword);
        return User.builder()
                .email(userJoinRequest.getEmail())
                .password(encodePassword)
                .userName(userJoinRequest.getName())
                .roleType(RoleType.USER)
                .joinDate(LocalDateTime.now())
                .nationality(userJoinRequest.getNationality())
                .nickname(userJoinRequest.getNickname())
                .phoneNumber(userJoinRequest.getPhoneNumber())
                .registrationType(userJoinRequest.getRegistrationType())
                .build();
    }
}