package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
    }
    @GetMapping("/get_user")
    public UserResponseDto getUser(int id){
        return userService.getUser(id);
    }

    @GetMapping("/gs")
    public String getUser(){
        return "test";
    }
}
