package edu.uom.currencymanager.currencies;
import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencyTest {

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
    }

    @Test
    public void TestCurrency() {

        //Verify
        assertTrue(curr.code.equals("LIR")
                && curr.name.equals("Maltese Lira")
                && curr.major == true
        );

    }

    @Test
    public void TestFromString() throws Exception {

        //Setup
        String str = "LIR,Maltese Lira,yes";

        //Exercise
        Currency lir = Currency.fromString(str);

        //Verify
        assertTrue(curr.code.equals(lir.code)
                && curr.name.equals(lir.name)
                && curr.major == lir.major
        );

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
