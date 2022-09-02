package com.zinkworks.atmmachine.resource;

import com.zinkworks.atmmachine.domain.WithdrawalRequest;
import com.zinkworks.atmmachine.domain.WithdrawalResponse;
import com.zinkworks.atmmachine.usecase.WithdrawalUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WithdrawalResource implements WithdrawApi {

    private final WithdrawalUseCase useCase;

    @Override
    public ResponseEntity<WithdrawalResponse> withdrawPost(String authorization, WithdrawalRequest withdrawal) {
        return ResponseEntity.status(HttpStatus.OK).body(useCase.execute(withdrawal));
    }
}
