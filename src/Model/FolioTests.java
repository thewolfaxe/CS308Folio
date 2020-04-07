package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

public class FolioTests {

    public FolioModel testFolio = new FolioModel(0, "test");

    @Test
    public void addStockTestGoodStock(){
        ArrayList<StockModel> empty = new ArrayList<>();
        StockModel testStock = new StockModel("GOOGL", "Google", 1);
        empty.add(testStock);

        testFolio.newStock("GOOGL", "Google", 1);
        System.out.println(empty.get(0) + " " + testFolio.getStocks().get(0));

        Assertions.assertTrue((empty.get(0).getName().equals(testFolio.getStocks().get(0).getName())) &&
                (empty.get(0).getLastKnownPrice() == testFolio.getStocks().get(0).getLastKnownPrice()) &&
                (empty.get(0).getNumShares() ==  testFolio.getStocks().get(0).getNumShares()) &&
                (empty.get(0).getLastKnownPrice() == testFolio.getStocks().get(0).getLastKnownPrice()) &&
                (empty.get(0).getTickerSymbol().equals(testFolio.getStocks().get(0).getTickerSymbol())) &&
                (empty.get(0).getValue() == testFolio.getStocks().get(0).getValue()) &&
                (empty.get(0).getInitBuyPrice() == testFolio.getStocks().get(0).getInitBuyPrice()) &&
                (empty.get(0).getInitialNoOfShares() == testFolio.getStocks().get(0).getInitialNoOfShares())&&
                (empty.get(0).getInitValue() == testFolio.getStocks().get(0).getInitValue()));
    }

    @Test
    public void addStockTestBadStock(){
        Assertions.assertNull(testFolio.newStock("blah", "blah", 0));
    }

    @Test
    public void increaseSharesTest(){
        testFolio.newStock("GOOGL", "Google", 1);
        testFolio.buyStock(new StockModel("GOOGL", "Google", 1),1);
        Assertions.assertEquals(testFolio.getStocks().get(0).getNumShares(), 2);
    }

    @Test
    public void deleteStockTestExists(){
        testFolio.deleteStock("GOOGL");
        Assertions.assertTrue(testFolio.getStocks().isEmpty());
    }

    @Test
    public void deleteStockTestDoesNotExist(){
        Assertions.assertFalse(testFolio.deleteStock("blahh"));
    }

    @Test
    public void saveTestGoodPath(){
        Assertions.assertTrue(testFolio.save("test.ser"));
    }

    @Test
    public void loadTestGoodPath(){
        Assertions.assertDoesNotThrow(() -> testFolio.load("test.ser"));
    }

    @Test
    public void loadTestBadPath(){
        FolioModel folio = new FolioModel(1, "bob");
        File file = new File("test.ser");
        file.delete();
        Assertions.assertNull(folio.load("sdads"));
    }

    @Test
    public void sortTestByTicker(){
        testFolio.newStock("GOOGL","Google",1);
        testFolio.newStock("APPL","Apple",1);
        testFolio.sort(1, false);

        Assertions.assertEquals("APPL", testFolio.getStocks().get(0).getTickerSymbol());
    }

    @Test
    public void sortTestByName(){
        testFolio.newStock("GOOGL","Google",1);
        testFolio.newStock("APPL","Apple",2);
        testFolio.sort(2, true);

        Assertions.assertEquals("Apple", testFolio.getStocks().get(0).getName());
    }

    @Test
    public void sortTestByNoShares(){
        testFolio.newStock("GOOGL","Google",1);
        testFolio.newStock("APPL","Apple",2);
        testFolio.sort(3, true);

        Assertions.assertEquals(1, testFolio.getStocks().get(0).getNumShares());
    }

}
