package com.example.springboot;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@Entity
@Table(name="wallets")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private int balance;
}

//done