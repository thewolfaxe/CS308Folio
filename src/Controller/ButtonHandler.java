package Controller;

import Model.StockModel;

public class ButtonHandler {
    Model.FolioModel folioModel;

    public ButtonHandler(Model.FolioModel folioModel) {
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
}
