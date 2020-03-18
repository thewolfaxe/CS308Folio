package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.awt.*;
import java.util.Collection;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("FolioTracker");
        VBox vBox = new VBox();


        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("TestFolio1");
            VBox tabContent = new VBox();
            Label tickerSymbol = new Label("Ticker Symbol");
            TextField tickerSymbol_txt = new TextField();
            Label numberShares = new Label("Number of Shares");
            TextField numberShares_txt = new TextField();
            HBox hbox = new HBox();
            Insets s= new Insets(10,10,10,10);
            hbox.setPadding(s);
            hbox.setSpacing(10);
            hbox.setStyle("-fx-background-color:  rgba(0,0,0,0.3);");
            Button add = new Button("Add");
            hbox.getChildren().addAll(tickerSymbol, tickerSymbol_txt, numberShares, numberShares_txt, add);
            hbox.setAlignment(Pos.CENTER);
//            tabContent.getChildren().add(hbox);

            TableView table = new TableView();
            table.setMinHeight(600 - hbox.getHeight());
            table.setEditable(false); //for now
            TableColumn tickerSymbolColumn = new TableColumn("Ticker Symbol");
            tickerSymbolColumn.setMinWidth(100);
            tickerSymbolColumn.setCellFactory(new PropertyValueFactory<>("H"));
            TableColumn stockNameColumn = new TableColumn("Stock Name");
            stockNameColumn.setMinWidth(100);
            TableColumn numberSharesColumn = new TableColumn("Number of Shares");
            numberSharesColumn.setMinWidth(100);
            TableColumn pricePerShareColumn = new TableColumn("Price per Share");
            pricePerShareColumn.setMinWidth(100);
            TableColumn valueOfHolding = new TableColumn("Value of Holding");
            valueOfHolding.setMinWidth(100);

            table.getColumns().addAll(
                    tickerSymbolColumn,
                    stockNameColumn,
                    numberSharesColumn,
                    pricePerShareColumn,
                    valueOfHolding);
            tabContent.getChildren().addAll(hbox,table);


        tab1.setContent(tabContent);

        tabpane.getTabs().add(tab1); //add all probably



        vBox.getChildren().addAll(tabpane);


        primaryStage.setScene(new Scene(vBox, 600, 600));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
