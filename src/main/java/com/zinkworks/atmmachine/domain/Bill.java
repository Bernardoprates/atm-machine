package com.zinkworks.atmmachine.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Bill {
    ZERO(0),
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50);
    private final int amount;

}
