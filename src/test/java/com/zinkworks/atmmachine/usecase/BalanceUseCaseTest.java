package com.zinkworks.atmmachine.usecase;

import com.zinkworks.atmmachine.constants.ErrorCode;
import com.zinkworks.atmmachine.dataprovider.AccountDataProvider;
import com.zinkworks.atmmachine.dataprovider.validator.AccountValidator;
import com.zinkworks.atmmachine.domain.BalanceRequest;
import com.zinkworks.atmmachine.domain.BalanceResponse;
import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import com.zinkworks.atmmachine.mapper.BalanceMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class BalanceUseCaseTest {

    @Mock
    private AccountValidator validator;

    @Mock
    private AccountDataProvider dataProvider;

    @Mock
    private BalanceMapper mapper;

    @InjectMocks
    private BalanceUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldExecuteValidationAndOperation() {
        //Given
        BalanceRequest request = new BalanceRequest().account("123456789")
                .PIN("1234");
        BalanceResponse response = new BalanceResponse().account("123456789")
                .accountBalance(10);

        //When
        Mockito.doNothing().when(validator).validatePin(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
        Mockito.when(dataProvider.computeBalance(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(10L);
        Mockito.when(mapper.balanceToResponse(ArgumentMatchers.any(),
                ArgumentMatchers.anyLong())).thenReturn(response);

        //Then
        BalanceResponse balanceResponse = useCase.execute(request);
        Assertions.assertNotNull(balanceResponse);
        Assertions.assertNotNull(balanceResponse.getAccount());
        Assertions.assertEquals(request.getAccount(), balanceResponse.getAccount());
        Assertions.assertNotNull(balanceResponse.getAccountBalance());
    }

    @Test
    void shouldExecuteValidationAndNoOperationWhenPinInvalid() {
        //Given
        BalanceRequest request = new BalanceRequest().account("123456789")
                .PIN("1234");
        BalanceResponse response = new BalanceResponse().account("123456789")
                .accountBalance(10);

        //When
        Mockito.doThrow(new AtmMachineGlobalException(ErrorCode.PIN_VALIDATION_FAILED, "Invalid pin/account."))
                .when(validator).validatePin(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
        Mockito.when(dataProvider.computeBalance(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(10L);
        Mockito.when(mapper.balanceToResponse(ArgumentMatchers.any(),
                ArgumentMatchers.anyLong())).thenReturn(response);

        //Then
        AtmMachineGlobalException globalException = Assertions.assertThrows(AtmMachineGlobalException.class,
                () -> useCase.execute(request));
        Assertions.assertEquals("Invalid pin/account.",
                globalException.getMessage());
        Mockito.verifyNoInteractions(mapper);
    }
}