package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ExchangeRateTest {

    CurrencyDatabase currDB;
    Currency curr, currZ;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
        currZ = null;
    }

    @Test
    public void TestExchangeRate() throws Exception {

        //Setup
        currDB.addCurrency(curr);
        currDB.addCurrency(currZ);
        ExchangeRate exRate = new ExchangeRate(curr, currZ, 4.79);

        //Verify
        assertTrue(exRate.sourceCurrency.code.equals("LIR")
                && exRate.destinationCurrency.code.equals("ZEN")
                && exRate.rate == 4.79
        );

        //Teardown
        currDB.deleteCurrency("LIR");
        currDB.deleteCurrency("ZEN");

    }

    @Test
    public void TestToString() throws Exception {

        //Setup
        currDB.addCurrency(curr);
        currDB.addCurrency(currZ);

        //Exercise
        ExchangeRate testRate = currDB.getExchangeRate("LIR", "ZEN");
        testRate.rate = 4.79;

        //Verify
        assertEquals("LIR 1 = ZEN " + testRate.rate + "", testRate.toString());

        //Teardown
        currDB.deleteCurrency("LIR");
        currDB.deleteCurrency("ZEN");
    }

}
