package edu.uom.currencymanager.main;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class CurrencyMenuTest {


    CurrencyDatabase currDB;
    Currency curr, currZ;
    CurrencyMenu currMenu;

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    @Mock
    CurrencyDatabase currDBMock;
    CurrencyMenu currMenuMock;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        curr = new Currency("LIR", "Maltese Lira", true);
        currZ = new Currency("ZEN", "Zeni", false);
        currMenu = new CurrencyMenu();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {
        currDB = null;
        curr = null;
        currZ = null;
        currMenu = null;
    }


    @Test
    public void TestGetMajorCurrencyRates() throws Exception {

        //Setup
        List<ExchangeRate> result =  currMenu.getMajorCurrencyRates();
        currMenu.addCurrency("LIR", "Maltese Lira", true);

        //Exercise
        List<ExchangeRate> testResult =  currMenu.getMajorCurrencyRates();

        //Verify
        assertTrue(testResult.size() > result.size());

        //Teardown
        currMenu.deleteCurrencyWithCode("LIR");


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
            currMenu.getExchangeRate("AAA", "USD");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }
    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencyDestination() {

        //Exercise
        try {
            //currDB.getExchangeRate("AAA", "LIR");
            currMenu.getExchangeRate("USD", "AAA");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }
    }

    @Test
    public void TestAddCurrency() throws Exception {

//        //Exercise
//        currMenu.addCurrency("LIR", "Maltese Lira", true);
//
//        //Verify
//        Currency testCurr = currMenu.currencyDatabase.getCurrencyByCode("LIR");
//        assertEquals("LIR", testCurr.code);
//        assertEquals("Maltese Lira", testCurr.name);
//        assertTrue(testCurr.major);
//        //assertTrue(currMan.currencyDatabase.currencyExists("LIR"));
//
//        //Teardown
//        currMenu.deleteCurrencyWithCode("LIR");

        //Setup
        currMenu.setCurrencyDatabase(currDBMock);
        String code = "LIR", name = "Maltese Lira";
        doReturn(false).when(currDBMock).currencyExists(anyString());

        //Exercise
        currMenu.addCurrency(code,name,true);

        //Verify
        verify(currDBMock,times(1)).addCurrency(any(Currency.class));

    }

    @Test
    public void TestAddCurrency_CodeOverThree() {

        //Exercise
        try{
            currMenu.addCurrency("LIRA", "Maltese Lira", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
//        try {
//            //Setup
//            currMenu.setCurrencyDatabase(currDBMock);
//            String code = "LIRA", name = "Maltese Lira";
//            doReturn(false).when(currDBMock).currencyExists(anyString());
//
//            //Exercise
//            currMenu.addCurrency(code, name, true);
//        }
//        catch (Exception e){
//            //Verify
//            assertEquals("A currency code should have 3 characters.", e.getMessage());
//        }

    }

    @Test
    public void TestAddCurrency_CodeUnderThree() {

        //Exercise
        try{
            currMenu.addCurrency("LI", "Maltese Lira", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency code should have 3 characters.", e.getMessage());
        }
//        try {
//            //Setup
//            currMenu.setCurrencyDatabase(currDBMock);
//            String code = "LI", name = "Maltese Lira";
//            doReturn(false).when(currDBMock).currencyExists(anyString());
//
//            //Exercise
//            currMenu.addCurrency(code, name, true);
//        }
//        catch (Exception e){
//            //Verify
//            assertEquals("A currency code should have 3 characters.", e.getMessage());
//        }
    }

    @Test
    public void TestAddCurrency_MinLength() {

        //Exercise
        try{
            currMenu.addCurrency("LIR", "ML", true);
        }

        //Verify
        catch (Exception e) {
            assertEquals("A currency's name should be at least 4 characters long.", e.getMessage());
        }
//        try {
//            //Setup
//            currMenu.setCurrencyDatabase(currDBMock);
//            String code = "LIR", name = "ML";
//            doReturn(false).when(currDBMock).currencyExists(anyString());
//
//            //Exercise
//            currMenu.addCurrency(code, name, true);
//        }
//        catch (Exception e){
//            //Verify
//            assertEquals("A currency's name should be at least 4 characters long.", e.getMessage());
//        }
    }

    @Test
    public void TestAddCurrency_CurrExists() throws Exception {

//        //Setup
//        currMenu.addCurrency("LIR", "Maltese Lira", true);
//
//        //Exercise
//        try{
//            currMenu.addCurrency("LIR", "Maltese Lira", true);
//        }
//
//        //Verify
//        catch (Exception e) {
//            assertEquals("The currency LIR already exists.", e.getMessage());
//        }
//
//        //Teardown
//        currMenu.deleteCurrencyWithCode("LIR");

        //Setup
        currMenu.setCurrencyDatabase(currDBMock);
        String code = "LIR", name = "Maltese Lira";
        doReturn(false).when(currDBMock).currencyExists(anyString());
        currMenu.addCurrency(code, name, true);

        try {
            //Exercise
            currMenu.addCurrency(code, name, true);
        }
        catch (Exception e){
            //Verify
            assertEquals("The currency LIR already exists.", e.getMessage());
        }

    }

    @Test
    public void TestDeleteCurrencyWithCode() throws Exception {

//        //Setup
//        currMenu.addCurrency("LIR", "Maltese Lira", true);
//
//        //Exercise
//        currMenu.deleteCurrencyWithCode("LIR");
//
//        //Verify
//        assertFalse(currMenu.currencyDatabase.currencyExists("LIR"));
        //Setup
        currMenu.setCurrencyDatabase(currDBMock);
        String code = "LIR";
        doReturn(true).when(currDBMock).currencyExists(anyString());

        //Exercise
        currMenu.deleteCurrencyWithCode(code);

        //Verify
        verify(currDBMock,times(1)).deleteCurrency(anyString());


    }

    @Test
    public void TestDeleteCurrencyWithCode_NoExist() {

        //Exercise
        try {
            currMenu.deleteCurrencyWithCode("LIR");
        }

        //Verify
        catch(Exception e) {
            assertEquals("Currency does not exist: LIR", e.getMessage());
        }


    }

    @Test
    public void TestListCurrencies()  {

        currMenu.setCurrencyDatabase(currDBMock);
        currMenu.listCurrencies();
        verify(currDBMock,times(1)).getCurrencies();

    }

    @Test
    public void TestListExchangeRates() throws Exception {

        currMenu.setCurrencyDatabase(currDBMock);
        currMenu.listExchangeRates();
        verify(currDBMock,times(1)).getMajorCurrencies();

    }

    @Test
    public void TestCheckExchangeRateInput() {

        //Setup
        String src = "EUR";
        String dst = "GBP";

        ByteArrayInputStream in = new ByteArrayInputStream((src + " " + dst).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.checkExchangeRateInput(sc);

        //Verify
        assertEquals(("\nEnter source currency code (e.g. EUR): "
                + src + "\nEnter destination currency code (e.g. GBP): " + dst), result);

    }
//    public void checkExchangeRateCalc(String source, String destination) {     //Seperate the try catch to make it more testable
//
//        try {
//            ExchangeRate rate = getExchangeRate(source, destination);
//            System.out.println(rate.toString());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
    @Test
    public void TestCheckExchangeRateCalc() throws Exception {

//        String src = "EUR";
//        String dst = "GBP";
//        currMenu.setCurrencyDatabase(currDBMock);
//        currMenu.checkExchangeRateCalc(src, dst);
//        verify(currDBMock,times(1)).getExchangeRate(src, dst);
//        Mockito.when(provider.getCase3()).thenReturn(DEFAULT_SOURCE);
//        Mockito.when(provider.getCase3()).thenReturn(DEFAULT_DESTINATION);

    }
}
