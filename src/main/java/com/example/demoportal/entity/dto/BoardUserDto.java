package com.example.demoportal.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BoardUserDto {

    private Long id;
    private String title;
    private String content;
    private String catagory;
    private String username;
    private String nickname;
    private int age;

    @QueryProjection
    public BoardUserDto(Long id,String title, String content, String catagory, String username, String nickname , int age) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.catagory = catagory;
        this.username = username;
        this.nickname = nickname;
        this.age = age;
    }
}
