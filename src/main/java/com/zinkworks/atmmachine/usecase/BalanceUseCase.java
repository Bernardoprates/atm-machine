package com.zinkworks.atmmachine.usecase;

import com.zinkworks.atmmachine.dataprovider.AccountDataProvider;
import com.zinkworks.atmmachine.dataprovider.validator.AccountValidator;
import com.zinkworks.atmmachine.domain.BalanceRequest;
import com.zinkworks.atmmachine.domain.BalanceResponse;
import com.zinkworks.atmmachine.mapper.BalanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceUseCase {

    private final AccountValidator validator;

    private final AccountDataProvider dataProvider;

    private final BalanceMapper mapper;

    public BalanceResponse execute(BalanceRequest request) {
        validator.validatePin(request.getAccount(),
                request.getPIN());
        long balance = dataProvider.computeBalance(request.getAccount(),
                request.getPIN());
        return mapper.balanceToResponse(request, balance);
    }
}
