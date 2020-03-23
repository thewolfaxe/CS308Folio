package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.StockModel;
import model.iStockModel;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
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
        add.setOnAction((a) -> System.out.println("Hello, World!"));
        hbox.getChildren().addAll(tickerSymbol, tickerSymbol_txt, numberShares, numberShares_txt, add);
        hbox.setAlignment(Pos.CENTER);
//            tabContent.getChildren().add(hbox);

        TableView<model.iStockModel> table = new TableView<>();
        table.setMinHeight(600 - hbox.getHeight());
        table.setEditable(false); //for now

        TableColumn<iStockModel, String> tickerSymbolColumn = new TableColumn<>("Ticker Symbol");
        tickerSymbolColumn.setMinWidth(100);
        tickerSymbolColumn.setCellValueFactory(new PropertyValueFactory<>("tickerSymbol"));

        TableColumn<model.iStockModel, String> stockNameColumn = new TableColumn<>("Stock Name");
        stockNameColumn.setMinWidth(100);
        stockNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<model.iStockModel, Integer> numberSharesColumn = new TableColumn<>("Number of Shares");
        numberSharesColumn.setMinWidth(100);
        numberSharesColumn.setCellValueFactory(new PropertyValueFactory<>("noOfShares"));


        TableColumn<model.iStockModel, Double> pricePerShareColumn = new TableColumn<>("Price per Share");
        pricePerShareColumn.setMinWidth(100);
        pricePerShareColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerShare"));


        TableColumn<model.iStockModel, Double> valueOfHolding = new TableColumn<>("Value of Holding");
        valueOfHolding.setMinWidth(100);
        valueOfHolding.setCellValueFactory(new PropertyValueFactory<>("valueOfHolding"));




        table.getColumns().addAll(tickerSymbolColumn,
                stockNameColumn,
                numberSharesColumn,
                pricePerShareColumn,
                valueOfHolding);
        tabContent.getChildren().addAll(hbox,table);

        table.getItems().add(new StockModel("h", "h",0));

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
