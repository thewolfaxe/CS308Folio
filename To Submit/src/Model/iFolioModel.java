package Model;

import java.util.ArrayList;

public interface iFolioModel {
    void refreshStocks(); //calls a refresh method in each stock and returns all the updated stocks in an ArrayList

    iStockModel buyStock(iStockModel s, int shares);
    iStockModel newStock(String ticker, String name, int shares);
    boolean deleteStock(String ticker);

    Double getValue();
    void updateValue();

    String getName();

    ArrayList<iStockModel> getStocks();
    void setStocks(ArrayList<iStockModel> stonks);

    ArrayList<iStockModel> sort(int sortCode, boolean ascending);

    boolean save(String path);
    int getId();

}
