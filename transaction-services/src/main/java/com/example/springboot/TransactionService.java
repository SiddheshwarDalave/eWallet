package com.example.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    public void createTransaction(TransactionRequest transactionRequest) {

        Transaction transaction;
        transaction=Transaction.builder()
                .fromUser(transactionRequest.getFromUser())
                .toUser(transactionRequest.getToUser())
                .amount(transactionRequest.getAmount())
                .status("PENDING")
                .transactionId(String.valueOf(UUID.randomUUID()))
                .transactionTime(String.valueOf(new Date()))
                .build();

        transactionRepository.save(transaction);

        JSONObject walletRequest=new JSONObject();
        walletRequest.put("fromUser",transactionRequest.getFromUser());
        walletRequest.put("toUser",transactionRequest.getToUser());
        walletRequest.put("amount",transactionRequest.getAmount());
        walletRequest.put("transactionId",transaction.getTransactionId());

        String massage =walletRequest.toString();

        kafkaTemplate.send("update_wallet",massage);

    }
    @KafkaListener(topics = {"update_transaction"},groupId = "friends_group")
    public void updateTransaction(String message) throws JsonProcessingException {


        //Decode the message
        JSONObject transactionRequest = objectMapper.readValue(message,JSONObject.class);

        String transactionStatus = (String) transactionRequest.get("TransactionStatus");


        String transactionId = (String) transactionRequest.get("transactionId");


        Transaction t = transactionRepository.findByTransactionId(transactionId);

        t.setStatus(transactionStatus);

        transactionRepository.save(t);


        // CALL NOTIFICATION SERVICE AND SEND EMAILS
        callNotificationService(t);


    }

    private void callNotificationService(Transaction transaction) {
        //FETCH IS EMAIL FROM USER SERVICE
        String fromUser =transaction.getFromUser();
        String toUser = transaction.getToUser();
        String transactionId = transaction.getTransactionId();

        // for sender
        URI url=URI.create("http://localhost:8282/get_user_by_username?userName="+fromUser);
        HttpEntity httpEntity = new HttpEntity(new HttpHeaders());

        JSONObject fromUserObject = restTemplate.exchange(url, HttpMethod.GET,httpEntity,JSONObject.class).getBody();

        String senderName = (String)fromUserObject.get("name");

        String senderEmail = (String)fromUserObject.get("email");

        //for receiver

        URI url1=URI.create("http://localhost:8282/get_user_by_username?userName="+fromUser);
        HttpEntity httpEntity1 = new HttpEntity(new HttpHeaders());

        JSONObject fromUserObject1 = restTemplate.exchange(url1, HttpMethod.GET,httpEntity1,JSONObject.class).getBody();

        String receiverName = (String)fromUserObject.get("name");

        String receiverEmail = (String)fromUserObject.get("email");

        //SEND THE EMAIL AND MESSAGE TO NOTIFICATIONS-SERVICE VIA KAFKA
        JSONObject emailRequest = new JSONObject();

        //SENDER should always receive email ---->

        emailRequest.put("email",senderEmail);

        String SenderMessageBody = String.format("Hi %s the transcation with transactionId %s has been %s of Rs %d",
                senderName,transactionId,transaction.getStatus(),transaction.getAmount());

        emailRequest.put("message",SenderMessageBody);

        String message = emailRequest.toString();

        kafkaTemplate.send("send_email",message);

        if(transaction.getStatus().equals("FAILED")){
            return;
        }
//SEND an email to the reciever also

        emailRequest.put("email",senderEmail);

        String receiverMessageBody = String.format("Hi %s you have recived money %d from %s",
                receiverName,transaction.getAmount(),senderName);

        emailRequest.put("message",receiverMessageBody);

        message = emailRequest.toString();

        kafkaTemplate.send("send_email",message);
    }
}
//done