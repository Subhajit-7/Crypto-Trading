package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
