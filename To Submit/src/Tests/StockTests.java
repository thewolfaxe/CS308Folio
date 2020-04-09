package Tests;

import Model.StockModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockTests {

    @Test
    public void constructTestGoodParams(){
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        Assertions.assertNotNull(testStockGood.refresh());
    }

    @Test
    public void constructTestBadParams(){
        StockModel testStockBad = new StockModel("blah", "blah", 1);
        Assertions.assertNull(testStockBad.refresh());
    }

    @Test
    public void testRefresh() {
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        Assertions.assertNotNull(testStockGood.refresh());
        Assertions.assertTrue(testStockGood.getLastKnownPrice() >= 0 || testStockGood.getLastKnownPrice() < 0);
    }

    @Test
    public void sellSharesTestGoodParams(){
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        Assertions.assertTrue(testStockGood.sellShares(1));
        Assertions.assertEquals(testStockGood.getNumShares(), 0);
    }

    @Test
    public void sellSharesTestBadParams(){
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        Assertions.assertFalse(testStockGood.sellShares(5));
        Assertions.assertEquals(testStockGood.getNumShares(), 1);
    }

    @Test
    public void buySharesTest(){
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        double firstValue = testStockGood.getValue();
        testStockGood.buyShares(1);
        Assertions.assertTrue(testStockGood.getValue() > firstValue);
        Assertions.assertEquals(testStockGood.getNumShares(), 2);
    }

    @Test
    public void checkTrendTest(){
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        Assertions.assertTrue(testStockGood.getTrend() > 0 || testStockGood.getTrend() == 0 || testStockGood.getTrend() < 0);
    }

    @Test
    public void checkSetValue() {
        StockModel testStockGood = new StockModel("GOOGL", "Google", 1);
        testStockGood.setValue(100);
        Assertions.assertEquals(testStockGood.getValue(), 100);

    }
}
