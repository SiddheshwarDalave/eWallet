package com.example.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = {"send_email"}, groupId = "friends_group")
    public void sendEmailMessage(String message) throws JsonProcessingException {

        //DECODING THE MESSAGE TO JSONObject
        //User email ....message

        JSONObject emailRequest = objectMapper.readValue(message,JSONObject.class);


        //Get the email and message from JSONObject
        String email = (String)emailRequest.get("email");
        String messageBody ="massage body" ;//(String)emailRequest.get("message");


        simpleMailMessage.setFrom("testpurposemail199@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(messageBody);

        simpleMailMessage.setSubject("Transaction Mail");



        javaMailSender.send(simpleMailMessage);

    }
}

//done