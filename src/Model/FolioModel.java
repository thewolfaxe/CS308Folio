package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class FolioModel implements iFolioModel, Serializable {

    private static final long serialversionUID = 123456789L;

    private int id;
    private String name;
    private ArrayList<StockModel> stocks;
    private Double value;

    public FolioModel(int id, String name) {
        stocks = new ArrayList<>();
        this.id = id;
        this.name = name;
    }

    public FolioModel() {}

    public String getName() {
        return name;
    }

    public void updateValue() {
        value = (double) 0;
        for(StockModel s: stocks){
            value += s.getValue();
        }
//        System.out.println("total: " + value);
    }
    
    public Double getValue(){
        updateValue();
        return value;
    }

    public synchronized void refreshStocks() {
        for (StockModel stock : stocks) {
            stock.refresh();
        }
    }

    public ArrayList<StockModel> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<StockModel> stonks) {
        this.stocks = stonks;
    }

    public ArrayList<StockModel> sort(int sortCode, boolean ascending) {
        if (sortCode < 0 || sortCode > 4) return null; //view must check this doesnt return null
        switch (sortCode) {
            case 0: //sort by ticker
                if (!ascending) {
                    stocks.sort(Comparator.comparing(StockModel::getTickerSymbol));
                } else {
                    stocks.sort(Comparator.comparing(StockModel::getTickerSymbol).reversed());
                }
                break;
            case 1: //sort by name
                if (!ascending) {
                    stocks.sort(Comparator.comparing(StockModel::getName));
                } else {
                    stocks.sort(Comparator.comparing(StockModel::getName).reversed());
                }
                break;
            case 2: //sort by no shares
                if (!ascending) {
                    stocks.sort(Comparator.comparingInt(StockModel::getNumShares));
                } else {
                    stocks.sort(Comparator.comparingInt(StockModel::getNumShares).reversed());
                }
                break;
            case 3: //sort by price per share
                if (!ascending) {
                    stocks.sort(Comparator.comparingDouble(StockModel::getLastKnownPrice));
                } else {
                    stocks.sort(Comparator.comparingDouble(StockModel::getLastKnownPrice).reversed());
                }
                break;
            case 4: //sort by value
                if (!ascending) {
                    stocks.sort(Comparator.comparingDouble(StockModel::getValue));
                } else {
                    stocks.sort(Comparator.comparingDouble(StockModel::getValue).reversed());
                }
                break;
        }
        return stocks;
    }

    public iStockModel newStock(String ticker, String name, int shares) {
        StockModel newStock = new StockModel(ticker, name, shares);
        if (newStock.refresh() != null) {
            stocks.add(newStock);
            return newStock;
        } else
            return null;
    }

    public iStockModel buyStock(iStockModel s, int shares) {
        for (iStockModel stock : stocks) {
            if (stock.getTickerSymbol().equals(s.getTickerSymbol())) {
                stock.buyShares(shares);
                return stock;
            }
        }
        return null;
    }

    public boolean deleteStock(String ticker) {
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getTickerSymbol().equals(ticker)) {
                stocks.remove(i);
                return true;
            }
        }
        return false; //false if the stock doesnt exist
    }

    public boolean save(String path) { //this takes just a path, no filename
//        path += this.name + ".ser";
        try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
            System.out.println("Folio has been saved");
            return true;
        } catch (IOException e) {
            System.out.println("Failed: " + e);
            return false;
        }

    }

    public static FolioModel load(String path) { //this takes full path to file. View must check for null returns
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            FolioModel object = (FolioModel) in.readObject();
            in.close();
            file.close();
            System.out.println("Folio has been loaded");
            return (FolioModel) object;
        } catch (InvalidClassException ex) {
            System.out.println(".folio file is of a previous version and is no longer compatible");
            return null;
        } catch (IOException ex) {
            System.out.println("Can't find the file");
//            ex.printStackTrace();
            return null;
        } catch (ClassNotFoundException ex) {
            System.out.println("Can't read the file");
            return null;
        }
    }

    public int getId() {
        return id;
    }

}
