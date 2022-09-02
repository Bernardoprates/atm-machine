package com.zinkworks.atmmachine.dataprovider.validator;

import com.zinkworks.atmmachine.domain.Account;
import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import com.zinkworks.atmmachine.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountValidatorTest {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private  AccountValidator validator;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldValidatePin() {
       //Given
        Account account = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(1000))
                .overdraft(BigDecimal.valueOf(1000))
                .build();

        //When
        Mockito.when(repository.findByAccountNumberAndPIN("123456789", "1234")).
                thenReturn(Optional.of(account));

        //Then
        Assertions.assertDoesNotThrow(() -> validator.validatePin("123456789", "1234"));
    }

    @Test
    void shouldFailValidatePin() {
        //Given

        //When
        Mockito.when(repository
                .findByAccountNumberAndPIN(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        //Then
        AtmMachineGlobalException exception = Assertions.assertThrows(AtmMachineGlobalException.class, () -> validator.validatePin("123456788", "1233"));
        Assertions.assertEquals("Invalid pin/account.", exception.getMessage());
    }


    @Test
    void shouldValidateBalance() {
        //Given
        Account account = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(10))
                .overdraft(BigDecimal.valueOf(100))
                .build();
        long amount = 10L;

        //When
        Mockito.when(repository.findByAccountNumber("123456789"))
                .thenReturn(Optional.of(account));

        //Then
        Assertions.assertDoesNotThrow(() ->validator.validateBalance("123456789", amount));
    }

    @Test
    void shouldNotValidateBalanceInvalidWithdrawalAmount() {
        //Given
        Account account = Account.builder()
                .accountNumber("123456789")
                .PIN("1234")
                .balance(BigDecimal.valueOf(10))
                .overdraft(BigDecimal.valueOf(100))
                .build();
        long amount = -2L;

        //When
        Mockito.when(repository.findByAccountNumber("123456789"))
                .thenReturn(Optional.of(account));

        //Then
        AtmMachineGlobalException exception = assertThrows(AtmMachineGlobalException.class, () -> validator.validateBalance("123456789", amount));
        Assertions.assertEquals("Please specify a proper amount.", exception.getMessage());
    }
}