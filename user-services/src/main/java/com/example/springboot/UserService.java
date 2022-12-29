package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser(UserRequestDto userRequestDto){
        User user =UserConverter.userConverter(userRequestDto);
        userRepository.save(user);
    }

    public UserResponseDto getUser(int id){
        User user =userRepository.findById(id).get();
        UserResponseDto userResponseDto=UserConverter.userConverterToDto(user);
        return userResponseDto;
    }
}
