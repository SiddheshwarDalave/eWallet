package com.example.springboot;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    String userName;
    String name;
    String email;
    String mobile;
}
