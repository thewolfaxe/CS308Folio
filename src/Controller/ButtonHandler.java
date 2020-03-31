package Controller;

import Model.StockModel;
import Model.iFolioModel;
import javafx.collections.ObservableList;

public class ButtonHandler {
    iFolioModel folioModel;

    public ButtonHandler(iFolioModel folioModel) {
        this.folioModel = folioModel;
    }

    public StockModel mainAdd(String name, String ticker, String number) {
        int numShares;
        try {
            numShares = Integer.parseInt(number);
        } catch (Exception e) {
            return null;
        }

        return folioModel.addStock(ticker, name, numShares);

    }

    public ObservableList<Model.StockModel> mainRefresh (ObservableList<Model.StockModel> stocks){
        for (StockModel stock : stocks) {
            System.out.println("Main refresh: " + stock.getLastKnownPrice());
            stock.refresh();
        }
        return stocks;
    }

    public StockModel soloRefresh(StockModel stock) {
        return stock.refresh();
    }

}
