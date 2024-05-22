package com.example.emtesttask.repository;

import com.example.emtesttask.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
