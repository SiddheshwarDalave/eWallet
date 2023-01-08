package com.example.springboot;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String userName; //userName or userCode

    String name;

    String email;

    String mobile;

}
//done