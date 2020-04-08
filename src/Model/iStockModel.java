package Model;

public interface iStockModel {

        void buyShares(int amount);
        boolean sellShares(int amount);
        double estimateProfits();
        double getTrend();
        iStockModel refresh();
        String getTickerSymbol();
        String getName();
        int getNumShares();
        int getInitialNoOfShares();
        double getLastKnownPrice();
        double getInitBuyPrice();
        double getValue();
        void setValue(double value);
        double getInitValue();
        double getGain();
}
