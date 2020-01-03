package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencyTest {

    CurrencyDatabase currDB;
    Currency curr, currZ;
//    Currency currMock = mock(Currency.class);

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
//        currMock = new Currency("LIR", "Maltese Lira", true);
    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
        currZ = null;
    }

    @Test
    public void TestCurrency() {

        //Verify
        assertTrue(curr.code.equals("LIR")
                && curr.name.equals("Maltese Lira")
                && curr.major
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

        //Exercise
        String testCurr = curr.toString();

        //Verify
        assertEquals("LIR - Maltese Lira", testCurr);
        //verify(currMock).toString();

        //Teardown
        currDB.deleteCurrency("LIR");

    }
}
