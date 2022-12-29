package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public void createUser(@RequestBody UserRequestDto userRequestDto){
        userService.createUser(userRequestDto);
    }
    @GetMapping("/get_user_by_id")
    public User getUser(@RequestParam("id") int id){
        return userService.getUser(id);
    }
    @GetMapping("/get_user_by_username")
    public User getUserByUserName(@RequestParam("userName") String userName) throws Exception{
        return userService.getUserByUserName(userName);
    }

    //test purpose
    @GetMapping("/test")
    public String getUser(){
        return "test";
    }
}
