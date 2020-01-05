package edu.uom.currencymanager.currencyserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DefaultCurrencyServerTest {

    DefaultCurrencyServer defaultCurrencyServer;

    @Before
    public void setup() {
        defaultCurrencyServer = new DefaultCurrencyServer();
    }

    @After
    public void teardown() {
        defaultCurrencyServer = null;
    }

    @Test
    public void TestGetExchangeRate() {
        //Setup
        boolean flag;
        String src = "a";
        String dst = "b";

        //Exercise
        double test = defaultCurrencyServer.getExchangeRate(src, dst);
        flag = test >= 0 && test <= 1.5;    //if(test >= 0 && test <= 1.5) flag = true;
                                            //else flag = false;

        //Verify
        assertTrue(flag);
    }
}
