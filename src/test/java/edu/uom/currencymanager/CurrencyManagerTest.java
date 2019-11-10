package edu.uom.currencymanager;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CurrencyManagerTest {


    CurrencyDatabase currDB;
    Currency curr, currZ;
    CurrencyManager currMan;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
        currMan = new CurrencyManager();

    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
        currZ = null;
        currMan = null;
    }


    @Test
    public void TestGetMajorCurrencyRates() throws Exception {

        //Setup
        List<ExchangeRate> result =  currMan.getMajorCurrencyRates();
        currMan.addCurrency("LIR", "Maltese Lira", true);

        //Exercise
        List<ExchangeRate> testResult =  currMan.getMajorCurrencyRates();

        //Verify
        assertTrue(testResult.size() > result.size());

        //Teardown
        currMan.deleteCurrencyWithCode("LIR");

    }

//    @Test
//    public void TestGetMajorCurrencyRates_NoMajorCurrencies() {
//
//        //Setup
//        //List with no major currencies must be created
//        List<Currency> currencies = new ArrayList<Currency>() {{
//            add(new Currency("AAA", "A", false));
//            add(new Currency("BBB", "B", false));
//            add(new Currency("CCC", "C", false));
//        }};
//        currDB.currencies = currencies;
//
//        //Exercise
//        List<Currency> result = currDB.getMajorCurrencies();
//
//        //Verify
//        assertTrue(result.isEmpty());
//
//    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencySource() {

        //Exercise
        try {
            //currDB.getExchangeRate("AAA", "LIR");
            currMan.getExchangeRate("AAA", "USD");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }
    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencyDestination() {

        //Exercise
        try {
            //currDB.getExchangeRate("AAA", "LIR");
            currMan.getExchangeRate("USD", "AAA");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrency() throws Exception {

        //Exercise
        currMan.addCurrency("LIR", "Maltese Lira", true);

        //Verify
        assertTrue(currMan.currencyDatabase.currencyExists("LIR"));

        //Teardown
        currMan.deleteCurrencyWithCode("LIR");

    }

    @Test
    public void TestDeleteCurrencyWithCode() throws Exception {

        //Setup
        currMan.addCurrency("LIR", "Maltese Lira", true);;

        //Exercise
        currMan.deleteCurrencyWithCode("LIR");

        //Verify
        assertFalse(currMan.currencyDatabase.currencyExists("LIR"));

    }

//    @Test
//    public void TestMain_Menu() {
//
//        //Setup
//        boolean exit = false;
//
//    }
}
