package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/create")
    public void createTransaction(@RequestBody TransactionRequest transactionRequest){
        transactionService.createTransaction(transactionRequest);
    }
    @GetMapping("/test")
    public String test(){
        return "tested successfully";
    }
}
//done