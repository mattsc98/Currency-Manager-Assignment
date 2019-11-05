package edu.uom.currencymanager.currencyserver;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import org.junit.After;
import org.junit.Before;

public class CurrencyManagerTest {


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




}
