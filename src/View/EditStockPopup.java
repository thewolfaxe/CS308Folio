package View;

import Controller.ButtonHandler;
import Controller.PopupButtonHandler;
import Model.StockModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class EditStockPopup extends Application {
    StockModel stock;
    String folioName;

    public EditStockPopup(StockModel stock, String folioName) {
        this.stock = stock;
        this.folioName = folioName;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage = popup();
        stage.show();
    }

    public Stage popup() {
        Stage stage = new Stage();

        VBox popup = new VBox();
        Insets s = new Insets(10,10,10,10);
        popup.setPadding(s);
        popup.setSpacing(10);

        Label portInfo = new Label("Editing portfolio: " + folioName);
        portInfo.setTextFill(Paint.valueOf("#6897bb"));

        GridPane mainContent = new GridPane();

        Label ticker = new Label("Ticker: " + stock.getTickerSymbol());
        Label userName = new Label("Name: " + stock.getName());

        Label initPrice = new Label("Initial value: " + stock.getInitBuyPrice());
        Label initShare = new Label("Initial shares: " + stock.getInitialNoOfShares());
        Label initValue = new Label("Initial Value: " + stock.getInitValue());

        Label cPrice = new Label("Current Price: " + stock.getLastKnownPrice());
        Label cNumShares = new Label("Current number of shares: " + stock.getNumShares());
        Label cValue = new Label("Current value: " + stock.getValue());

        Label totGain = new Label("Total gain: " + (stock.getValue()-stock.getInitValue()));

        Label sell = new Label("Sell: ");
        TextField sell_txt = new TextField();
        Label buy = new Label("Buy: ");
        TextField buy_txt = new TextField();

        mainContent.add(ticker, 0, 0);
        mainContent.add(userName, 1, 0);
        mainContent.add(cPrice, 0, 1);
        mainContent.add(cNumShares, 1, 1);
        mainContent.add(cValue, 0, 2);
        mainContent.add(initPrice, 0, 3);
        mainContent.add(initShare, 1, 3);
        mainContent.add(initValue, 0, 4);
        mainContent.add(totGain, 0, 5);
        mainContent.add(sell, 0, 6);
        mainContent.add(sell_txt, 1, 6);
        mainContent.add(buy, 0, 7);
        mainContent.add(buy_txt, 1, 7);
        mainContent.setVgap(1);
        mainContent.setHgap(5);
        mainContent.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        Button apply = new Button("Apply");
        Button cancel = new Button("Cancel");
        Button sellAll = new Button("Sell All");
        buttons.getChildren().addAll(apply, cancel, sellAll);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(3);

        popup.getChildren().addAll(portInfo, mainContent, buttons);

        stage.setScene(new Scene(popup, 400, 200));

        PopupButtonHandler handler = new PopupButtonHandler(stock);
        apply.setOnMouseClicked(e -> {
            int bought = (int) Integer.parseInt(buy_txt.getText());
            int sold = (int) Integer.parseInt(sell_txt.getText());
            handler.editStockApply(bought, sold);
            stage.close();
        });

        cancel.setOnMouseClicked(e -> {
            stage.close();
        });

        sellAll.setOnMouseClicked(e -> {
            handler.editStockApply(0, stock.getNumShares());
        });

        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
