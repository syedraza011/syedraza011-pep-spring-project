package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    public Account userRegistration(Account account){
      
        if (account == null) {
            throw new IllegalArgumentException("Account object cannot be null.");
        }
    
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
    
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password cannot be null and must be at least 4 characters long.");
        }
        if ((account.getUsername())!=null) {
            return null;
        }
        
           return accountRepository.save(account);
      
    }
    public Account loginUser(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username or Password cannot be null or empty.");
        }
    
        return accountRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    }
    
    // public Account loginUser(String username, String password){

    //     if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
    //         throw new IllegalArgumentException("Username or Password cannot be null or empty.");
    //     }
    //     return accountRepository.findbyusername(username, password)
    //             .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
    // }
}
