package com.subhajit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.Wallet;
import com.subhajit.modal.WalletTransaction;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWallet(Wallet wallet);
}
