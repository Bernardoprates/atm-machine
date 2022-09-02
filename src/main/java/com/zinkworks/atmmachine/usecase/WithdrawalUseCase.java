package com.zinkworks.atmmachine.usecase;

import com.zinkworks.atmmachine.dataprovider.AccountDataProvider;
import com.zinkworks.atmmachine.dataprovider.MachineDataProvider;
import com.zinkworks.atmmachine.dataprovider.validator.AccountValidator;
import com.zinkworks.atmmachine.domain.WithdrawalRequest;
import com.zinkworks.atmmachine.domain.WithdrawalResponse;
import com.zinkworks.atmmachine.mapper.WithdrawalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WithdrawalUseCase {

    private final AccountValidator validator;

    private final MachineDataProvider machineDataProvider;

    private final AccountDataProvider accountDataProvider;

    private final WithdrawalMapper mapper;

    public WithdrawalResponse execute(WithdrawalRequest request) {
        validator.validatePin(request.getAccount(), request.getPIN());
        validator.validateBalance(request.getAccount(), request.getAmount());
        machineDataProvider.computeWithdrawal(request.getAmount());
        long remainingBalance = accountDataProvider.computeWithdrawal(request.getAccount(), request.getAmount());
        return mapper.mapToResponse(request, remainingBalance);
    }
}
