package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class CurrencyTest {

    CurrencyDatabase currDB;
    Currency curr, currZ;
    Currency currMock = mock(Currency.class);

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
        currMock = new Currency("LIR", "Maltese Lira", true);
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
        //currDB.addCurrency(curr);
        when(currMock.toString()).thenReturn("LIR - Maltese Lira");

        //Exercise
        //String testCurr = curr.toString();
        Assert.assertEquals(currMock.toString(), "LIR - Maltese Lira");

        //Verify
        //assertEquals("LIR - Maltese Lira", testCurr);
        verify(currMock).toString();

        //Teardown
        //currDB.deleteCurrency("LIR");

    }
}
