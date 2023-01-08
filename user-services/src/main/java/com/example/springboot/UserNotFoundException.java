package com.example.springboot;

public class UserNotFoundException extends Exception{

    public UserNotFoundException() {
        super("User not found");
    }

}
//done