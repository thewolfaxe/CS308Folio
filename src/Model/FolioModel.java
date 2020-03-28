package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class FolioModel implements iFolioModel, Serializable {
    
	private static final long serialversionUID = 123456789L; 
	
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
                    stocks.sort(Comparator.comparingDouble(StockModel::getLastKnownPrice));
                }else{
                    stocks.sort(Comparator.comparingDouble(StockModel::getLastKnownPrice).reversed());
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

    public StockModel addStock(String ticker, String name, int shares){
        for(StockModel stock: stocks) {
            if(stock.getTickerSymbol().equals(ticker)) {
                stock.buyShares(shares);
                return stock;
            }
        }

        StockModel newStock = new StockModel(ticker, name, shares);
        if (newStock.refresh() != null) {
            stocks.add(newStock);
            return newStock;
        }else
            return null;
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
        for (StockModel stock : stocks) {
            stock.refresh();
            val += stock.getValue();
        }
        return val;
    }

    public boolean save(String path) { //this takes just a path, no filename
    	path += this.name + ".ser";
    	try {
    		FileOutputStream file = new FileOutputStream(path); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
            out.writeObject(this); 
            out.close(); 
            file.close(); 
            System.out.println("Folio has been saved"); 
           return true;
    	}catch(IOException e) {
            System.out.println("Failed: " + e);
    		return false;
    	}
    	
    }
    
    public FolioModel load(String path) { //this takes full path to file. View must check for null returns
    	try {
            FileInputStream file = new FileInputStream(path); 
            ObjectInputStream in = new ObjectInputStream(file); 
            Object object = (FolioModel)in.readObject(); 
            in.close(); 
            file.close(); 
            System.out.println("Folio has been loaded"); 
            return (FolioModel) object;
        }catch (IOException ex) { 
            System.out.println("IOException is caught"); 
            return null;
        }catch (ClassNotFoundException ex) { 
            System.out.println("ClassNotFoundException" + " is caught"); 
            return null;
        } 
    }
    /*
    public boolean save(String pathFile){
        File path = new File(pathFile);

        try(PrintWriter pw = new PrintWriter(path)){
            StringBuilder sb = new StringBuilder();

            for (StockModel s : stocks) { //would use stocks.forEach but confusing to read. . .
                sb.append(s.getTickerSymbol());
                sb.append(',');
                sb.append(s.getName());
                sb.append(',');
                sb.append(s.getInitialNoOfShares());
                sb.append(',');
                sb.append(s.getInitBuyPrice());
                sb.append(',');
                sb.append(s.getNumShares());
                sb.append(',');
                sb.append(s.getLastKnownPrice());

                pw.write(String.valueOf(sb));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    } */
  
    
    public int getId() {
        return id;
    }
}
