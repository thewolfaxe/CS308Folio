package Controller;

import Model.StockModel;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class EditStockHandler {
    iStockModel stock;

    public EditStockHandler(iStockModel stock) {
        this.stock = stock;
    }

    public void editStockApply(int bought, int sold) {
        if(bought != 0)
            stock.buyShares(bought);

        if(sold != 0)
            stock.sellShares(sold);
    }

    public iFolioModel delete(iFolioModel folio) {
        for(iStockModel stonk: folio.getStocks())
            if(stonk.getTickerSymbol().equals(stock.getTickerSymbol())) {
                folio.deleteStock(stock.getTickerSymbol());
                return folio;
            }
        return null;
    }
}
