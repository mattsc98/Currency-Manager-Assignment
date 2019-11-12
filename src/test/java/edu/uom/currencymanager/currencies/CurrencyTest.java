package edu.uom.currencymanager.currencies;
import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencyTest {

    //Lira
    final String CODE = "LIR";
    final String NAME = "Maltese Lira";
    final boolean MAJOR = true;

    //Zeni
    final String CODE_Z = "ZEN";
    final String NAME_Z = "Zeni";
    final boolean MAJOR_Z = false;

    CurrencyDatabase currDB;
    Currency curr, currZ;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency(CODE, NAME, MAJOR);
        currZ = new Currency(CODE_Z, NAME_Z, MAJOR_Z);
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

        //Exercise
        String testCurr = curr.toString();

        //Verify
        assertEquals("LIR - Maltese Lira", testCurr);

        //Teardown
        currDB.deleteCurrency("LIR");

    }
}
