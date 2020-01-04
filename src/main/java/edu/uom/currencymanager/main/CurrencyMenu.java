package edu.uom.currencymanager.main;

import edu.uom.currencymanager.currencies.Currency;
import edu.uom.currencymanager.currencies.CurrencyDatabase;
import edu.uom.currencymanager.currencies.ExchangeRate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CurrencyMenu {

    CurrencyDatabase currencyDatabase;
    Scanner sc = new Scanner(System.in);

    public CurrencyMenu() throws Exception {
        currencyDatabase = new CurrencyDatabase();
    }

    public void setCurrencyDatabase(CurrencyDatabase currencyDatabase) {
        this.currencyDatabase = currencyDatabase;
    }

    public String currMenu(Scanner sc) throws Exception {

        //CurrencyManager manager = new CurrencyManager();

        boolean exit = false;
        StringBuilder checkInput = new StringBuilder();

        while (!exit) {
            //checkInput = "";
            System.out.println("\nMain Menu\n---------\n");

            String CHOICE1 = "1. List currencies";
            System.out.println(CHOICE1);

            String CHOICE2 = "2. List exchange rates between major currencies";
            System.out.println(CHOICE2);

            String CHOICE3 = "3. Check exchange rate";
            System.out.println(CHOICE3);

            String CHOICE4 = "4. Add currency";
            System.out.println(CHOICE4);

            String CHOICE5 = "5. Delete currency";
            System.out.println(CHOICE5);

            String CHOICE0 = "0. Quit";
            System.out.println(CHOICE0);

            String ENTER = "\nEnter your choice: ";

            int choice = sc.nextInt();
            checkInput.append(CHOICE1).append(CHOICE2).append(CHOICE3).append(CHOICE4).append(CHOICE5).append(CHOICE0).append(ENTER);

            switch (choice) {
                case 0:
                    String CASE0 = "\nExiting Program\n";
                    System.out.println(CASE0);
                    checkInput.append(CASE0);
                    exit = true;
                    break;
                case 1:
                    String CASE1 = "\nAvailable Currencies\n--------------------";
                    System.out.println(CASE1);
                    listCurrencies();
                    checkInput.append(CASE1);
                    break;
                case 2:
                    String CASE2 = "\nMajor Currency Exchange Rates\n-----------------------------";
                    System.out.println(CASE2);
                    listExchangeRates();
                    checkInput.append(CASE2);
                    break;
                case 3:
                    String CASE3 = "\nChecking Exchange Rates\n-----------------------------";
                    System.out.println(CASE3);
                    checkExchangeRateInput(sc);
                    checkInput.append(CASE3);
                    break;
                case 4:
                    String CASE4 = "\nAdding a New Currency\n-----------------------------";
                    System.out.println(CASE4);
                    addNewCurrencyInput(sc);
                    checkInput.append(CASE4);
                    break;
                case 5:
                    String CASE5 = "\nDeleting a Currency\n-----------------------------";
                    System.out.println(CASE5);
                    deleteCurrencyInput(sc);
                    checkInput.append(CASE5);
                    break;
            }
            Thread.sleep(1000);
            //return checkInput;
        }
        return checkInput.toString();
    }

    //-----case 1-----//
    public void listCurrencies() {
        List<Currency> currencies = currencyDatabase.getCurrencies();
      //  List<String> list = new ArrayList<String>(Collections.<String>emptyList());
        for (Currency currency : currencies) {
            System.out.println(currency.toString());
          //  list.add(currency.toString());
        }

      //  return list;
    }


    //-----case 2-----//
    public void listExchangeRates() throws Exception {
        List<ExchangeRate> exchangeRates = getMajorCurrencyRates();
        //List<String> list = new ArrayList<String>(Collections.<String>emptyList());
        for (ExchangeRate rate : exchangeRates) {
            System.out.println(rate.toString());
         //   list.add(rate.toString());
        }


    }

    public List<ExchangeRate> getMajorCurrencyRates() throws Exception {

        List<ExchangeRate> exchangeRates = new ArrayList<ExchangeRate>();

        List<Currency> currencies = currencyDatabase.getMajorCurrencies();

        for (Currency src : currencies) {
            for (Currency dst : currencies) {
                if (src != dst) {
                    exchangeRates.add(currencyDatabase.getExchangeRate(src.code, dst.code));
                }
            }
        }
        return exchangeRates;
    }


    //-----case 3-----//
    public String checkExchangeRateInput(Scanner sc) {
        String checkInput = "";

        String SRC = "\nEnter source currency code (e.g. EUR): ";
        System.out.print(SRC);
        String src = sc.next().toUpperCase();

        String DST = "\nEnter destination currency code (e.g. GBP): ";
        System.out.print(DST);
        String dst = sc.next().toUpperCase();

        checkInput = SRC + src + DST + dst;
        checkExchangeRateCalc(src, dst);
        return checkInput;
    }

    public void checkExchangeRateCalc(String source, String destination) {     //Seperate the try catch to make it more testable

        try {
            ExchangeRate rate = getExchangeRate(source, destination);
            System.out.println(rate.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public ExchangeRate getExchangeRate(String sourceCurrency, String destinationCurrency) throws Exception {
        return currencyDatabase.getExchangeRate(sourceCurrency, destinationCurrency);
    }


    //-----case 4-----//
    public String addNewCurrencyInput(Scanner sc) {
        String checkInput = "";

        String CODE = "\nEnter the currency code: ";
        System.out.print(CODE);
        String code = sc.next().toUpperCase();

        String NAME = "\nEnter currency name: ";
        System.out.print(NAME);
        String name = sc.next();
        //name += sc.nextLine();

        String MAJOR = "\nIs this a major currency? [y/n]";
        String major = "\n";
        while (!(major.equalsIgnoreCase("y") || major.equalsIgnoreCase("n"))) {
            System.out.println(MAJOR);
            major = sc.next();
        }

        checkInput = CODE + code + NAME + name + MAJOR + major;
        addNewCurrencyCheck(code,name,major);
        return checkInput;
    }

    public void addNewCurrencyCheck(String code, String name, String major) {
        try {
            addCurrency(code, name, major.equalsIgnoreCase("y"));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void addCurrency(String code, String name, boolean major) throws Exception {

        //Check format of code
        if (code.length() != 3) {
            throw new Exception("A currency code should have 3 characters.");
        }

        //Check minimum length of name
        if (name.length() < 4) {
            throw new Exception("A currency's name should be at least 4 characters long.");
        }

        //Check if currency already exists
        if (currencyDatabase.currencyExists(code)) {
            throw new Exception("The currency " + code + " already exists.");
        }

        //Add currency to database
        currencyDatabase.addCurrency(new Currency(code,name,major));

    }


    //-----case 5-----//
    public String deleteCurrencyInput(Scanner sc) {
        String checkInput = "";

        String CODE = "\nEnter the currency code: ";
        System.out.print(CODE);
        String code = sc.next().toUpperCase();

        checkInput = CODE;
        deleteCurrencyCheck(code);

        return checkInput;
    }

    public void deleteCurrencyCheck(String code) {
        try {
            deleteCurrencyWithCode(code);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void deleteCurrencyWithCode(String code) throws Exception {

        if (!currencyDatabase.currencyExists(code)) {
            throw new Exception("Currency does not exist: " + code);
        }

        currencyDatabase.deleteCurrency(code);

    }

}
