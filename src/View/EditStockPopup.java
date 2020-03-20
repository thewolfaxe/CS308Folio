package View;

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

        Label portInfo = new Label("Editing portfolio: ");
        portInfo.setTextFill(Paint.valueOf("#6897bb"));

        GridPane mainContent = new GridPane();

        Label ticker = new Label("tickerSym");
        Label userName = new Label("nameUserGave");

        Label cValue = new Label("Current value: ");
        Label dChange = new Label("Daily change: ");

        Label numShares = new Label("Number of shares: ");
        TextField numSharesTxt = new TextField("recorded current value");

        Label initVal = new Label("Initial value: ");
        TextField initValTxt = new TextField("recorded init value");

        Label totGain = new Label("Total gain: ");

        mainContent.add(ticker, 0, 0);
        mainContent.add(userName, 1, 0);
        mainContent.add(cValue, 0, 1);
        mainContent.add(dChange, 1, 1);
        mainContent.add(numShares, 0, 3);
        mainContent.add(numSharesTxt, 1, 3);
        mainContent.add(initVal, 0, 4);
        mainContent.add(initValTxt, 1, 4);
        mainContent.add(totGain, 0, 5);
        mainContent.setVgap(1);
        mainContent.setHgap(5);
        mainContent.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        Button save = new Button("Save");
        Button cancel = new Button("Cancel");
        Button delete = new Button("Delete");
        buttons.getChildren().addAll(save, cancel, delete);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(3);

        popup.getChildren().addAll(portInfo, mainContent, buttons);

        stage.setScene(new Scene(popup, 400, 200));

        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
