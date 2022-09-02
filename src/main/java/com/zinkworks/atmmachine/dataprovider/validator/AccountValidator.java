package com.zinkworks.atmmachine.dataprovider.validator;

import com.zinkworks.atmmachine.constants.ErrorCode;
import com.zinkworks.atmmachine.domain.Account;
import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import com.zinkworks.atmmachine.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountValidator {
    private final AccountRepository repository;

    public void validatePin(String accountNumber, String PIN) {
        Optional<Account> persistedAccount = repository.findByAccountNumberAndPIN(accountNumber, PIN);
        if (persistedAccount.isEmpty()) {
            throw new AtmMachineGlobalException(ErrorCode.PIN_VALIDATION_FAILED, "Invalid pin/account.");
        }
    }

    public void validateBalance(String accountNumber, long amount) {
        Account account = repository.findByAccountNumber(accountNumber).get();
        BigDecimal maxAmount = account.getBalance().add(account.getOverdraft());
        if(amount <= 0) {
            throw new AtmMachineGlobalException(ErrorCode.AMOUNT_INVALID, "Please specify a proper amount.");
        }
        if(BigDecimal.valueOf(amount).compareTo(maxAmount) > 0) {
            throw new AtmMachineGlobalException(ErrorCode.INSUFFICIENT_FUNDS,
                    "You do not have sufficient funds in order to perform this transaction.");
        }
    }
}
