package Controller;

import Model.StockModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

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
}
