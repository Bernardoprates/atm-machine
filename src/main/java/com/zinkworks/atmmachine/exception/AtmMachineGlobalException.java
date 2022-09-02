package com.zinkworks.atmmachine.exception;

import com.zinkworks.atmmachine.constants.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AtmMachineGlobalException extends RuntimeException {

    private String code;

    private String message;

    private HttpStatus status;

    public AtmMachineGlobalException(ErrorCode errorCode, String message) {
        this.code = errorCode.getValue();
        this.status = errorCode.getStatus();
        this.message = message;
    }

}
