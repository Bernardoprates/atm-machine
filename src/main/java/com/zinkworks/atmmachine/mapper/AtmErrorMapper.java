package com.zinkworks.atmmachine.mapper;

import com.zinkworks.atmmachine.domain.Error;
import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AtmErrorMapper {

    Error decodeException(AtmMachineGlobalException exception);
}
