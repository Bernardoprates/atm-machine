package com.zinkworks.atmmachine.mapper;


import com.zinkworks.atmmachine.domain.BalanceRequest;
import com.zinkworks.atmmachine.domain.BalanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BalanceMapper {

    @Mapping(source = "request.account", target = "account")
    BalanceResponse balanceToResponse(BalanceRequest request, long accountBalance);

}
