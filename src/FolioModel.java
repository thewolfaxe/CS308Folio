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

    public boolean addStock(String ticker, String name, int shares){
        stocks.add(new StockModel(ticker, name, shares));
        return true; //have some way of return false if ticker symbol is invalid
    }

    public boolean deleteStock(String ticker) {
       for(int i=0;i<stocks.size();i++){
           if(stocks.get(i).getTicker().equals(ticker)){

           }
       }
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
