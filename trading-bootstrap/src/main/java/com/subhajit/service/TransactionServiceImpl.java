package com.subhajit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subhajit.domain.WalletTransactionType;
import com.subhajit.modal.Wallet;
import com.subhajit.modal.WalletTransaction;
import com.subhajit.repository.WalletTransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Override
    public List<WalletTransaction> getTransactionByWallet(Wallet wallet) {
        return walletTransactionRepository.findByWallet(wallet);
    }

    @Override
    public WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type, String transferId, String purpose, Long amount) {
        WalletTransaction transaction = new WalletTransaction();

        transaction.setWallet(wallet);
        transaction.setType(type);
        transaction.setDate(LocalDate.now());
        transaction.setTransferId(transferId);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);

        return walletTransactionRepository.save(transaction);
    }


}
