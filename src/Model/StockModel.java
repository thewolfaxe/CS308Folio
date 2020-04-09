package Model;

import java.io.Serializable;

public class StockModel implements iStockModel, Serializable {

    private String tickerSymbol;
    private String name;
    private int numShares;
    private int initialNoOfShares;
    private double lastKnownPrice;
    private double initialBuyPrice;
    private double value;
    private double high;
    private double low;
    private double soldStockValue;
    private double boughtStockValue;

    /*
     * Constructor for stock w/values
     * @param tickerSymbol      stock ticker symbol
     * @param name              name of the stock
     * @param noOfShares        number of shares for given stock
     */
    public StockModel(String tickerSymbol, String name, int initialNoOfShares) {
        this.tickerSymbol = tickerSymbol;
        this.name = name;
        this.initialNoOfShares = initialNoOfShares;
        this.numShares = initialNoOfShares;
        this.lastKnownPrice = -1;
        this.value = -1;
        this.high = Double.NEGATIVE_INFINITY;
        this.low = Double.POSITIVE_INFINITY;
        this.soldStockValue = 0;
        this.boughtStockValue = 0;
        refresh(); //this is called when a new stock is created and the refresh method will update lastknown price therefore initial price can be set to lastknown
        this.initialBuyPrice = lastKnownPrice;
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public String getName() {
        return name;
    }

    public int getNumShares() {
        return numShares;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public int getInitialNoOfShares() {
        return initialNoOfShares;
    }

    public double getLastKnownPrice() {
        return lastKnownPrice;
    }

    public double getInitBuyPrice() {
        return initialBuyPrice;
    }

    public double getInitValue() {
        return Math.round(initialBuyPrice * initialNoOfShares*100)/100.00;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Math.round(value*100)/100.00;
    }

    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     */
    public void buyShares(int amount) {
        numShares += amount;
        boughtStockValue += amount * lastKnownPrice;
        setValue(getNumShares() * getLastKnownPrice());
    }

    /*
     * returns the overall value that the stock was sold at
     * @return                  total value stock was sold at
     */
    public boolean sellShares(int amount) {
        if ((numShares - amount) < 0)
            return false;

        soldStockValue += amount * lastKnownPrice;
        numShares -= amount;
        setValue(getNumShares() * getLastKnownPrice());
        return true;
    }


    //this may be a bit of a pain to do properly but can cheat by just checking if current value is more than initialValue
    public double getTrend() {
        return Math.round(((lastKnownPrice - initialBuyPrice) * (initialNoOfShares))*100)/100.00;     //return true if trend is increasing and false if it is decreasing
    }

    public double getGain() {
        return Math.round((value - getInitValue() - boughtStockValue + soldStockValue)*100)/100.00;
    }

    //this needs to pull fresh values from the stock market and update local values
    public iStockModel refresh() {
        try {
            String value = StrathQuoteServer.getLastValue(tickerSymbol);
            value = value.replace(",", "");
            lastKnownPrice = Double.parseDouble(value);
            setHigh(lastKnownPrice);
            setLow(lastKnownPrice);
            setValue(getNumShares() * getLastKnownPrice());
            return this;
        } catch (WebsiteDataException | NoSuchTickerException e) {
            System.out.println("failed: " + e);
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Couldn't parse double from what StrathQuoteServer returned");
            return null;
        }
    }

    private void setHigh(double lastKnownPrice) {
        if (lastKnownPrice > high) {
            high = lastKnownPrice;
        }
    }

    private void setLow(double lastKnownPrice) {
        if (lastKnownPrice < low) {
            low = lastKnownPrice;
        }
    }
}
