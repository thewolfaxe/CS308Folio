package Controller;

import Model.StockModel;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

public class NewStockHandler {
    iFolioModel folioModel;

    public NewStockHandler(iFolioModel folioModel) {
        this.folioModel = folioModel;
    }

    public iStockModel addStock(String name, String ticker, String number, ObservableList<iStockModel> stocks) {
        int numShares;
        try {
            numShares = Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }

        for(iStockModel stock: folioModel.getStocks()) {
            if(stock.getTickerSymbol().equals(ticker)) {
                System.out.println("\nThis stock already exists in this folio");

                System.out.println("i am using " + folioModel.getName() + "'s folio");
                System.out.println("Stocks: ");
                for(iStockModel stonk: folioModel.getStocks())
                    System.out.println(stonk.getTickerSymbol());
                System.out.println("Observable List:");
                for(iStockModel stonk: stocks)
                    System.out.println(stonk.getTickerSymbol());
                System.out.println();

                return null;
            }
        }


        return folioModel.newStock(ticker, name, numShares);

    }



}
