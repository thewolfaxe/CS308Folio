package Model;

public class StockModel implements iStockModel, Serializable {

    private String tickerSymbol;
    private String name;
    private int numShares;
    private int initialNoOfShares;
    private double lastKnownPrice;
    private double initialBuyPrice;
    private double value;

    /*
     * Constructor for stock w/values
     * @param tickerSymbol      stock ticker symbol
     * @param name              name of the stock
     * @param noOfShares        number of shares for given stock
     */
    public StockModel(String tickerSymbol, String name, int initialNoOfShares){
       /* this.tickerSymbol = new SimpleStringProperty(tickerSymbol);
        this.name = new SimpleStringProperty(name);
        this.initialNoOfShares = initialNoOfShares;
        this.numShares = new SimpleIntegerProperty(initialNoOfShares);
        this.lastKnownPrice = new SimpleDoubleProperty(-1);
        this.value = new SimpleDoubleProperty(-1);
        */
    	this.tickerSymbol = tickerSymbol;
    	this.name = name;
    	this.initialNoOfShares = initialNoOfShares;
    	this.numShares = initialNoOfShares;
    	this.lastKnownPrice = -1;
    	this.value = -1;
        refresh(); //this is called when a new stock is created and the refresh method will update lastknown price therefore initial price can be set to lastknown
        //initialBuyPrice = lastKnownPrice.get();
        initialBuyPrice = lastKnownPrice;
    }

    public String getTickerSymbol() {
        //return tickerSymbol.get();
    	return tickerSymbol;
    }

    public String getName() {
        //return name.get();
    	return name;
    }

    public int getNumShares() {
       // return numShares.get();
    	return numShares;
    }

    public int getInitialNoOfShares() {
        return initialNoOfShares;
    }

    public double getLastKnownPrice() {
       // return lastKnownPrice.get();
    	return lastKnownPrice;
    }

    public double getInitBuyPrice() {
        return initialBuyPrice;
    }

    public double getValue() {
       // return value.get();
    	return value;
    }

    public void setValue(double value) {
       // this.value.set(value);
    	this.value = value;
    }

    /*
     * increase number of shares in this stock
     * @param numberOfShares    number of shares to increase by
     */
    public void buyShares(int amount){
        //numShares.set(numShares.get() + amount);
        numShares += amount;
        setValue(getNumShares()*getLastKnownPrice());
    }

    /*
     * returns the overall value that the stock was sold at
     * @return                  total value stock was sold at
     */
    public boolean sellShares(int amount){
        if((numShares - amount) < 0)
            return false;

       
        numShares -= amount;
        setValue(getNumShares()*getLastKnownPrice());
        return true;
    }

    /*
     * returns the estimate profit for stick
     * @return                  estimated profit
     */
    public double estimateProfits(){
        refresh();
        return value - (initialBuyPrice * initialNoOfShares);
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
            setValue(getNumShares()*getLastKnownPrice());
            return this;    //this may cause an issue in the constructor but maybe not
        }catch(WebsiteDataException | NoSuchTickerException e){
            System.out.println("failed: " + e);
            return null;
        }
        
    }
}
