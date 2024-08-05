//package com.example.demoportal;
//
//import com.example.demoportal.entity.User;
//import com.example.demoportal.entity.dto.UserDto;
//import com.example.demoportal.service.UserService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    public User createUser() {
//        UserDto userDto = new UserDto();
//        userDto.setName("김건우3");
//        userDto.setEmail("gunwoo3@hanpass.com");
//        userDto.setPassword("1");
//        return User.CreateMember(userDto, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("회원가입 테스트")
//    @Rollback(value = false)
//    public void createMember() {
//        User user = createUser();
//        User savedUser = userService.saveUser(user);
//        assertEquals(user, savedUser);
//        assertEquals(user.getUserName(), savedUser.getUserName());
//        assertEquals(user.getEmail(), savedUser.getEmail());
//    }
//
//    @Test
//    @DisplayName("중복 회원 가입 테스트")
//    @Rollback(value = false)
//    public void duplicateEmail() {
//
//        User user1 = createUser();
//        User user2 = createUser();
//
//        userService.saveUser(user1);
//
//        Throwable e = assertThrows(IllegalStateException.class, () -> {
//            userService.saveUser(user2);
//        });
//
//        assertEquals("이미 가입된 회원입니다.", e.getMessage());
//
//    }
//
//}
