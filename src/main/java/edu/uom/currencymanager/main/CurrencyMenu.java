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

    public void currMenu() throws Exception {

        //CurrencyManager manager = new CurrencyManager();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nMain Menu\n---------\n");

            System.out.println("1. List currencies");
            System.out.println("2. List exchange rates between major currencies");
            System.out.println("3. Check exchange rate");
            System.out.println("4. Add currency");
            System.out.println("5. Delete currency");
            System.out.println("0. Quit");

            System.out.print("\nEnter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    System.out.println("\nAvailable Currencies\n--------------------");
                    listCurrencies();
                    break;
                case 2:
                    System.out.println("\nMajor Currency Exchange Rates\n-----------------------------");
                    listExchangeRates();
                    break;
                case 3:
                    System.out.println("\nChecking Exchange Rates\n-----------------------------");
                    checkExchangeRateInput();
                    break;
                case 4:
                    System.out.println("\nAdding a New Currency\n-----------------------------");
                    addNewCurrency();
                    break;
                case 5:
                    System.out.println("\nDeleting a Currency\n-----------------------------");
                    deleteACurrency();
                    break;
            }
            Thread.sleep(1000);
        }
    }

    //-----case 1-----//
    public void listCurrencies() {
        List<Currency> currencies = currencyDatabase.getCurrencies();
        for (Currency currency : currencies) {
            System.out.println(currency.toString());
        }
    }


    //-----case 2-----//
    public void listExchangeRates() throws Exception {
        List<ExchangeRate> exchangeRates = getMajorCurrencyRates();
        for (ExchangeRate rate : exchangeRates) {
            System.out.println(rate.toString());
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
    public void checkExchangeRateInput() {
        System.out.print("\nEnter source currency code (e.g. EUR): ");
        String src = sc.next().toUpperCase();
        System.out.print("\nEnter destination currency code (e.g. GBP): ");
        String dst = sc.next().toUpperCase();
        checkExchangeRateCalc(src, dst);
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
    public void addNewCurrency() {
        System.out.print("\nEnter the currency code: ");
        String code = sc.next().toUpperCase();
        System.out.print("Enter currency name: ");
        String name = sc.next();
        name += sc.nextLine();

        String major = "\n";
        while (!(major.equalsIgnoreCase("y") || major.equalsIgnoreCase("n"))) {
            System.out.println("Is this a major currency? [y/n]");
            major = sc.next();
        }

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
    public void deleteACurrency() {
        System.out.print("\nEnter the currency code: ");
        String code = sc.next().toUpperCase();
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
