package View;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Model.FolioModel;
import Model.StockModel;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FolioTracker");
        VBox vBox = new VBox();


        TabPane tabpane = new TabPane();
        Tab tab1 = new Tab("TestFolio1");
        //we will need a new folioModel for each tab
        FolioModel folioModel = new FolioModel(1, "TestFolio1");

        VBox tabContent = new VBox();

        Insets s= new Insets(10,10,10,10);

        VBox newStocks = new VBox();

        HBox nameStock = new HBox();
        Label name = new Label("Chosen Name");
        TextField name_txt = new TextField();
        nameStock.setSpacing(10);
//        nameStock.setPadding(s);
        nameStock.setAlignment(Pos.CENTER);
        nameStock.getChildren().addAll(name, name_txt);

        HBox stockInfo = new HBox();
        Label tickerSymbol = new Label("Ticker Symbol");
        TextField tickerSymbol_txt = new TextField();
        Label numberShares = new Label("Number of Shares");
        TextField numberShares_txt = new TextField();
        stockInfo.getChildren().addAll(tickerSymbol, tickerSymbol_txt, numberShares, numberShares_txt);
//        stockInfo.setPadding(s);
        stockInfo.setSpacing(10);
        stockInfo.setAlignment(Pos.CENTER);

        newStocks.setPadding(s);
        newStocks.setSpacing(10);
        newStocks.setStyle("-fx-background-color:  rgba(0,0,0,0.3);");
        Button add = new Button("Add");
        Button refresh = new Button("Refresh stock values");

        newStocks.getChildren().addAll(nameStock, stockInfo, add, refresh);
        newStocks.setAlignment(Pos.CENTER);

        ObservableList<StockModel> stocks = FXCollections.observableArrayList();

        TableView<StockModel> table = new TableView<>();
        table.setItems(stocks);
        table.setMinHeight(600 - newStocks.getHeight());
        table.setEditable(false); //for now

        TableColumn<StockModel, String> tickerSymbolColumn = new TableColumn<>("Ticker Symbol");
        tickerSymbolColumn.setMinWidth(100);
        tickerSymbolColumn.setCellValueFactory(new PropertyValueFactory<>("tickerSymbol"));

        TableColumn<StockModel, String> stockNameColumn = new TableColumn<>("Stock Name");
        stockNameColumn.setMinWidth(100);
        stockNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<StockModel, Integer> numberSharesColumn = new TableColumn<>("Number of Shares");
        numberSharesColumn.setMinWidth(100);
        numberSharesColumn.setCellValueFactory(new PropertyValueFactory<>("numShares"));


        TableColumn<StockModel, Double> pricePerShareColumn = new TableColumn<>("Price per Share");
        pricePerShareColumn.setMinWidth(100);
        pricePerShareColumn.setCellValueFactory(new PropertyValueFactory<>("lastKnownPrice"));


        TableColumn<StockModel, Double> valueOfHolding = new TableColumn<>("Value of Holding");
        valueOfHolding.setMinWidth(100);
        valueOfHolding.setCellValueFactory(new PropertyValueFactory<>("value"));




        table.getColumns().addAll(tickerSymbolColumn,
                stockNameColumn,
                numberSharesColumn,
                pricePerShareColumn,
                valueOfHolding);
        tabContent.getChildren().addAll(newStocks,table);

//        table.getItems().add(new StockModel("h", "h",0));

        tab1.setContent(tabContent);

        tabpane.getTabs().add(tab1); //add all probably



        vBox.getChildren().addAll(tabpane);


        primaryStage.setScene(new Scene(vBox, 600, 600));
        primaryStage.show();



        Controller.ButtonHandler buttonHandler = new Controller.ButtonHandler(folioModel);
        add.setOnAction(a -> {
            StockModel stock = buttonHandler.mainAdd(name_txt.getText(), tickerSymbol_txt.getText(), numberShares_txt.getText());
            if(stock != null)
                stocks.add(stock);
            else {
                Alert addedError = new Alert(Alert.AlertType.ERROR);
                addedError.setTitle("Error");
                addedError.setHeaderText("Error adding your stock");
                addedError.setContentText("Please make sure all the provided info is correct");
                System.out.println("Failed");
                addedError.showAndWait();
            }
        });

        refresh.setOnAction(a -> {
            ObservableList<Model.StockModel> refreshedStocks = buttonHandler.mainRefresh(stocks);
            for(StockModel stock : refreshedStocks){
                System.out.println(stock.getName());
            }

            for(int i = 0; i < refreshedStocks.size(); i++){
                stocks.set(i,refreshedStocks.get(i));
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
