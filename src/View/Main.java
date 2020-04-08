package View;

import Controller.FileHandler;
import Controller.NewFolioAdder;
import Controller.NewStockHandler;
import Controller.RefreshHandler;
import Model.iFolioModel;
import Model.iStockModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class Main extends Application {

    private ArrayList<iFolioModel> folios = new ArrayList<>(); // all current opened folio's
    private NewStockHandler newStockHandler;
    private RefreshHandler refreshHandler;
    private FileHandler fileHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FolioTracker");
        VBox vBox = new VBox();
        VBox innerTab;
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem menuNew = new MenuItem("New");
        MenuItem menuSave = new MenuItem("Save Folio");
        MenuItem menuLoad = new MenuItem("Load Folio");
        MenuItem menuExit = new MenuItem("Exit");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        fileMenu.getItems().addAll(menuNew, menuSave, menuLoad, separator, menuExit);
        menuBar.getMenus().add(fileMenu);
        vBox.getChildren().add(menuBar);
        TabPane tabpane = new TabPane();

        if (folios.size() == 0) {
            Tab tab1 = new Tab("*No Folio*");
            VBox tabContent = new VBox();
            Insets s = new Insets(10, 10, 10, 10);
            Label noFolio = new Label("No Folio open");
            tabContent.getChildren().add(noFolio);
            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1);
            innerTab = new VBox();
            innerTab.getChildren().add(tabpane);
        } else {
            innerTab = new VBox();
        }
        for (int i = 0; i < folios.size(); i++) {

            innerTab = new VBox();
            Tab tab1 = new Tab(folios.get(i).getName()); // Set folio tab name
            int finalI = i; // Needed for some reason
            tab1.setOnCloseRequest(e -> { // On close delete it from the foilios arraylist
                folios.remove(folios.get(finalI));
            });
            VBox tabContent = new VBox();
            Insets s = new Insets(10, 10, 10, 10);
            VBox newStocks = new VBox();

            HBox stockInfo = new HBox();
            HBox nameStock = new HBox();
            Label name = new Label("Chosen Name");
            TextField name_txt = new TextField();
            Label tickerSymbol = new Label("Ticker Symbol");
            TextField tickerSymbol_txt = new TextField();
            Label numberShares = new Label("Number of Shares");
            TextField numberShares_txt = new TextField();
            stockInfo.setSpacing(10);
            stockInfo.setAlignment(Pos.CENTER);
            newStocks.setPadding(s);
            newStocks.setSpacing(10);
            newStocks.setStyle("-fx-background-color:  rgba(0,0,0,0.3);");
            Button add = new Button("Add");
            stockInfo.getChildren().addAll(name, name_txt, tickerSymbol, tickerSymbol_txt, numberShares, numberShares_txt, add);
            Button refresh = new Button("Refresh stock values");

            newStocks.getChildren().addAll(nameStock, stockInfo, refresh);
            newStocks.setAlignment(Pos.CENTER);

            ObservableList<iStockModel> stocks = FXCollections.observableArrayList(folios.get(i).getStocks());

            TableView<iStockModel> table = new TableView<>();
            table.setItems(stocks);
            table.setMinHeight(400 - newStocks.getHeight());
            table.setEditable(false); //for now

            TableColumn<iStockModel, String> tickerSymbolColumn = new TableColumn<>("Ticker Symbol");
            tickerSymbolColumn.setMinWidth(200);
            tickerSymbolColumn.setCellValueFactory(new PropertyValueFactory<>("tickerSymbol"));

            TableColumn<iStockModel, String> stockNameColumn = new TableColumn<>("Stock Name");
            stockNameColumn.setMinWidth(100);
            stockNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<iStockModel, Integer> numberSharesColumn = new TableColumn<>("Number of Shares");
            numberSharesColumn.setMinWidth(100);
            numberSharesColumn.setCellValueFactory(new PropertyValueFactory<>("numShares"));

            TableColumn<iStockModel, Double> pricePerShareColumn = new TableColumn<>("Price per Share");
            pricePerShareColumn.setMinWidth(100);
            pricePerShareColumn.setCellValueFactory(new PropertyValueFactory<>("lastKnownPrice"));

            TableColumn<iStockModel, Double> valueOfHolding = new TableColumn<>("Value of Holding");
            valueOfHolding.setMinWidth(100);
            valueOfHolding.setCellValueFactory(new PropertyValueFactory<>("value"));

            TableColumn<iStockModel, Double> change = new TableColumn<>("Trend");
            change.setMinWidth(100);
            change.setCellValueFactory(new PropertyValueFactory<>("trend"));

            change.setCellFactory(a -> new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, false);
                    if (item != null) {
                        double num = Math.round(item * 100);
                        num = num / 100;
                        if (num < 0) {
                            this.setText(Double.toString(num));
                            this.setBackground(new Background(new BackgroundFill(Color.RED,
                                    null, null)));
                        } else {
                            this.setText(Double.toString(num));
                            this.setBackground(new Background(new BackgroundFill(Color.GREEN,
                                    null, null)));
                        }
                    }
                }
            });

            TableColumn<iStockModel, Double> high = new TableColumn<>("High");
            high.setMinWidth(100);
            high.setCellValueFactory(new PropertyValueFactory<>("high"));

            TableColumn<iStockModel, Double> low = new TableColumn<>("Low");
            low.setMinWidth(100);
            low.setCellValueFactory(new PropertyValueFactory<>("low"));


            table.getColumns().addAll(tickerSymbolColumn,
                    stockNameColumn,
                    numberSharesColumn,
                    pricePerShareColumn,
                    valueOfHolding,
                    change,
                    high,
                    low);

            ObservableList<iFolioModel> test = FXCollections.observableArrayList(folios.get(i));
            TableView<iFolioModel> tot = new TableView<>();
            tot.setItems(test);
            TableColumn<iFolioModel, Double> total = new TableColumn<>("Total Value");
            total.setMinWidth(100);
            total.setCellValueFactory(new PropertyValueFactory<>("value"));
            tot.getColumns().add(total);

            tabContent.getChildren().addAll(newStocks, table, tot);

            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1); //add all probably

            refreshHandler = new RefreshHandler(folios.get(i), stocks);
            newStockHandler = new NewStockHandler(folios.get(i));

            autoRefreshSetup(stocks, test);


            tickerSymbol_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, newStockHandler, refreshHandler, test);
                }
            });
            numberShares_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, newStockHandler, refreshHandler, test);
                }
            });
            name_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, newStockHandler, refreshHandler, test);
                }
            });
            add.setOnAction(a -> {
                handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, newStockHandler, refreshHandler, test);
            });


            refresh.setOnAction(a -> {
                ObservableList<iStockModel> refreshedStocks = refreshHandler.stockRefresh(stocks);
                for (int j = 0; j < refreshedStocks.size(); j++) {
                    if (stocks.get(j).getLastKnownPrice() > refreshedStocks.get(j).getLastKnownPrice()) {
                        //set text for that row green, confused how to do this
                    } else if (stocks.get(j).getLastKnownPrice() < refreshedStocks.get(j).getLastKnownPrice()) {
                        //set text for that row green, confused how to do this
                    }
                    stocks.set(j, refreshedStocks.get(j));
                }
            });

            table.setRowFactory(a -> {
                TableRow<iStockModel> row = new TableRow<>();
                row.setOnMouseClicked(e -> {
                    int size = 0;
                    int sizeAfter = 0;
                    if (e.getClickCount() == 2 && (!row.isEmpty())) {
                        EditStockPopup popupEdit = new EditStockPopup(row.getItem(), folios.get(finalI));
                        size = folios.get(finalI).getStocks().size();
                        folios.get(finalI).setStocks(popupEdit.popup());
                        sizeAfter = folios.get(finalI).getStocks().size();
                    }
                    if (!row.isEmpty()) {
                        if(size == sizeAfter) {
                            iStockModel refreshedStock = refreshHandler.soloRefresh(row.getItem());
                            if (refreshedStock != null)
                                if (stocks.contains(refreshedStock))
                                    stocks.set(stocks.indexOf(refreshedStock), refreshedStock);
                        }else
                            stocks.setAll(folios.get(finalI).getStocks());
                            autoRefresh(stocks, test);
                    }
                });
                return row;
            });

            innerTab.getChildren().addAll(tabpane);
        }

        vBox.getChildren().add(innerTab);
        primaryStage.setScene(new Scene(vBox, 1000, 600));
        primaryStage.show();

        menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        menuNew.setOnAction(e -> {
            newDialog();
            try {
                start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        fileHandler = new FileHandler();

        menuSave.setOnAction(e -> {
            if (folios.size() > 0) {
                if (fileHandler.save(folios.get(tabpane.getSelectionModel().getSelectedIndex()))) {
                    System.out.println("File saved successfully");
                }
            } else {
                errorAlert("Error", "No Folio open", "Please create or load a folio before saving",
                        "No folio detected to save");
            }
        });

        menuLoad.setOnAction(e -> {
            try {
                folios = fileHandler.load(folios);
                start(primaryStage);
            } catch (Exception ex) {
                errorAlert("Error", "Error loading your folio", "Please make sure you have the correct file",
                        "Generic error needed for start method");

            }
        });

        menuExit.setOnAction(e -> {
            System.exit(0);
        });
    }

    public void errorAlert(String title, String header, String content, String consolePrint) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(header);
        error.setContentText(content);
        System.out.println(consolePrint);
        error.showAndWait();
    }

    public void autoRefreshSetup(ObservableList<iStockModel> stocks, ObservableList<iFolioModel> totalValue) {
        Task<Void> refreshThread = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(60000);
                } catch(InterruptedException e) {
                    System.out.println("Thread sleep interrupted");
                }
                Platform.runLater(() -> {
                    Timeline autoRefresh = autoRefresh(stocks, totalValue);
                    autoRefresh.setCycleCount(Timeline.INDEFINITE);
                    autoRefresh.play();
                });

                return null;
            }
        };

        new Thread(refreshThread).start();
    }

    private Timeline autoRefresh(ObservableList<iStockModel> stocks, ObservableList<iFolioModel> totValue) {
        return new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
            System.out.println("\nRefreshing stocks");
            ObservableList<iStockModel> refreshedStocks = refreshHandler.stockRefresh(stocks);
            ObservableList<iFolioModel> resfreshedTotal = refreshHandler.totalRefresh(totValue);
            for (int j = 0; j < refreshedStocks.size(); j++)
                stocks.set(j, refreshedStocks.get(j));
            for (int j = 0; j < resfreshedTotal.size(); j++)
                totValue.set(j, resfreshedTotal.get(j));
            System.out.println("stocks refreshed");
            System.out.println(stocks.size());
        }));
    }


    private void handleAdd(TextField name_txt, TextField tickerSymbol_txt, TextField numberShares_txt, ObservableList<iStockModel> stocks, NewStockHandler newStockHandler, RefreshHandler refresh, ObservableList<iFolioModel> totals) {
        iStockModel stock = newStockHandler.addStock(name_txt.getText(), tickerSymbol_txt.getText().toUpperCase(), numberShares_txt.getText());
        if (stock != null) {
            stocks.add(stock);
            ObservableList<iFolioModel> refreshedTotal = refresh.totalRefresh(totals);
            for (int j = 0; j < refreshedTotal.size(); j++)
                totals.set(j, refreshedTotal.get(j));

            name_txt.clear();
            tickerSymbol_txt.clear();
            numberShares_txt.clear();
        } else {
            errorAlert("Error", "Problem adding your stock", "Please make sure all the provided info" +
                            " is correct. Please note this stock may already exist in your folio",
                    "Can't add stock");
        }
    }

    private void newDialog() {
        NewFolioAdder adder = new NewFolioAdder(folios);
        folios = adder.addFolio();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
