package Controller;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.collections.ObservableList;


public class RefreshHandler {
    private iFolioModel folioModel;
    private ObservableList<iStockModel> stocks;

    public RefreshHandler(iFolioModel folioModel, ObservableList<iStockModel> stocks) {
        this.folioModel = folioModel;
        this.stocks = stocks;
    }

    public ObservableList<Model.iStockModel> stockRefresh(ObservableList<Model.iStockModel> stocks){
        for (iStockModel stock : stocks)
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
