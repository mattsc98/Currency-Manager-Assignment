package edu.uom.currencymanager.currencies;
import edu.uom.currencymanager.currencies.Currency;
import org.junit.After;
import org.junit.Before;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class CurrencyDatabaseTest {

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

    @Mock
    CurrencyDatabase databaseMock;



    @Test
    public void TestAddCurrency() throws Exception {

        //Exercise
        currDB.addCurrency(curr);

        //Verify
        assertTrue(currDB.currencyExists("LIR"));

        //Teardown
        currDB.deleteCurrency("LIR");

    }

    @Test
    public void TestDeleteCurrency() throws Exception {

        //Setup
        currDB.addCurrency(curr);

        //Exercise
        currDB.deleteCurrency("LIR");

        //Verify
        assertFalse(currDB.currencyExists("LIR"));

    }

    @Test
    public void TestGetCurrencyByCode() throws Exception {

        //Exercise
        currDB.addCurrency(curr);
        Currency retrievedCurr = currDB.getCurrencyByCode("LIR");

        //Verify
        assertEquals("LIR", retrievedCurr.code);

        //Teardown
        currDB.deleteCurrency("LIR");

    }

    @Test
    public void TestCurrencyExists() throws Exception {

        //Exercise
        currDB.addCurrency(curr);

        //Verify
        assertTrue(currDB.currencyExists("LIR"));

        //Teardown
        currDB.deleteCurrency("LIR");
    }

    @Test
    public void TestGetCurrencies() {

        //Setup
        List<Currency> currencies = new ArrayList<Currency>() {{
            add(new Currency("AAA", "A", true));
            add(new Currency("BBB", "B", true));
            add(new Currency("CCC", "C", true));
        }};
        currDB.currencies = currencies;

        //Exercise
        List<Currency> result = currDB.getCurrencies();

        //Verify
        assertEquals(currencies, result);

        //Teardown
        currencies = null;
    }

    @Test
    public void TestGetMajorCurrencies() throws Exception {

        //Setup
        currDB.addCurrency(curr);

        //Exercise
        List<Currency> result =  currDB.getMajorCurrencies();

        //Verify
        assertEquals(4, result.size());

        //Teardown
        currDB.deleteCurrency("LIR");
        result = null;

    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencySource() throws Exception {

        //Setup
        //currDB.addCurrency(curr);

        //Exercise
        try {
            //currDB.getExchangeRate("AAA", "LIR");
            currDB.getExchangeRate("AAA", "USD");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

        //Teardown
        //currDB.deleteCurrency("LIR");
    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencyDestination() throws Exception {

        //Setup
        //currDB.addCurrency(curr);

        //Exercise
        try {
            //currDB.getExchangeRate("LIR", "AAA");
            currDB.getExchangeRate("USD", "AAA");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

        //Teardown
        //currDB.deleteCurrency("LIR");

    }

    @Test
    public void TestGetExchangeRate_UnknownRate() throws Exception {

        //Setup
        currDB.addCurrency(curr);
        currDB.addCurrency(currZ);

        //Exercise
        currDB.getExchangeRate("LIR", "ZEN");

        //Verify
        //assertEquals();


    }


}
