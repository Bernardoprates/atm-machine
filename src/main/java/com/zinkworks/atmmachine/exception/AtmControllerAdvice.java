package com.zinkworks.atmmachine.exception;

import com.zinkworks.atmmachine.domain.Error;
import com.zinkworks.atmmachine.mapper.AtmErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class AtmControllerAdvice {

    private final AtmErrorMapper errorMapper;

    @ExceptionHandler(value
            = { AtmMachineGlobalException.class })
    protected ResponseEntity<Object> handleConflict(
            AtmMachineGlobalException ex, WebRequest request) {
        Error bodyOfResponse = errorMapper.decodeException(ex);
        HttpStatus status = ex.getStatus();
        return ResponseEntity.status(status).body(bodyOfResponse);
    }

}
