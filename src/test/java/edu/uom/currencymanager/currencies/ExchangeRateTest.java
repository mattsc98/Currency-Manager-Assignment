package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExchangeRateTest {

    Currency curr, currZ;

    @Before
    public void setup()  {
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
    }

    @After
    public void teardown() {
        curr = null;
        currZ = null;
    }

    @Test
    public void TestExchangeRate()  {

        //Setup
        ExchangeRate exRate = new ExchangeRate(curr, currZ, 4.79);

        //Verify
        assertTrue(exRate.sourceCurrency.code.equals("LIR")
                && exRate.destinationCurrency.code.equals("ZEN")
                && exRate.rate == 4.79
        );

    }

    @Test
    public void TestToString()  {

        //Exercise
        ExchangeRate testRate = new ExchangeRate(curr, currZ, 4.79);// = currDB.getExchangeRate("LIR", "ZEN");

        //Verify
        assertEquals("LIR 1 = ZEN " + testRate.rate + "", testRate.toString());

    }

}
