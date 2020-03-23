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
        stocks = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void refreshStocks() {
        for(StockModel stock: stocks) {
            stock.refresh();    //refresh needs to internally update values within model.StockModel
        }
    }

    public ArrayList<iStockModel> sort(int sortCode, ArrayList<iStockModel> stocks) {
        //do something to sort stocks
        return stocks;
    }

    public boolean addStock(String ticker, String name, int shares){
        stocks.add(new StockModel(ticker, name, shares));
        return true; //have some way of return false if ticker symbol is invalid
    }

    public boolean deleteStock(String ticker) {
       for(int i=0;i<stocks.size();i++){
           if(stocks.get(i).getTickerSymbol().equals(ticker)){

           }
       }
        return true; //false if the stock doesnt exist
    }

    public double getFolioValue() {
        double val = 0;
        for (iStockModel stock : stocks) {
            //stock.refresh();      //maybe call here,
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
