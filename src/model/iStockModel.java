package model;

public interface iStockModel {

        void setName(String name);
        void getCurrentPrices();
        double getValue();
        void buyShares(int amount);
        boolean sellShares(int amount);
        double estimateProfits();
        boolean checkTrend();
        StockModel refresh();
        String getTicker();
}
