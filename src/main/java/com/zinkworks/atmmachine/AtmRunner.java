package com.zinkworks.atmmachine;

import com.zinkworks.atmmachine.domain.Account;
import com.zinkworks.atmmachine.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AtmRunner implements CommandLineRunner {

    private final AccountRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();
        Account account1 = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(800))
                .overdraft(BigDecimal.valueOf(200))
                .build();
        Account account2 = Account.builder()
                .accountNumber("987654321")
                .PIN("4321")
                .balance(BigDecimal.valueOf(1230))
                .overdraft(BigDecimal.valueOf(1500))
                .build();
        repository.insert(List.of(account1, account2));
    }
}
