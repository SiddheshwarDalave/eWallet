package com.example.springboot;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDto {
    String userName;
    String name;
    String email;
    String mobile;
}
//done