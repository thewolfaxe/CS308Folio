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

    public ObservableList<Model.iStockModel> mainRefresh (ObservableList<Model.iStockModel> stocks){
        for (iStockModel stock : stocks)
            stock.refresh();

        return stocks;
    }

    public iStockModel soloRefresh(iStockModel stock) {
        return stock.refresh();
    }
}
