package com.zinkworks.atmmachine.dataprovider;

import com.zinkworks.atmmachine.domain.Account;
import com.zinkworks.atmmachine.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

class AccountDataProviderTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountDataProvider dataProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldComputeWithdrawal() {
        //Given
        Account account = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(10)).overdraft(BigDecimal.valueOf(10)).build();
        Account subtractedAccount = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(0)).overdraft(BigDecimal.valueOf(10)).build();

        //When
        Mockito.when(repository.findByAccountNumber(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(account));
        Mockito.when(repository.save(ArgumentMatchers.any()))
                .thenReturn(subtractedAccount);

        //Then
        long computeWithdrawal = dataProvider.computeWithdrawal("123456789", 10L);
        Assertions.assertEquals(subtractedAccount.getBalance().longValue(), computeWithdrawal);

    }

    @Test
    void computeBalance() {
        //Given
        Account account = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(10)).overdraft(BigDecimal.valueOf(10)).build();
        //When
        Mockito.when(repository.findByAccountNumberAndPIN("123456789", "1234")).thenReturn(Optional.of(account));

        //Then
        long computeBalance = dataProvider.computeBalance(account.getAccountNumber(), account.getPIN());
        Assertions.assertEquals(10L, computeBalance);
    }
}