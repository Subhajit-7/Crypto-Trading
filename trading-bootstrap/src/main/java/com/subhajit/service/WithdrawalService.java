package com.subhajit.service;

import java.util.List;

import com.subhajit.modal.User;
import com.subhajit.modal.Withdrawal;

public interface WithdrawalService {

    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal procedWithwithdrawal(Long withdrawalId, boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);

    List<Withdrawal> getAllWithdrawalRequest();
}
