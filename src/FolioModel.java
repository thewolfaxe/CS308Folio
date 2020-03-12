import java.util.ArrayList;

public class FolioModel implements iFolioModel {
    
    private int id;
    private String name;
    private ArrayList<StockModel> stocks;

    public FolioModel(int id, String name) {
        stocks = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void refreshStocks() {
        for(StockModel stock: stocks) {
            stock.refresh();    //refresh needs to internally update values within StockModel
        }
    }

    public ArrayList<StockModel> sort(int sortCode, ArrayList<StockModel> stocks) {
        //do something to sort stocks
        return stocks;
    }

    public boolean addStock(StockModel stock){
        stocks.add(stock);
        return true; //have some way of return false if ticker symbol is invalid
    }

    public boolean deleteStock(StockModel stock) {
        //done automatically when
        stocks.remove(stock); //will probably have to override equals method in StockModel for this to work
        return true; //false if the stock doesnt exist
    }

    public double getFolioValue() {
        double val = 0;
        for (StockModel stock : stocks) {
            //stock.refresh();      //maybe call here,
            val += stock.getValue();
        }
        return val;
    }
   
}
