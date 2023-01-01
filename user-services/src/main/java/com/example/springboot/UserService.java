package com.example.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public final String REDIS_PREFIX_KEY="user::";

    public void createUser(UserRequestDto userRequestDto) {
        //save first to repo ant then in cache

        User user = UserConverter.userConverter(userRequestDto);
        userRepository.save(user);

        //save in cache //implemented method
        saveInCache(user);
    }
    private void saveInCache(User user){
        Map map=objectMapper.convertValue(user, Map.class);
        redisTemplate.opsForHash().putAll(REDIS_PREFIX_KEY+user.getUserName(),map);
        redisTemplate.expire(REDIS_PREFIX_KEY+user.getUserName(), Duration.ofHours(2));
    }



    public User getUser(int id){
        User user =userRepository.findById(id).get();
        //UserResponseDto userResponseDto=UserConverter.userConverterToDto(user); //not required
        return user;
    }

    public User getUserByUserName(String userName) throws UserNotFoundException {

        Map map=redisTemplate.opsForHash().entries(REDIS_PREFIX_KEY+userName);
        if(map==null || map.size()==0){
            User user=userRepository.findByUserName(userName);
            if(user!=null){
                saveInCache(user);
            }
            else{
             throw new UserNotFoundException();
            }
            return user;
        }
        else{
            User object=objectMapper.convertValue(map,User.class);
            return object;
        }
    }
}
