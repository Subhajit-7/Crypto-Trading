package com.subhajit.service;

import java.util.List;

import com.subhajit.domain.WalletTransactionType;
import com.subhajit.modal.Wallet;
import com.subhajit.modal.WalletTransaction;

public interface TransactionService {

    List<WalletTransaction> getTransactionByWallet(Wallet wallet);

    WalletTransaction createTransaction(Wallet wallet, WalletTransactionType type, String transferId, String purpose, Long amount);
}
