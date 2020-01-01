//package edu.uom.currencymanager.currencies;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CurrencyCheck {
//
//    List<Currency> currencies = new ArrayList<Currency>();
//
//    public CurrencyCheck() {
//
//    }
//
//    public static boolean currencyExists(String code) {
//        return getCurrencyByCode(code) != null;
//    }
//
//    public static Currency getCurrencyByCode(String code) {
//        List<Currency> currencies = new ArrayList<Currency>();
//        for (Currency currency : currencies) {
//            if (currency.code.equalsIgnoreCase(code)) {
//                return currency;
//            }
//        }
//
//        return null;
//    }
//
//}
