package com.example.springboot;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByUserName(String userName);

    //query to update wallet

//    @Modifying
//    @Query(value = "update wallet w set w.amount=w.amount + :amount where w.userName= :userName" )
//    int updateWallet(String userName, int amount);
}

//done