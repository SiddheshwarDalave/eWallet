package com.example.springboot;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;

    public void createWallet(String userName){
        Wallet wallet=Wallet.builder()
                .userName(userName).
                amount(0).build();

        walletRepository.save(wallet);
    }

    //update wallet methods

    Wallet incrementWallet(String userName, int amount){
        /*
        Metgod 1 -> to save it to repo by using methods and all
         */
        //fetched the old wallet with its complete detail
        Wallet oldWallet=walletRepository.findByUserName(userName);
        //added new amount
        int newAmount= oldWallet.getAmount()+amount;
        //fetched the id
         int originalId=oldWallet.getId();
         //made the wallet builder
        Wallet updateWallet= Wallet.builder().id(originalId).userName(userName).amount(newAmount).build();
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
        int newAmount= oldWallet.getAmount()-amount;
        //fetched the id
        int originalId=oldWallet.getId();
        //made the wallet builder
        Wallet updateWallet= Wallet.builder().id(originalId).userName(userName).amount(newAmount).build();
        //saved it in repo
        walletRepository.save(updateWallet);

        return updateWallet;
    }
}
