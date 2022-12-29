package com.examplespringboot.eWallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/gett")
    public String getUsers(){
        return "tesxdzftT";
    }
}
