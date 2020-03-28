package Model;

public interface iStockModel {

        void buyShares(int amount);
        boolean sellShares(int amount);
        double estimateProfits();
        boolean checkTrend();
        StockModel refresh();
        String getTickerSymbol();
        String getName();
        int getNumShares();
        int getInitialNoOfShares();
        double getLastKnownPrice();
        double getInitBuyPrice();
        double getValue();
        void setValue(double value);

}
