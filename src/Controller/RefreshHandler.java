package Controller;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class RefreshHandler {
    private iFolioModel folioModel;

    public RefreshHandler(iFolioModel folioModel) {
        this.folioModel = folioModel;
    }

    public ObservableList<Model.iStockModel> stockRefresh(ObservableList<iStockModel> stocks){
        for (iStockModel stock : folioModel.getStocks())
            stock.refresh();

        return stocks;
    }

    public ObservableList<iFolioModel> totalRefresh(ObservableList<iFolioModel> total) {
        for(iFolioModel folio: total)
            folio.updateValue();

        return total;
    }

    public ArrayList<iStockModel> specalRefresh(ObservableList<iStockModel> stocks) {
        ArrayList<iStockModel> stonks = new ArrayList<>();
        for(iStockModel stock: stocks) {
            stonks.add(stock.refresh());
        }
        return stonks;
    }

    public iStockModel soloRefresh(iStockModel stock) {
        return stock.refresh();
    }
}
