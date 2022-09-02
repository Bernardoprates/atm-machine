package com.zinkworks.atmmachine.dataprovider;

import com.zinkworks.atmmachine.exception.AtmMachineGlobalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineDataProviderTest {

    private static final long TOTAL_AT_START = 10 * 50 + 30 * 20 + 30 * 10 + 20 * 5;

    private MachineDataProvider machineDataProvider;

    @BeforeEach
    void setUp() {
        //reset slots for each test
        machineDataProvider = new MachineDataProvider();
    }

    @Test
    void shouldNotComputeWithdrawalAmountInvalid() {
        //Given
        long testAmount = -1;

        //Then
        AtmMachineGlobalException exception = Assertions.assertThrows(AtmMachineGlobalException.class,
                () -> machineDataProvider.computeWithdrawal(testAmount));
        Assertions.assertEquals("Please specify a proper amount.", exception.getMessage());
    }

    @Test
    void shouldRetrieveZero() {
        //Given
        long expectedAmount = 0;

        //When
        machineDataProvider.computeWithdrawal(TOTAL_AT_START);
        long retrievedTotal = machineDataProvider.retrieveTotalAmount();

        //Then
        Assertions.assertEquals(expectedAmount, retrievedTotal);
    }


    @Test
    void shouldRetrieveInitialTotalAmount() {
        //Given
        long totalAmountAtStart = TOTAL_AT_START;

        //When
        long retrievedTotal = machineDataProvider.retrieveTotalAmount();

        //Then
        Assertions.assertEquals(totalAmountAtStart, retrievedTotal);
    }

    @Test
    void shouldReflectComputation() {
        //Given
        long totalAmountAfterComputation = TOTAL_AT_START - 50;

        //When
        machineDataProvider.computeWithdrawal(50);
        long totalAmount = machineDataProvider.retrieveTotalAmount();

        //Then
        Assertions.assertEquals(totalAmountAfterComputation, totalAmount);
    }

}