package model;

import java.util.ArrayList;

interface iFolioModel {
    void refreshStocks(); //calls a refresh method in each stock and returns all the updated stocks in an ArrayList
    ArrayList<StockModel> sort(int sortCode, ArrayList<StockModel> stocks);
    boolean addStock(String ticker, String name, int shares);
    boolean deleteStock(String ticker);
    double getFolioValue();
    String getName();
}
