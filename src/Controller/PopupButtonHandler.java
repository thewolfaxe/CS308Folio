package Controller;

import Model.StockModel;
import javafx.collections.ObservableList;

public class PopupButtonHandler {
    StockModel stock;

    public PopupButtonHandler(StockModel stock) {
        this.stock = stock;
    }

    public void editStockApply(int bought, int sold) {
        if(bought != 0)
            stock.buyShares(bought);

        if(sold != 0)
            stock.sellShares(sold);
    }
}
