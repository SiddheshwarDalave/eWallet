package com.example.springboot;

import lombok.Builder;

@Builder
public class UserResponseDto {
    int id;

    String userName; //userName or userCode

    String name;

    String email;

    String mobile;
}
