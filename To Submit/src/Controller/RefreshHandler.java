package Controller;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;

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

    public iStockModel soloRefresh(iStockModel stock) {
        return stock.refresh();
    }
}
