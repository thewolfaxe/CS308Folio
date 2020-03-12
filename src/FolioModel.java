import java.util.ArrayList;

public class FolioModel implements iFolioModel {
    
    private User user;
    private int id;
    private String name;
    private ArrayList<StockModel> stocks;

    public FolioModel(User user, int id, String name) {
        stocks = new ArrayList<StockModel>();
        this.user = user;
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public ArrayList<StockModel> refresh() {
        for(int i=0;i<stocks.size();i++){
            stocks.set(i, stocks.get(i).refresh());
        }
        return stocks;
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
        stocks.remove(stock); //will probably have to override equals method in StockModel for this to work
        return true; //false if the stock doesnt exist
    }

    public double getFolioValue() {
        double val = 0;
        for(int i=0;i<stocks.size();i++){
            stocks.get(i).updateStockValue();
            val += stocks.get(i).getValueStock();
        }
        return val;
    }
   
}
