package com.zinkworks.atmmachine.mapper;

import com.zinkworks.atmmachine.domain.WithdrawalRequest;
import com.zinkworks.atmmachine.domain.WithdrawalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WithdrawalMapper {

    @Mapping(target = "sumWithdrawn", source = "request.amount")
    @Mapping(target = "account", source = "request.account")
    @Mapping(target = "remainingBalance", source = "remainingBalance")
    WithdrawalResponse mapToResponse(WithdrawalRequest request, long remainingBalance);
}
