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
        Currency testCurr = currMan.currencyDatabase.getCurrencyByCode("LIR");
        assertEquals("LIR", testCurr.code);
        assertEquals("Maltese Lira", testCurr.name);
        assertEquals(true, testCurr.major);
        //assertTrue(currMan.currencyDatabase.currencyExists("LIR"));

        //Teardown
        currMan.deleteCurrencyWithCode("LIR");

    }

    @Test
    public void TestAddCurrency_CodeOverThree() {

        //Exercise
        try{
            currMan.addCurrency("LIRA", "Maltese Lira", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrency_CodeUnderThree() {

        //Exercise
        try{
            currMan.addCurrency("LI", "Maltese Lira", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrency_MinLength() {

        //Exercise
        try{
            currMan.addCurrency("LIR", "ML", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency's name should be at least 4 characters long.", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrency_CurrExists() throws Exception {

        //Setup
        currMan.addCurrency("LIR", "Maltese Lira", true);

        //Exercise
        try{
            currMan.addCurrency("LIR", "Maltese Lira", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("The currency LIR already exists.", e.getMessage());
        }

        //Teardown
        currMan.deleteCurrencyWithCode("LIR");

    }

    @Test
    public void TestDeleteCurrencyWithCode() throws Exception {

        //Setup
        currMan.addCurrency("LIR", "Maltese Lira", true);

        //Exercise
        currMan.deleteCurrencyWithCode("LIR");

        //Verify
        assertFalse(currMan.currencyDatabase.currencyExists("LIR"));

    }

    @Test
    public void TestDeleteCurrencyWithCode_NoExist() throws Exception {

        //Exercise
        try {
            currMan.deleteCurrencyWithCode("LIR");
        }

        //Verify
        catch(Exception e) {
            assertEquals("Currency does not exist: LIR", e.getMessage());
        }


    }

//    @Test
//    public void TestMain_Menu() {
//
//        //Setup
//        boolean exit = false;
//
//    }
}
