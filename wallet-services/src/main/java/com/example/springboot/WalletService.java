package com.example.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaTemplate<String, String > kafkaTemplate;
    @KafkaListener(topics = {"create_wallet"},groupId = "friends_group")
    public void createWallet(String massage) throws JsonProcessingException {

        JSONObject walletRequestJsonObject=objectMapper.readValue(massage,JSONObject.class);

        String userName= (String) walletRequestJsonObject.get("userName");
        Integer value= (Integer) walletRequestJsonObject.get("balance");
        Wallet wallet = Wallet.builder()
                .userName(userName)
                .balance(value).build();

        walletRepository.save(wallet);
    }

    //update wallet methods
//
    Wallet incrementWallet(String userName, int amount){
        /*
        Metgod 1 -> to save it to repo by using methods and all
         */
        //fetched the old wallet with its complete detail
        Wallet oldWallet=walletRepository.findByUserName(userName);
        //added new amount
        int newAmount= oldWallet.getBalance()+amount;
        //fetched the id
         int originalId=oldWallet.getId();
         //made the wallet builder
        Wallet updateWallet= Wallet.builder().id(originalId).userName(userName).balance(newAmount).build();
        //saved it in repo
        walletRepository.save(updateWallet);



        /*
        Method 2 -> write the query
         */
       // int success= walletRepository.updateWallet(userName,amount);

        return updateWallet;

    }

    Wallet decrementWallet(String userName, int amount){
        //fetched the old wallet with its complete detail
        Wallet oldWallet=walletRepository.findByUserName(userName);
        //added new amount
        int newAmount= oldWallet.getBalance()-amount;
        //fetched the id
        int originalId=oldWallet.getId();
        //made the wallet builder
        Wallet updateWallet= Wallet.builder().id(originalId).userName(userName).balance(newAmount).build();
        //saved it in repo
        walletRepository.save(updateWallet);

        return updateWallet;
    }

    @KafkaListener(topics = {"update_wallet"},groupId = "friends_group")
    public void updateWallet(String massage) throws JsonProcessingException {

       JSONObject jsonObject=objectMapper.readValue(massage, JSONObject.class);

       String fromUser= (String) jsonObject.get("fromUser");
       String toUser= (String) jsonObject.get("toUser");
       int transactionAmount= (int) jsonObject.get("amount");
       String transactionId= (String) jsonObject.get("transactionId");

        //TODO STEPS :
        // 1st CHECK BALANCE FROM fromUser
        /*

            //IF FAIL (if senders balance is not sufficient)
            //SEND STATUS AS FAILED

            //OTHERWISE
            deduct the senders money
            add the receivers money
            SEND STATUS AS SUCCESS

         */
        Wallet sendersWallet = walletRepository.findByUserName(fromUser);
        if(sendersWallet.getBalance()>=transactionAmount){

            //HAPPY CASE

            //UPDATE THE WALLETS

            Wallet fromWallet = walletRepository.findByUserName(fromUser);

            fromWallet.setBalance(fromWallet.getBalance() - transactionAmount);
            walletRepository.save(fromWallet);

            Wallet toWallet = walletRepository.findByUserName(toUser);
            toWallet.setBalance(toWallet.getBalance() + transactionAmount);
            walletRepository.save(toWallet);


            //PUSH TO KAFKA

            JSONObject sendToTransaction = new JSONObject();

            sendToTransaction.put("transactionId",transactionId);
            sendToTransaction.put("TransactionStatus","SUCCESS");

            String sendMessage =  sendToTransaction.toString();

            kafkaTemplate.send("update_transaction",sendMessage);

        }
        else{

            //SAD CASE
            JSONObject sendToTransaction = new JSONObject();

            sendToTransaction.put("transactionId",transactionId);
            sendToTransaction.put("TransactionStatus","FAILED");
            String sendMessage =  sendToTransaction.toString();

            kafkaTemplate.send("update_transaction",sendMessage);

        }
    }

}

//done