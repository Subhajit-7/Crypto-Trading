package com.subhajit.request;

import com.subhajit.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
