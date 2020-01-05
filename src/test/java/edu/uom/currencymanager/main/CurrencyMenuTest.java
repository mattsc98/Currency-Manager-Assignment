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
    CurrencyMenu currMenu;

    String menu = "1. List currencies" +
            "2. List exchange rates between major " +
            "currencies3" +
            ". Check exchange rate" +
            "4. Add currency" +
            "5. Delete currency" +
            "0. Quit\n" +
            "Enter your choice: ";

    @Mock
    CurrencyDatabase currDBMock;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
        currMenu = new CurrencyMenu();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {
        currDB = null;
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

    @Test
    public void TestGetExchangeRate_UnknownCurrencySource()  {

        //Setup
//        currDB.addCurrency(curr);

        //Exercise
        try {
            currMenu.getExchangeRate("AAA", "GBP");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

        //Teardown
//        currDB.deleteCurrency("LIR");
    }

    @Test
    public void TestGetExchangeRate_UnknownCurrencyDestination()  {

        //Setup
//        currDB.addCurrency(curr);

        //Exercise
        try {
            currMenu.getExchangeRate("GBP", "AAA");
        } catch (Exception e) {
            assertEquals("Unkown currency: AAA", e.getMessage());
        }

        //Teardown
//        currDB.deleteCurrency("LIR");
    }

    @Test
    public void TestAddCurrency() throws Exception {

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

    }

    @Test
    public void TestAddCurrency_CurrExists() throws Exception {

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

    @Test
    public void TestCheckExchangeRateCalc()  {

        //Setup
        String src = "AAA";
        String dst = "BBB";

        //Exercise
        try {
            currMenu.checkExchangeRateCalc(src, dst);
        }
        //Verify
        catch (Exception e) {
            assertEquals("", e.getMessage());
        }

    }

    @Test
    public void TestAddNewCurrencyInput() throws Exception {

        //Setup
        String code = "LIR";
        String name = "Lira";
        String major = "Y";

        ByteArrayInputStream in = new ByteArrayInputStream((code + " " + name + " " + major).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.addNewCurrencyInput(sc);

        //Verify
        assertEquals(("\nEnter the currency code: LIR" +
                "\nEnter currency name: Lira" +
                "\nIs this a major currency? [y/n]Y"), result);

        //Teardown
        currMenu.deleteCurrencyWithCode("LIR");

    }

    @Test
    public void TestAddNewCurrencyCheck() {

        //Setup
        String code = "AAAA";
        String name = "aaaa";
        String major = "Y";

        //Exercise
        try {
            currMenu.addNewCurrencyCheck(code, name, major);
        }
        //Verify
        catch (Exception e) {
            assertEquals("" , e.getMessage());
        }

    }

    @Test
    public void TestDeleteCurrencyInput() throws Exception {

        //Setup
        currMenu.addCurrency("TST", "Test", false);
        String code = "TST";

        ByteArrayInputStream in = new ByteArrayInputStream((code).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.deleteCurrencyInput(sc);

        //Verify
        assertEquals(("\nEnter the currency code: "), result);

    }

    @Test
    public void TestDeleteCurrencyCheck() {

        //Setup
        String code = "TST";

        //Exercise
        try {
            currMenu.deleteCurrencyCheck(code);
        }
        //Verify
        catch (Exception e) {
            assertEquals("" , e.getMessage());
        }

    }

    @Test
    public void TestMenuChoice0() throws Exception {
        //Setup
        String choice = "0";

        ByteArrayInputStream in = new ByteArrayInputStream((choice).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                "\nExiting Program\n")
                , result);
    }

    @Test
    public void TestMenuChoice1() throws Exception {

        //Setup
        String choice = "1";
        String quit = "0";

        ByteArrayInputStream in = new ByteArrayInputStream((choice + " " + quit).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                        "\nAvailable Currencies\n" +
                        "--------------------" +
                        menu +
                        "\nExiting Program\n")
                , result);
    }

    @Test
    public void TestMenuChoice2() throws Exception {

        //Setup
        String choice = "2";
        String quit = "0";

        ByteArrayInputStream in = new ByteArrayInputStream((choice + " " + quit).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                        "\nMajor Currency Exchange Rates\n" +
                        "-----------------------------" +
                        menu +
                        "\nExiting Program\n")
                , result);
    }

    @Test
    public void TestMenuChoice3() throws Exception {

        //Setup
        String choice = "3";
        String quit = "0";
        //feed incorrect data to force return to menu
        String src = "AAAA";
        String dst = "BBBB";

        ByteArrayInputStream in = new ByteArrayInputStream((choice + " " + src + " " + dst + " " + quit).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                        "\nChecking Exchange Rates\n" +
                        "-----------------------------" +
                        menu +
                        "\nExiting Program\n")
                , result);

    }

    @Test
    public void TestMenuCase4() throws Exception {
        //Setup
        String choice = "4";
        String quit = "0";
        //feed incorrect data to force return to menu
        String code = "AAAA";
        String name = "aa";
        String major = "n";

        ByteArrayInputStream in = new ByteArrayInputStream((choice + " " + code + " " +
                name + " " + major + " " + quit).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                        "\nAdding a New Currency\n" +
                        "-----------------------------" +
                        menu +
                        "\nExiting Program\n")
                , result);

    }

    @Test
    public void TestMenuCase5() throws Exception {
        //Setup
        String choice = "5";
        String quit = "0";
        //feed incorrect data to force return to menu
        String code = "AAAA";

        ByteArrayInputStream in = new ByteArrayInputStream((choice + " " + code + " " + quit).getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(in);

        //Exercise
        String result = currMenu.currMenu(sc);

        //Verify
        assertEquals((menu +
                        "\nDeleting a Currency\n" +
                        "-----------------------------" +
                        menu +
                        "\nExiting Program\n")
                , result);

    }
}
