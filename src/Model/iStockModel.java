package Model;

public interface iStockModel {

        void buyShares(int amount);
        boolean sellShares(int amount);
        double estimateProfits();
        boolean checkTrend();
        StockModel refresh();
}
