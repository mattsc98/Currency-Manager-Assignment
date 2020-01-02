package edu.uom.currencymanager.currencyserver;

import java.util.Random;

public class DefaultCurrencyServer implements CurrencyServer {

    int seed;

    public double getExchangeRate(String sourceCurrency, String destinationCurrency) {
        Random random = new Random(seed);
        //random.setSeed(20);
        return random.nextDouble()*1.5; //Generate a random number between 0 and 1.5;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

}
