package com.zinkworks.atmmachine.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    AMOUNT_INVALID("ATM-01", HttpStatus.BAD_REQUEST),
    ATM_OUT_OF_FUNDS("ATM-02", HttpStatus.INTERNAL_SERVER_ERROR),
    INSUFFICIENT_FUNDS("ATM-03", HttpStatus.BAD_REQUEST),
    PIN_VALIDATION_FAILED("ATM-04", HttpStatus.BAD_REQUEST);

    private String value;
    private HttpStatus status;
}
