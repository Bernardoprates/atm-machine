package com.zinkworks.atmmachine.dataprovider;

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
public class AccountDataProvider {

    private final AccountRepository accountRepository;

    public long computeWithdrawal(String accountNumber, long amount){
        Optional<Account> optionalAccount = accountRepository
                .findByAccountNumber(accountNumber);
        Account account = optionalAccount.get();
        BigDecimal balance = account.getBalance().subtract(BigDecimal.valueOf(amount));
        if(balance.signum() < 0 &&
                balance.abs().compareTo(account.getOverdraft()) < 0) {
            throw new AtmMachineGlobalException(ErrorCode.INSUFFICIENT_FUNDS,
                    "You do not have sufficient funds in order to perform this transaction.");
        }
        account.setBalance(balance);
        accountRepository.save(account);
        return balance.longValue();
    }

    public long computeBalance(String account, String PIN) {
        return accountRepository.findByAccountNumberAndPIN(account, PIN)
                .get().getBalance().longValue();
    }
}
