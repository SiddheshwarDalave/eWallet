package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
@SpringBootApplication
public class WalletApplication {
    public static void main(String[] args) {

        SpringApplication.run(WalletApplication.class,args);
    }
}
//@SpringBootApplication
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
//public class WalletApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(WalletApplication.class, args);
//    }
//
//}
//done