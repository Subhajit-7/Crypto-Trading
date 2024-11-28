package com.subhajit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.subhajit.modal.Coin;

public interface CoinRepository extends JpaRepository<Coin, String> {

}
