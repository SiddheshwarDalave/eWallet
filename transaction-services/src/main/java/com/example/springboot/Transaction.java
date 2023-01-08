package com.example.springboot;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String transactionId = UUID.randomUUID().toString();

    private String fromUser;

    private String toUser;

    private int amount;

    private String status;

    private String transactionTime;


}

//done