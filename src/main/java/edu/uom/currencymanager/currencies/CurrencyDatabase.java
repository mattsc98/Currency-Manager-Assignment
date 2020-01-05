package edu.uom.currencymanager.currencies;

import edu.uom.currencymanager.currencyserver.CurrencyServer;
import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CurrencyDatabase {

    //private final DatabaseReadWrite databaseReadWrite;
    CurrencyServer currencyServer;
    List<Currency> currencies = new ArrayList<Currency>();
    HashMap<String, ExchangeRate> exchangeRates = new HashMap<String, ExchangeRate>();

    String currenciesFile = "target" + File.separator + "classes" + File.separator + "currencies.txt";

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setCurrencyServer(CurrencyServer currencyServer) {
        this.currencyServer = currencyServer;
    }

    public CurrencyDatabase() throws Exception {
        init();
    }

    public void init() throws Exception {
        //Initialise currency server
        currencyServer = new DefaultCurrencyServer();

        //Read in supported currencies from text file
        BufferedReader reader = new BufferedReader(new FileReader(currenciesFile));

        //skip the first line to avoid header
        String firstLine = reader.readLine();
        if (!firstLine.equals("code,name,major")) {
            throw new Exception("Parsing error when reading currencies file.");
        }

        while (reader.ready()) {
            String  nextLine = reader.readLine();

            //Check if line has 2 commas
//            int numCommas = 0;
//            char[] chars = nextLine.toCharArray();
//            for (char c : chars) {
//                if (c == ',') numCommas++;
//            }
            int numCommas = checkIfLineHasTwoCommas(nextLine);

//            if (numCommas != 2) {
//                throw new Exception("Parsing error: expected two commas in line " + nextLine);
//            }

            nextLine = checkForTwoCommas(nextLine, numCommas);

            Currency currency = Currency.fromString(nextLine);

//            if (currency.code.length() == 3) {
//                if (!currencyExists(currency.code)) {
//                    currencies.add(currency);
//                }
//            } else {
//                System.err.println("Invalid currency code detected: " + currency.code);
//            }

            String msg = checkCurrCode(currency);
            if(!msg.equals("Successfully Added")) System.err.println(msg);
        }
    }

    public int checkIfLineHasTwoCommas(String nextLine) {

        int numCommas = 0;
        char[] chars = nextLine.toCharArray();

        for (char c : chars) {
            if (c == ',') numCommas++;
        }

        return numCommas;
    }

    public String checkForTwoCommas(String nextLine, int numCommas) throws Exception {

        if (numCommas != 2) {
            throw new Exception("Parsing error: expected two commas in line " + nextLine);
        }

        return nextLine;
    }

    public String checkCurrCode(Currency currency) {

        if (currency.code.length() == 3) {

            if (!currencyExists(currency.code)) {
                currencies.add(currency);
                return "Successfully Added";
            }
            else {
                //System.err.println("The Currency Already Exists: " + currency.code);
                return "The Currency Already Exists";
            }

        }
        else {
            //System.err.println("Invalid currency code detected: " + currency.code);
            return "Invalid currency code detected: " + currency.code;
        }

    }

    //AAAAAAAAAAAAA
    public Currency getCurrencyByCode(String code) {

        for (Currency currency : currencies) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }

        return null;
    }

    //AAAAAAAAAAAAA
    public boolean currencyExists(String code) {
        return getCurrencyByCode(code) != null;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public List<Currency> getMajorCurrencies() {
        List<Currency> result = new ArrayList<Currency>();

        for (Currency currency : currencies) {
            if (currency.major) {
                result.add(currency);
            }
        }

        return result;
    }

    public ExchangeRate getExchangeRate(String sourceCurrencyCode, String destinationCurrencyCode) throws  Exception {
        long FIVE_MINUTES_IN_MILLIS = 300000;  //5*60*100

        ExchangeRate result = null;

        Currency sourceCurrency = getCurrencyByCode(sourceCurrencyCode);
        if (sourceCurrency == null) {
            throw new Exception("Unkown currency: " + sourceCurrencyCode);
        }

        Currency destinationCurrency = getCurrencyByCode(destinationCurrencyCode);
        if (destinationCurrency == null) {
            throw new Exception("Unkown currency: " + destinationCurrencyCode);
        }

        //Check if exchange rate exists in database
        String key = sourceCurrencyCode + destinationCurrencyCode;
        if (exchangeRates.containsKey(key)) {
            result = exchangeRates.get(key);
            if (System.currentTimeMillis() - result.timeLastChecked > FIVE_MINUTES_IN_MILLIS) {
                result = null;
            }
        }

        if (result == null) {
            double rate = currencyServer.getExchangeRate(sourceCurrencyCode, destinationCurrencyCode);
            currencyServer.setSeed(new Random().nextInt());
            result = new ExchangeRate(sourceCurrency,destinationCurrency, rate);

            //Cache exchange rate
            exchangeRates.put(key, result);

            //Cache inverse exchange rate
            String inverseKey = destinationCurrencyCode+sourceCurrencyCode;
            exchangeRates.put(inverseKey, new ExchangeRate(destinationCurrency, sourceCurrency, 1/rate));
        }

        return result;
    }

    public void addCurrency(Currency currency) throws Exception {

        //Save to list
        currencies.add(currency);

        //Persist
        persistCheck(currenciesFile);
    }

    public void deleteCurrency(String code) throws Exception {

        //Save to list
        currencies.remove(getCurrencyByCode(code));

        //Persist
        persistCheck(currenciesFile);
    }

    public void persistCheck(String file) {
        try {
            //Persist list
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            persist(writer);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void persist(BufferedWriter writer) throws Exception {

        writer.write("code,name,major\n");
        for (Currency currency : currencies) {
            writer.write(currency.code + "," + currency.name + "," + (currency.major ? "yes" : "no"));
            writer.newLine();
        }

        writer.flush();
        writer.close();
    }


}
