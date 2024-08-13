package com.example.demoportal.entity.dto.querydsl;

import lombok.Data;

@Data
public class BoardSearchCondition {

    private Long id;
    private String userName;
    private int ageGoe;
    private int ageLoe;
}
