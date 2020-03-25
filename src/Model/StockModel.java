package Model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableArray;

public class StockModel implements iStockModel {

    private SimpleStringProperty tickerSymbol;
    private SimpleStringProperty name;
    private SimpleIntegerProperty numShares;
    private int initialNoOfShares;
    private SimpleDoubleProperty lastKnownPrice;
    private double initialBuyPrice;
    private SimpleDoubleProperty value;

    /*
     * Constructor for stock w/values
     * @param tickerSymbol      stock ticker symbol
     * @param name              name of the stock
     * @param noOfShares        number of shares for given stock
     */
    public StockModel(String tickerSymbol, String name, int initialNoOfShares){
        this.tickerSymbol = new SimpleStringProperty(tickerSymbol);
        this.name = new SimpleStringProperty(name);
        this.initialNoOfShares = initialNoOfShares;
        this.numShares = new SimpleIntegerProperty(initialNoOfShares);
        this.lastKnownPrice = new SimpleDoubleProperty(-1);
        this.value = new SimpleDoubleProperty(-1);
        refresh(); //this is called when a new stock is created and the refresh method will update lastknown price therefore initial price can be set to lastknown
        initialBuyPrice = lastKnownPrice.get();
    }

    public String getTickerSymbol() {
        return tickerSymbol.get();
    }

    public String getName() {
        return name.get();
    }

    public int getNumShares() {
        return numShares.get();
    }

    public int getInitialNoOfShares() {
        return initialNoOfShares;
    }

    public double getLastKnownPrice() {
        return lastKnownPrice.get();
    }

    public double getInitBuyPrice() {
        return initialBuyPrice;
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     */
    public void buyShares(int amount){
        numShares.set(numShares.get() + amount);
        setValue(getNumShares()*getLastKnownPrice());
    }

    /*
     * returns the overall value that the stock was sold at
     * @return                  total value stock was sold at
     */
    public boolean sellShares(int amount){
        if((numShares.get()-amount) < 0)
            return false;

        numShares.set(numShares.get()-amount);
        setValue(getNumShares()*getLastKnownPrice());
        return true;
    }

    /*
     * returns the estimate profit for stick
     * @return                  estimated profit
     */
    public double estimateProfits(){
        refresh();
        return value.get() - (initialBuyPrice * initialNoOfShares);
    }

    //this may be a bit of a pain to do properly but can cheat by just checking if current value is more than initialValue
    public boolean checkTrend(){
        return false;       //return true if trend is increasing and false if it is decreasing
    }

    //this needs to pull fresh values from the stock market and update local values
    public StockModel refresh() {
        //deffo update lastKnownPrice, push change from here or pull changes from outside??
        try{
            String value = StrathQuoteServer.getLastValue(tickerSymbol.get());
            value = value.replace(",", "");
            lastKnownPrice.set( Double.parseDouble(value));
            setValue(getNumShares()*getLastKnownPrice());
            return this;    //this may cause an issue in the constructor but maybe not
        }catch(Model.WebsiteDataException | Model.NoSuchTickerException e){
            System.out.println("failed: " + e);
            return null;
        }
        
    }
}
