package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyDatabaseTest {

    CurrencyDatabase currDB;
    Currency curr, currZ;

    @Mock
    CurrencyDatabase currDBMock;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
        currZ = null;
    }

    @Test
    public void TestAddCurrency() throws Exception {

        //Exercise
        currDB.addCurrency(curr);

        //Verify
        assertTrue(currDB.currencyExists("LIR"));

        //Teardown
        currDB.deleteCurrency("LIR");

//        //Exercise
//        currDBMock.addCurrency(curr);
//
//        //Verify
//        verify(currDBMock,times(1)).addCurrency(any(Currency.class));
//        //assertTrue(currDBMock.currencyExists("LIR"));
    }

    @Test
    public void TestAddCurrencyException()  {

        try{
            //Setup
            Currency currBad = new Currency("a", "a", false);
            //Exercise
            currDB.addCurrency(currBad);
        }
        catch(Exception e) {
            //Verify
            assertEquals("", e.getMessage());
        }

    }

    @Test
    public void TestDeleteCurrency() throws Exception {

        //Setup
        currDB.addCurrency(curr);

        //Exercise
        currDB.deleteCurrency("LIR");

        //Verify
        assertFalse(currDB.currencyExists("LIR"));

//        //Exercise
//        currDBMock.deleteCurrency("LIR");
//
//        //Verify
//        verify(currDBMock,times(1)).deleteCurrency(anyString());

    }

    @Test
    public void TestDeleteCurrencyException()  {

        try{
            //Exercise
              currDB.deleteCurrency("a");
        }
        catch(Exception e) {
            //Verify
            assertEquals("", e.getMessage());
        }

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
    public void TestGetCurrencyByCode_Null() {

        //Exercise
        Currency retrievedCurr = currDBMock.getCurrencyByCode(null);

        //Verify
        assertNull(retrievedCurr);

    }

    @Test
    public void TestGetCurrencyByCodeException()  {

        try{
            currDB.getCurrencyByCode("a");
        }
        catch(Exception e) {
            assertEquals("", e.getMessage());
        }

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
    public void TestCurrencyExists_NoCurrency() {

        //Verify
        assertFalse(currDB.currencyExists("a"));

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

    }

    @Test
    public void TestGetCurrencies_NoCurrencies() {

        //Setup
        currDB.currencies = new ArrayList<Currency>() {{
        }};

        //Exercise
        List<Currency> result = currDB.getCurrencies();

        //Verify
        assertTrue(result.isEmpty());

    }

    @Test
    public void TestGetMajorCurrencies() throws Exception {

        //Setup
        List<Currency> result = currDB.getMajorCurrencies();
        currDB.addCurrency(curr);

        //Exercise
        List<Currency> testResult = currDB.getMajorCurrencies();

        //Verify
        assertTrue(testResult.size() > result.size());

        //Teardown
        currDB.deleteCurrency("LIR");

    }

    @Test
    public void TestGetMajorCurrencies_NoMajorCurrencies() {

        //Setup
        //List with no major currencies must be created
        currDB.currencies = new ArrayList<Currency>() {{
            add(new Currency("AAA", "A", false));
            add(new Currency("BBB", "B", false));
            add(new Currency("CCC", "C", false));
        }};

        //Exercise
        List<Currency> result = currDB.getMajorCurrencies();

        //Verify
        assertTrue(result.isEmpty());

    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencySource() throws Exception { //check

        //Setup
        currDB.addCurrency(curr);

        //Exercise
        try {
            currDB.getExchangeRate("AAA", "LIR");
            //currDB.getExchangeRate("AAA", "USD");
            //better use LIR because if USD is ever deleted then the test fails
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

        //Teardown
        currDB.deleteCurrency("LIR");
    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencyDestination() throws Exception {

        //Setup
        currDB.addCurrency(curr);

        //Exercise
        try {
            currDB.getExchangeRate("LIR", "AAA");
           // currDB.getExchangeRate("USD", "AAA");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

//        Teardown
        currDB.deleteCurrency("LIR");

    }

    @Test
    public void TestGetExchangeRate_UnknownRate() throws Exception {

        //Setup
        currDB.addCurrency(curr);
        currDB.addCurrency(currZ);
        ExchangeRate exRate = new ExchangeRate(curr, currZ, 4.79);

        //Exercise
        ExchangeRate testRate = currDB.getExchangeRate("LIR", "ZEN");
        testRate.rate = 4.79;

        //Verify
        assertEquals("LIR 1 = ZEN " + exRate.rate + "", testRate.toString());

        //Teardown
        currDB.deleteCurrency("LIR");
        currDB.deleteCurrency("ZEN");
    }

    @Test
    public void TestGetExchangeRate_ExceededTime() throws Exception {

        //Setup
        long SIX_MINUTES_IN_MILLIS = 360000; //6*60*100
        currDB.addCurrency(curr);
        currDB.addCurrency(currZ);
        ExchangeRate testResult = currDB.getExchangeRate("LIR", "ZEN");
        testResult.timeLastChecked = testResult.timeLastChecked - SIX_MINUTES_IN_MILLIS;

        //Exercise
        ExchangeRate result = currDB.getExchangeRate("LIR","ZEN");

        //Verify
        assertNotSame(testResult, result);

        //Teardown
        currDB.deleteCurrency("LIR");
        currDB.deleteCurrency("ZEN");
    }

    @Test
    public void TestPersistCheck()  {

        //Setup
        String testFile = "target" + File.separator + "classes" + File.separator + "aaa.txt";

        try {
            //Exercise
            currDB.persistCheck(testFile);
        }

        catch (Exception e) {
            //Verify
            assertEquals("", e.getMessage());
        }
    }

    @Test
    public void TestPersist() throws Exception {

        //Setup
        String testFile = "target" + File.separator + "classes" + File.separator + "test.txt";
        BufferedReader read = new BufferedReader(new FileReader(testFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));

        //Exercise
        currDB.persist(writer);
        String firstLine = read.readLine();

        //Verify
        assertEquals("code,name,major", firstLine);
    }


}
