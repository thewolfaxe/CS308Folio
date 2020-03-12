public class StockModel {

    private String tickerSymbol;
    private String name;
    private int noOfShares;
    private Integer initialNoOfShares = null;
    private double price;
    private double value; // noOfShares * price
    private Double buyPrice = null;

    /*
     * Constructor for stock without values
     * Needs to use setters
     */
    public StockModel(){ //empty
    }

    /*
     * Constructor for stock w/values
     * @param tickerSymbol      stock ticker symbol
     * @param name              name of the stock
     * @param noOfShares        number of shares for given stock
     */
    public StockModel(String tickerSymbol, String name, int noOfShares){
        this.tickerSymbol = tickerSymbol;
        this.name = name;
        this.noOfShares = noOfShares;
    }

    /*
     * setter for tickerSymbol
     * @param tickerSymbol      stock ticker symbol
     */
    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    /*
     * setter for stock name
     * @param name              name of the stock
     */
    public void setName(String name) {
        this.name = name;
    }

    /*
     * setter for number of shares
     * @param noOfShares        number of shares for stock
     */
    public void setNoOfShares(int noOfShares) {
        this.noOfShares = noOfShares;
        if(initialNoOfShares == null)
                initialNoOfShares = noOfShares;
    }

    /*
     * setter for price
     * @param price             price of the stock
     */
    public void setPrice(double price) {
        this.price = price;
        if(buyPrice == null)
            buyPrice = price;
    }

    /*
     * setter for number of shares
     * @param value             total value of stock (noOfShares * price)
     */
    public void setValue(double value) {
        setValue(value);
    }

    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     */
    public void increaseShares(int numberOfShares){
        setNoOfShares(noOfShares+=numberOfShares);
    }

    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     * @return                  current value of the stock
     */
    public double getValueStock(){
        // get current value by using class provided?
        // set current price as well here?
        return 0.0;
    }

    /*
     * updates overall value of the stock (share price x num of shares)
     */
    public void updateStockValue(){
        setPrice(getValueStock());
        setValue(noOfShares * getValueStock());
    }

    /*
     * returns the overall value that the stock was sold at
     * @return                  total value stock was sold at
     */
    public double sellStock(){
        //full delete
        //can decide whether to leave it on folio with ticker & name with
        //all other values at zeros
        double returnValue = value;
        tickerSymbol = "";
        name = "";
        noOfShares = 0;
        price = 0;
        value = 0;
        return returnValue;
    }

    /*
     * returns the estimate profit for stick
     * @return                  estimated profit
     */
    public double estimateProfits(){
        //update stock before?
        return (price * noOfShares) - (buyPrice * initialNoOfShares);
    }
    
    //public checkTrend(){
        
    //}

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }

    @Override
    public String toString(){
        return this.name + ":" + tickerSymbol  + ":" + Integer.toString(noOfShares);
    }

    public StockModel refresh() {
        return null;
    }
}
