package com.subhajit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.subhajit.domain.WalletTransactionType;
import com.subhajit.modal.User;
import com.subhajit.modal.Wallet;
import com.subhajit.modal.WalletTransaction;
import com.subhajit.service.TransactionService;
import com.subhajit.service.UserService;
import com.subhajit.service.WalletService;

@RestController
public class TransactionController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/transactions")
    public ResponseEntity<List<WalletTransaction>> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Wallet wallet = walletService.getUserWallet(user);

        List<WalletTransaction> transactionList = transactionService.getTransactionByWallet(wallet);

        return new ResponseEntity<List<WalletTransaction>>(transactionList, HttpStatus.OK);
    }
}
