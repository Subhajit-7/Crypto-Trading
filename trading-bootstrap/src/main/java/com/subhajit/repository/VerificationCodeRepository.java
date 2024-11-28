package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    public VerificationCode findByUserId(Long userId);
}
