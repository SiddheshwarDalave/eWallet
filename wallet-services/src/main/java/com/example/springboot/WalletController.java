package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WalletController {
    @Autowired
     WalletService walletService;

    @PostMapping("/create")
    public void createWallet(@RequestParam("userName") String userName ){
        walletService.createWallet(userName);
    }

    @PutMapping("/credit")
    public Wallet credited(@RequestParam("userName") String userName,
                           @RequestParam("amount") int amount){
        return walletService.incrementWallet(userName,amount);
    }

    @PutMapping("/debit")
    public Wallet debited(@RequestParam("userName") String userName,
                           @RequestParam("amount") int amount){
        return walletService.decrementWallet(userName,amount);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }



}
