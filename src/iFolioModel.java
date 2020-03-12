import java.util.ArrayList;

interface iFolioModel {
    ArrayList<StockModel> refresh(); //calls a refresh method in each stock and returns all the updated stocks in an ArrayList
    ArrayList<StockModel> sort(int sortCode, ArrayList<StockModel> stocks);
    boolean addStock(StockModel stock);
    boolean deleteStock(StockModel stock);
    double getFolioValue();
    String getName();
}
