package edu.oum.currencymanager.currencies;
import org.junit.After;
import org.junit.Before;
import edu.uom.currencymanager.currencies.CurrencyDatabase;

public class CurrencyDatabaseTest {

    CurrencyDatabase currDB;

    @Before
    public void setup() throws Exception {
        currDB = new CurrencyDatabase();
    }

    @After
    public void teardown() {
        currDB = null;
    }

}
