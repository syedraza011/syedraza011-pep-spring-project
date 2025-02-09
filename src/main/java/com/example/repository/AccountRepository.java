package com.example.repository;
import java.util.Optional;
import com.example.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsernameAndPassword(String username, String password);
    
    boolean findByUsername(String username);
    //public Account accountRegistrationRepository();
    //public Optional<Account>  accountLogin(String username, String password);
  
}