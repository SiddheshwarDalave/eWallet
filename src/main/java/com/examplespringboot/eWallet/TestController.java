package com.examplespringboot.eWallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String getUsers(){
        return "only for test purpose";
    }
}
