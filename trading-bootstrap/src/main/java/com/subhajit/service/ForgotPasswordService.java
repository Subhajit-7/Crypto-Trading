package com.subhajit.service;

import com.subhajit.domain.VerificationType;
import com.subhajit.modal.ForgotPasswordToken;
import com.subhajit.modal.User;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
