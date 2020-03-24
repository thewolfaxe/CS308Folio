package model;

import javafx.beans.property.SimpleStringProperty;

public class StockModel implements iStockModel {

    private String tickerSymbol;
    private String name;
    private int noOfShares;
    private int initialNoOfShares;
    private double lastKnownPrice;
    private double initialBuyPrice;

    /*
     * Constructor for stock w/values
     * @param tickerSymbol      stock ticker symbol
     * @param name              name of the stock
     * @param noOfShares        number of shares for given stock
     */
    public StockModel(String tickerSymbol, String name, int initialNoOfShares){
        this.tickerSymbol = tickerSymbol;
        this.name = name;
        this.initialNoOfShares = initialNoOfShares;
        this.noOfShares = initialNoOfShares;
        /*if(initialNoOfShares > 0)
            getCurrentPrices();    //this updates lastKnownPrice and buyPrice */
        refresh(); //this is called when a new stock is created and the refresh method will update lastknown price therefore initial price can be set to lastknown
        initialBuyPrice = lastKnownPrice;
    }


    /*
     * setter for stock name
     * @param name              name of the stock
     */
    public void setName(String name) {
        this.name = name;       //need to check if the server class returns a name
    }

    /*
     * updates the lastKnownPrice to the current price
     * updates the initialBuyPrice if needed
     */


//Refresh() method will do this
  /*  public void getCurrentPrices() {
        lastKnownPrice = 1;    //this will pull the real price from the online market
        if(initialBuyPrice == -1)
            initialBuyPrice = lastKnownPrice;
    } */ 


    /*
     * setter for number of shares
     * @param value             total value of stock (noOfShares * price)
     */
    public double getValue() {
        return noOfShares*lastKnownPrice;
    }

    public String getName(){
        return name;
    }
    public int getNumShares(){
        return noOfShares;
    }
    public double getPricePerShare(){
        return lastKnownPrice;
    }
    public double getValue(){
        return lastKnownPrice * noOfShares;
    }
    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     */
    public void buyShares(int amount){
        noOfShares += amount;
    }

    /*
     * returns the overall value that the stock was sold at
     * @return                  total value stock was sold at
     */
    public boolean sellShares(int amount){
        //full delete
        //can decide whether to leave it on folio with ticker & name with
        //all other values at zeros
        /*
        //we can maybe have a sellAll() method to do this
        double returnValue = value;
        tickerSymbol = "";
        name = "";
        noOfShares = 0;
        price = 0;
        value = 0;
        return returnValue;

         */

        if((noOfShares-amount) < 0)
            return false;

        noOfShares -= amount;
        return true;
    }

    /*
     * returns the estimate profit for stick
     * @return                  estimated profit
     */
    public double estimateProfits(){
        refresh();
        return (lastKnownPrice * noOfShares) - (initialBuyPrice * initialNoOfShares);
    }

    //this may be a bit of a pain to do properly but can cheat by just checking if current value is more than initialValue
    public boolean checkTrend(){
        return false;       //return true if trend is increasing and false if it is decreasing
    }

    //this needs to pull fresh values from the stock market and update local values
    public StockModel refresh() {
        //deffo update lastKnownPrice, push change from here or pull changes from outside??
        try{
            String value = StrathQuoteServer.getLastValue(tickerSymbol);
            value = value.replace(",", "");
            lastKnownPrice = Double.parseDouble(value);
            return this;
        }Catch(WebsiteDataException | NoSuchTickerException e){
            System.out.println("failed: " + e);
            return null;
        }
        
    }

    public String getTickerSymbol(){
        return tickerSymbol;
    }

}
