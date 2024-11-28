package com.subhajit.service;

import com.subhajit.modal.PaymentDetails;
import com.subhajit.modal.User;

public interface PaymentDetailsService {

    public PaymentDetails addPaymentDetails(
        String accountNumber, 
        String accountHolderName, 
        String ifsc, 
        String bankName, 
        User user
    );

    public PaymentDetails getUsersPaymentDetails(User user);
}
