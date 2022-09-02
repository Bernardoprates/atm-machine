package com.zinkworks.atmmachine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Data
@Document(collection = "account")
public class Account {
    @Id
    @JsonProperty("_id")
    private String id;
    private String accountNumber;
    private String PIN;
    private BigDecimal balance;
    private BigDecimal overdraft;
}
