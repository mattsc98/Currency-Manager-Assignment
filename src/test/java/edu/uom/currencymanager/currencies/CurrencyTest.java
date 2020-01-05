package edu.uom.currencymanager.currencies;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurrencyTest {

    Currency curr;

    @Mock
    CurrencyDatabase currDBMock;

    @Before
    public void setup()  {
        curr = new Currency("LIR", "Maltese Lira", true);
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void teardown() {
        curr = null;
    }

    @Test
    public void TestCurrency() {

        //Verify
        assertTrue(curr.code.equals("LIR")
                && curr.name.equals("Maltese Lira")
                && curr.major
        );

    }

    @Test
    public void TestFromString() throws Exception {

        //Setup
        String str = "LIR,Maltese Lira,yes";

        //Exercise
        Currency lir = Currency.fromString(str);

        //Verify
        assertTrue(curr.code.equals(lir.code)
                && curr.name.equals(lir.name)
                && curr.major == lir.major
        );

    }

    @Test
    public void TestFromStringException()  {

        try{
            //Setup
            String str = "a,a,a";

            //Exercise
           Currency.fromString(str);

        }
        catch(Exception e) {
            //Verify
            assertEquals("", e.getMessage());
        }

    }

    @Test
    public void TestToString() throws Exception {

        //Setup
        currDBMock.addCurrency(curr);

        //Exercise
        String testCurr = curr.toString();

        //Verify
        assertEquals("LIR - Maltese Lira", testCurr);

    }
}
