package com.example.springboot;

public class UserConverter {
    public static User userConverter(UserRequestDto userRequestDto){
         return User.builder()
                .userName(userRequestDto.getUserName())
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .mobile(userRequestDto.getMobile())
                .build();
    }
    //no need
//    public static UserResponseDto userConverterToDto(User user){
//        return UserResponseDto.builder()
//                .id(user.getId())
//                .userName(user.getUserName())
//                .name(user.getName())
//                .email(user.getEmail())
//                .mobile(user.getMobile())
//                .build();
//    }
}
