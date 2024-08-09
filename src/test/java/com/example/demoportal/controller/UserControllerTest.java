package com.example.demoportal.controller;

import com.example.demoportal.entity.User;
import com.example.demoportal.entity.dto.UserJoinRequest;
import com.example.demoportal.entity.dto.UserJoinResponse;
import com.example.demoportal.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private UserJoinRequest userJoinRequest;

    private UserJoinResponse userJoinResponse;

    @BeforeEach
    void setUp() {
        userJoinRequest = UserJoinRequest.builder()
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        userJoinResponse = UserJoinResponse.builder()
                .id(1L)
                .userName("Test User")
                .build();
    }

    @BeforeEach
    void init() {
        userJoinRequest = UserJoinRequest.builder()
                .email("test@example.com")
                .password("password123")
                .nationality("대한민국")
                .nickname("Test User")
                .phoneNumber("010-1234-5678")
                .registrationType("일반")
                .name("김건우")
                .build();

        userJoinResponse = UserJoinResponse.builder()
                .userName("김건우")
                .id(10L)
                .build();
    }


    @Test
    void createUser() throws Exception {

        when(userService.saveUser(any(User.class))).thenReturn(userJoinResponse);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        // when & then
        mockMvc.perform(post("/users/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userJoinRequest)))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.data.userId").value(userJoinResponse.getId()))
                .andExpect((ResultMatcher) jsonPath("$.data.username").value(userJoinResponse.getUserName()));
    }
}