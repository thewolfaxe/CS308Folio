package Tests;

import Model.StockModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockTests {
    public StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
    public StockModel testStockBad = new StockModel("blah", "blah", 1);

    @Test
    public void constructTestGoodParams(){
        Assertions.assertNotNull(testStockGood.refresh());
    }

    @Test
    public void constructTestBadParams(){
        Assertions.assertNull(testStockBad.refresh());
    }

    @Test
    public void sellSharesTestGoodParams(){
        Assertions.assertTrue(testStockGood.sellShares(1));
    }

    @Test
    public void sellSharesTestBadParams(){
        Assertions.assertFalse(testStockGood.sellShares(5));
    }

    @Test
    public void buySharesTest(){
        double firstValue = testStockGood.getValue();
        testStockGood.buyShares(1);
        Assertions.assertTrue(testStockGood.getValue() > firstValue);
    }

    @Test
    public void checkTrendTest(){
        Assertions.assertTrue(testStockGood.getTrend() > 0 || testStockGood.getTrend() == 0 || testStockGood.getTrend() < 0);
    }
}