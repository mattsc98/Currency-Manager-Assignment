package edu.uom.currencymanager.currencies;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTest {


    @Test
    public void TestFormatAmount() {

        //Setup
        double testAmount = 9888777.654;

        //Exercise
        String testFormat = Util.formatAmount(testAmount);

        //Verify
        assertEquals(testFormat, "9,888,777.65");
    }
}
