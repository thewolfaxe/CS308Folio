public class StockModel {

    String tickerSymbol;
    String name;
    int noOfShares;
    double price;
    double value;

    public StockModel(String tickerSymbol, String name, int noOfShares){
        this.tickerSymbol = tickerSymbol;
        this.name = name;
        this.noOfShares = noOfShares;
    }

    public StockModel(){

    }

    public void addStock(int n){
        noOfShares += n;
    }

    public void updateStock(){
        // get current value using class provided?
    }

}
