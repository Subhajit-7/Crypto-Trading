package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken, String> {

    ForgotPasswordToken findByUserId(Long userId);
}
