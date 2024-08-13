package com.example.demoportal.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserInfoResponse {

    private  String username;
    private  String nickname;
    private  Integer age;

    @QueryProjection
    public UserInfoResponse(String username, String nickname, Integer age) {
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }
}
