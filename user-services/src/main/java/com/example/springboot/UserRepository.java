package com.example.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);

    List<User> findAllByUserNameAndName(String userName,String name );

    boolean existsByUserName(String userName);
}
//done