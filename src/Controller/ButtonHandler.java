package Controller;

import Model.StockModel;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

public class ButtonHandler {
    iFolioModel folioModel;

    public ButtonHandler(iFolioModel folioModel) {
        this.folioModel = folioModel;
    }

    public iStockModel mainAdd(String name, String ticker, String number) {
        int numShares;
        try {
            numShares = Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }

        return folioModel.addStock(ticker, name, numShares);

    }

    public ObservableList<Model.iStockModel> mainRefresh (ObservableList<Model.iStockModel> stocks){
        for (iStockModel stock : stocks) {
            System.out.println("Main refresh: " + stock.getLastKnownPrice());
            stock.refresh();
        }
        return stocks;
    }

    public iStockModel soloRefresh(iStockModel stock) {
        return stock.refresh();
    }

}
