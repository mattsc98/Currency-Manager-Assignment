package edu.oum.currencymanager.currencies;
import edu.uom.currencymanager.currencies.Currency;
import org.junit.After;
import org.junit.Before;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import static org.junit.Assert.*;

import org.junit.Test;

public class CurrencyDatabaseTest {

    CurrencyDatabase currDB;
    Currency curr;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);

    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
    }

    @Test
    public void TestAddCurrency() {

        //Exercise
        try {
            currDB.addCurrency(curr);
        }catch(Exception e) {};

        //Verify
        assertTrue(currDB.currencyExists("LIR"));

        //Teardown
        try {
            currDB.deleteCurrency("LIR");
        }catch (Exception e) {};

    }

    @Test
    public void TestDeleteCurrency() {

        try {
            //Setup
            currDB.addCurrency(curr);

            //Exercise
            currDB.deleteCurrency("LIR");
        }catch (Exception e) {};

        //Verify
        assertFalse(currDB.currencyExists("LIR"));

    }

    @Test
    public void TestGetCurrencyByCode() throws Exception {

        //Exercise
        currDB.addCurrency(curr);


        //Verify
    }



}
