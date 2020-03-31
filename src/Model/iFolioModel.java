package Model;

import java.util.ArrayList;

public interface iFolioModel {
    void refreshStocks(); //calls a refresh method in each stock and returns all the updated stocks in an ArrayList

    //    ArrayList<iStockModel> sort(int sortCode, ArrayList<iStockModel> stocks);
    iStockModel addStock(String ticker, String name, int shares);

    boolean deleteStock(String ticker);

    double getFolioValue();

    String getName();

    ArrayList<StockModel> getStocks();

    ArrayList<StockModel> sort(int sortCode, boolean ascending);

    boolean save(String path);


    int getId();
}
