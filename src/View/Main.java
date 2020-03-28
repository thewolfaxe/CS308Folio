package View;

import Model.FolioModel;
import Model.StockModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {

    private ArrayList<FolioModel> folios = new ArrayList<>(); // all current opened folio's
    private String newPopupField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FolioTracker");
        VBox vBox = new VBox();

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem menuItem1 = new MenuItem("New");

        fileMenu.getItems().add(menuItem1);


        menuBar.getMenus().add(fileMenu);


        vBox.getChildren().add(menuBar);
//        FolioModel folioModel = new FolioModel(1, "TestFolio1");
//        FolioModel folioModel2 = new FolioModel(1, "TestFolio1");
//        folios.add(folioModel);
//        folios.add(folioModel2);
        TabPane tabpane = new TabPane();

        if (folios.size() == 0) {
            Tab tab1 = new Tab("*No Folio*");
            VBox tabContent = new VBox();
            Insets s = new Insets(10, 10, 10, 10);
            Label noFolio = new Label("No Folio open");
            tabContent.getChildren().add(noFolio);
            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1);
            System.out.println("H");

        }
        for (int i = 0; i < folios.size(); i++) {

            Tab tab1 = new Tab(folios.get(i).getName()); // Set folio tab name

            int finalI = i; // Needed for some reason
            tab1.setOnCloseRequest(e -> { // On close delete it from the foilios arraylist
                folios.remove(folios.get(finalI));
            });
            //we will need a new folioModel for each tab

            VBox tabContent = new VBox();

            Insets s = new Insets(10, 10, 10, 10);

            VBox newStocks = new VBox();

//            nameStock.setSpacing(10);
////        nameStock.setPadding(s);
//            nameStock.setAlignment(Pos.CENTER);
//            nameStock.getChildren().addAll(name, name_txt);

            HBox stockInfo = new HBox();
            HBox nameStock = new HBox();
            Label name = new Label("Chosen Name");
            TextField name_txt = new TextField();
            Label tickerSymbol = new Label("Ticker Symbol");
            TextField tickerSymbol_txt = new TextField();
            Label numberShares = new Label("Number of Shares");
            TextField numberShares_txt = new TextField();
            stockInfo.getChildren().addAll(name, name_txt, tickerSymbol, tickerSymbol_txt, numberShares, numberShares_txt);
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
//        stocks = getStocks();

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
            tabContent.getChildren().addAll(newStocks, table);

//        table.getItems().add(new StockModel("h", "h",0));

            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1); //add all probably

            Controller.ButtonHandler buttonHandler = new Controller.ButtonHandler(folios.get(i));
            Timeline autoRefresh = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ObservableList<Model.StockModel> refreshedStocks = buttonHandler.mainRefresh(stocks);
                    for (int i = 0; i < refreshedStocks.size(); i++) {
                        stocks.set(i, refreshedStocks.get(i));
                        System.out.println(refreshedStocks.get(i).getValue());
                    }
                    System.out.println("stocks refreshed");
                }
            }));

            autoRefresh.setCycleCount(Timeline.INDEFINITE);
            autoRefresh.play();
            add.setOnAction(a -> {
                StockModel stock = buttonHandler.mainAdd(name_txt.getText(), tickerSymbol_txt.getText(), numberShares_txt.getText());
                boolean isin = false;
                if (stock != null){
                    if(stocks.contains(stock)){
                        stocks.set(stocks.indexOf(stock), stock);
                    }
//
//                    System.out.println(stock.toString());
//                    for(StockModel sm : stocks){
//                        if(sm.getTickerSymbol().equals(tickerSymbol.getText())){
//                            if(sm.getName().equals(name))
//                                System.out.println(true);
//                            isin = true;
//                            sm.buyShares(Integer.parseInt(numberShares_txt.getText()));
////                            return s;
//                        }
//                    }
                    else{

                        stocks.add(stock);
                    }
                }
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
                for (int j = 0; j < refreshedStocks.size(); j++) {
                    stocks.set(j, refreshedStocks.get(j));
                }
            });
        }


            vBox.getChildren().addAll(tabpane);

//
//            primaryStage.setScene(new Scene(vBox, 600, 600));
//            primaryStage.show();
//
//


//            vBox.getChildren().add(tabpane);

            System.out.println("HERE");


            primaryStage.setScene(new Scene(vBox, 1000, 600));
            primaryStage.show();


            menuItem1.setOnAction(e -> {
                newDialog();
                try {
                    start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(newPopupField.toString());
            });

        }

        private void getStocks () {
            return;
        }


        private void newDialog() {

            TextInputDialog dialog = new TextInputDialog();

            dialog.setTitle("Enter new stock name");
            dialog.setHeaderText("Enter stock name");
            dialog.setContentText("Stock Name:");

            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                newPopupField = result.get();
                if (folios.size() > 0) {
                    folios.add(new FolioModel(folios.get(folios.size() - 1).getId() + 1, newPopupField));
                } else {
                    folios.add(new FolioModel(0, newPopupField));

                }
                System.out.println(folios.size());

            });
        }

        public static void main (String[]args){
            launch(args);
        }

}
