package com.zinkworks.atmmachine.resource;

import com.zinkworks.atmmachine.domain.BalanceRequest;
import com.zinkworks.atmmachine.domain.BalanceResponse;
import com.zinkworks.atmmachine.usecase.BalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceResource implements BalanceApi{

    private final BalanceUseCase useCase;
    @Override
    public ResponseEntity<BalanceResponse> balanceGet(String authorization, String account, String PIN) {
        BalanceRequest balanceRequest = new BalanceRequest().account(account).PIN(PIN);
        BalanceResponse balanceResponse = useCase.execute(balanceRequest);
        return ResponseEntity.ok(balanceResponse);
    }
}
