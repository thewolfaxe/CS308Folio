package View;

import Controller.EditStockHandler;
import Model.StockModel;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class EditStockPopup extends Application {
    iStockModel stock;
    iFolioModel folio;

    public EditStockPopup(iStockModel stock, iFolioModel folio) {
        this.stock = stock;
        this.folio = folio;
    }

    @Override
    public void start(Stage stage) throws Exception {
//        stage = popup();
//        stage.show();
    }

    public ArrayList<iStockModel> popup() {
        Stage stage = new Stage();

        VBox popup = new VBox();
        Insets s = new Insets(10, 10, 10, 10);
        popup.setPadding(s);
        popup.setSpacing(10);

        Label portInfo = new Label("Editing portfolio: " + folio.getName());
        portInfo.setTextFill(Paint.valueOf("#6897bb"));

        GridPane mainContent = new GridPane();

        Label ticker = new Label("Ticker: " + stock.getTickerSymbol());
        Label userName = new Label("Name: " + stock.getName());

        Label initPrice = new Label("Initial price: " + stock.getInitBuyPrice());
        Label initShare = new Label("Initial shares: " + stock.getInitialNoOfShares());
        Label initValue = new Label("Initial Value: " + stock.getInitValue());

        Label cPrice = new Label("Current Price: " + stock.getLastKnownPrice());
        Label cNumShares = new Label("Current number of shares: " + stock.getNumShares());
        Label cValue = new Label("Current value: " + stock.getValue());

        Label totGain = new Label("Total gain: " + stock.getGain());

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
        Button del = new Button("Delete");
        buttons.getChildren().addAll(apply, cancel, sellAll, del);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(3);

        popup.getChildren().addAll(portInfo, mainContent, buttons);

        stage.setScene(new Scene(popup));

        EditStockHandler handler = new EditStockHandler(stock);

        apply.setOnMouseClicked(e -> {
            int bought, sold;
            try {
                bought = (int) Integer.parseInt(buy_txt.getText());
            } catch (NumberFormatException ex) {
                bought = 0;
            }
            try {
                sold = (int) Integer.parseInt(sell_txt.getText());
            } catch (NumberFormatException ex) {
                sold = 0;
            }
            if(stock.getNumShares() >= sold) {
                handler.editStockApply(bought, sold);
                stage.close();
            }else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Insufficient stocks");
                error.setHeaderText("You don't have that many stocks");
                error.setContentText("Please give a possible amount");
                error.showAndWait();
            }
        });

        cancel.setOnMouseClicked(e -> {
            stage.close();
        });

        sellAll.setOnMouseClicked(e -> {
            handler.editStockApply(0, stock.getNumShares());
            Alert deleteQ = new Alert(Alert.AlertType.CONFIRMATION);
            deleteQ.setTitle("Empty Stock");
            deleteQ.setHeaderText("You have no stock in this company");
            deleteQ.setContentText("Would you like to remove this stock from your folio?");
            deleteQ.initOwner(stage);
            Optional<ButtonType> choice = deleteQ.showAndWait();
            choice.ifPresent(l -> {
                if(choice.get() == ButtonType.OK) {
                    folio = delete(handler, stage);
                }
            });
            stage.close();
        });

        del.setOnMouseClicked(e -> {
            if (stock.getNumShares() != 0) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText("You still have shares in this company");
                warning.setContentText("We cannot delete this stock until there are 0 shares");
                warning.initOwner(stage);
                warning.showAndWait();
            } else {
                folio = delete(handler, stage);
                stage.close();
            }
        });

        stage.initModality(Modality.WINDOW_MODAL);
        stage.showAndWait();
        return folio.getStocks();
    }

    public iFolioModel delete(EditStockHandler handler, Stage stage) {
        iFolioModel check = handler.delete(folio);
        if (check != null)
            return check;
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("There was a problem deleting your stock");
        error.setContentText("Please try again later");
        error.initOwner(stage);
        error.showAndWait();
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
