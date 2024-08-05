package com.example.demoportal.entity.dto;

import com.example.demoportal.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private Long id;
    private String userName;

    public static UserJoinResponse fromUser(User user) {
        UserJoinResponse response = new UserJoinResponse(user.getUserId(),user.getUserName());
        return response;
    }
}
