package edu.uom.currencymanager.main;

import java.util.Scanner;

public class CurrencyManager {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        CurrencyMenu currencyMenu = new CurrencyMenu();
        currencyMenu.currMenu(sc);

    }

}
