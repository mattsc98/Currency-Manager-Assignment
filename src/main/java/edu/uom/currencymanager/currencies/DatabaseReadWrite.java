//package edu.uom.currencymanager.currencies;
//
//import edu.uom.currencymanager.currencyserver.CurrencyServer;
//import edu.uom.currencymanager.currencyserver.DefaultCurrencyServer;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseReadWrite {
//
//    CurrencyServer currencyServer;
//    List<Currency> currencies = new ArrayList<Currency>();
//
//    String currenciesFile = "target" + File.separator + "classes" + File.separator + "currencies.txt";
//
//    public DatabaseReadWrite() throws Exception {
//        init();
//    }
//
//    public void init() throws Exception {
//        //Initialise currency server
//        currencyServer = new DefaultCurrencyServer();
//
//        //Read in supported currencies from text file
//        BufferedReader reader = new BufferedReader(new FileReader(currenciesFile));
//
//        //skip the first line to avoid header
//        String firstLine = reader.readLine();
//        if (!firstLine.equals("code,name,major")) {
//            throw new Exception("Parsing error when reading currencies file.");
//        }
//
//        while (reader.ready()) {
//            String  nextLine = reader.readLine();
//
//            //Check if line has 2 commas
//            int numCommas = 0;
//            char[] chars = nextLine.toCharArray();
//            for (char c : chars) {
//                if (c == ',') numCommas++;
//            }
//            //int numCommas = checkIfLineHasTwoCommas(nextLine);
//
//            if (numCommas != 2) {
//                throw new Exception("Parsing error: expected two commas in line " + nextLine);
//            }
//
//            //checkForTwoCommas(nextLine, numCommas);
//
//            Currency currency = Currency.fromString(nextLine);
//
//            if (currency.code.length() == 3) {
//                if (!CurrencyCheck.currencyExists(currency.code)) {
//                    currencies.add(currency);
//                }
//            } else {
//                System.err.println("Invalid currency code detected: " + currency.code);
//            }
//            //checkCurrCode(currency);
//        }
//    }
//
//}
