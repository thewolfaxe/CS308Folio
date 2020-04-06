package Controller;

import Model.StockModel;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

public class NewStockHandler {
    iFolioModel folioModel;
    iStockModel stock;

    public NewStockHandler(iFolioModel folioModel) {
        this.folioModel = folioModel;
    }

    public iStockModel addStock(String name, String ticker, String number) {
        int numShares;
        try {
            numShares = Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }

        for(iStockModel stock: folioModel.getStocks()) {
            if(stock.getTickerSymbol().equals(ticker)) {
                return null;
            }
        }

        return folioModel.newStock(ticker, name, numShares);

    }



}
