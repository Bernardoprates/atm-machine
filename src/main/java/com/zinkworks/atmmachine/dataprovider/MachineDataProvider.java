package com.zinkworks.atmmachine.dataprovider;

import com.zinkworks.atmmachine.constants.ErrorCode;
import com.zinkworks.atmmachine.domain.Bill;
import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static com.zinkworks.atmmachine.domain.Bill.*;

@Service
public class MachineDataProvider {

    private final Map<Bill, Integer> slots = new EnumMap<>(Map.of(FIFTY, 10, TWENTY, 30, TEN, 30, FIVE, 20));

    private static final Comparator<Bill> BILL_COMPARATOR = (b1, b2) -> b1.getAmount() - b2.getAmount();

    public void computeWithdrawal(long amount) {
        validate(amount);
        Map<Bill, Integer> wallet = new HashMap<>();
        long remainingAmount = amount;
        int billsToRetrieve = 0;
        boolean amountIsNotMet = true;
        Bill highestBill = maxBill();
        while (amountIsNotMet) {
            if(highestBill == ZERO) {
                throw new AtmMachineGlobalException(ErrorCode.ATM_OUT_OF_FUNDS,
                        "ATM machine is not able to meet your request for a withdrawal, due to lack of funds.");
            }
            billsToRetrieve = (int) (remainingAmount/highestBill.getAmount());
            remainingAmount %= highestBill.getAmount();
            amountIsNotMet &= (remainingAmount != 0);
            wallet.put(highestBill, billsToRetrieve);
            highestBill = findHighestBill(remainingAmount, highestBill);
        }
        wallet.entrySet().forEach(entry -> slots.put(entry.getKey(), slots.get(entry.getKey()) - entry.getValue()));
    }

    private void validate(long amount) {
        if (amount <= 0) {
            throw new AtmMachineGlobalException(ErrorCode.AMOUNT_INVALID, "Please specify a proper amount.");
        }
        if(amount > retrieveTotalAmount()) {
            throw new AtmMachineGlobalException(ErrorCode.ATM_OUT_OF_FUNDS,
                    "ATM machine is not able to meet your request for a withdrawal, due to lack of funds.");
        }
    }

    private Bill maxBill() {
        return slots.keySet()
                .stream()
                .filter(key -> slots.get(key) > 0)
                .max(BILL_COMPARATOR)
                .orElse(ZERO);
    }

    private Bill findHighestBill(long amount, Bill bill) {
        return slots.keySet().stream()
                .filter(key -> key.getAmount() <= bill.getAmount())
                .filter(key -> (slots.get(key) > 0) )
                .filter(key -> key.getAmount() <= amount)
                .max(BILL_COMPARATOR).orElse(ZERO);
    }

    public long retrieveTotalAmount() {
        return slots.entrySet().stream().map(entry ->
                entry.getKey().getAmount() * entry.getValue().longValue())
                .reduce(0L, Long::sum);
    }
}
