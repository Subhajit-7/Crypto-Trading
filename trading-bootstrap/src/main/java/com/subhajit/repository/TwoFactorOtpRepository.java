package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.TwoFactorOTP;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
    TwoFactorOTP findByUserId(Long userId);
}
