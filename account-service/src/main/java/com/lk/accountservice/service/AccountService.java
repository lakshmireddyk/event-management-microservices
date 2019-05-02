package com.lk.accountservice.service;

import com.lk.accountservice.model.Account;
import com.lk.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountDetails(Long userId){

        return accountRepository.findById(userId).get();

    }
}
