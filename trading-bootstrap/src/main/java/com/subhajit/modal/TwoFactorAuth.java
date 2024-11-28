package com.subhajit.modal;

import com.subhajit.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
