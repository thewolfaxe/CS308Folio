package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FolioModel implements iFolioModel {
    
    private int id;
    private String name;
    private ArrayList<StockModel> stocks;

    public FolioModel(int id, String name) {
        stocks = new ArrayList<StockModel>();
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void refreshStocks() {
        for(StockModel stock: stocks) {
            stock.refresh();    
        }
    }

    public ArrayList<StockModel> getStocks() {
        return stocks;
    }
    
    public ArrayList<StockModel> sort(int sortCode, boolean ascending) {
        if(sortCode < 0 || sortCode > 4) return null; //view must check this doesnt return null
        switch(sortCode){
            case 0: //sort by ticker
                if(!ascending){
                    stocks.sort(Comparator.comparing(StockModel::getTickerSymbol));
                }else{
                    stocks.sort(Comparator.comparing(StockModel::getTickerSymbol).reversed());
                }
                break;
            case 1: //sort by name
                if(!ascending){
                    stocks.sort(Comparator.comparing(StockModel::getName));
                }else{
                    stocks.sort(Comparator.comparing(StockModel::getName).reversed());
                }
                break;
            case 2: //sort by no shares
                if(!ascending){
                    stocks.sort(Comparator.comparingInt(StockModel::getNumShares));
                }else{
                    stocks.sort(Comparator.comparingInt(StockModel::getNumShares).reversed());
                }
                break;
            case 3: //sort by price per share
                if(!ascending){
                    stocks.sort(Comparator.comparingDouble(StockModel::getPricePerShare));
                }else{
                    stocks.sort(Comparator.comparingDouble(StockModel::getPricePerShare).reversed());
                }
                break;
            case 4: //sort by value
                if(!ascending){
                    stocks.sort(Comparator.comparingDouble(StockModel::getValue));
                }else{
                    stocks.sort(Comparator.comparingDouble(StockModel::getValue).reversed());
                }
                break;
        }
        return stocks;
    }

    public boolean addStock(String ticker, String name, int shares){
        StockModel newStock = new StockModel(ticker, name, shares)
        if(newStock.refresh != null){
            stocks.add(stock);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteStock(String ticker) {
       for(int i=0;i<stocks.size();i++){
           if(stocks.get(i).getTickerSymbol().equals(ticker)){
               stocks.remove(i);
               return true;
           }
       }
        return false; //false if the stock doesnt exist
    }

    public double getFolioValue() {
        double val = 0;
        for (iStockModel stock : stocks) {
            stock.refresh();
            val += stock.getValue();
        }
        return val;
    }

    public boolean save(String pathFile){
        File path = new File(pathFile);

        try(PrintWriter pw = new PrintWriter(path)){
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < stocks.size(); i++){ //would use stocks.forEach but confusing to read. . .
                StockModel s = stocks.get(i);

                sb.append(s.getTickerSymbol());
                sb.append(',');
                sb.append(s.getName());
                sb.append(',');
                sb.append(s.getNoOfShares());
                sb.append(',');
                sb.append(s.getInitialPrice());
                sb.append(',');
                sb.append(s.getLastKnownPrice());
                sb.append(',');
                sb.append(s.getInitialBuyPrice());

                pw.write(String.valueOf(sb));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
}
