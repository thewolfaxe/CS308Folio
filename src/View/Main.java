package View;

import Controller.ButtonHandler;
import Model.FolioModel;
import Model.StockModel;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {

    private ArrayList<FolioModel> folios = new ArrayList<>(); // all current opened folio's
    private String newPopupField;

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
        fileMenu.getItems().addAll(menuNew, menuSave, menuLoad);
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

            ObservableList<StockModel> stocks = FXCollections.observableArrayList(folios.get(i).getStocks());

            TableView<StockModel> table = new TableView<>();
            table.setItems(stocks);
            table.setMinHeight(600 - newStocks.getHeight());
            table.setEditable(false); //for now

            TableColumn<StockModel, String> tickerSymbolColumn = new TableColumn<>("Ticker Symbol");
            tickerSymbolColumn.setMinWidth(200);
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

            TableColumn<StockModel, Double> change = new TableColumn<>("Trend");
            change.setMinWidth(100);
            change.setCellValueFactory(new PropertyValueFactory<>("trend"));

            int finalI1 = i;
            change.setCellFactory(a -> new TableCell<StockModel, Double>() {

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

            TableColumn<StockModel, Double> high = new TableColumn<>("High");
            high.setMinWidth(100);
            high.setCellValueFactory(new PropertyValueFactory<>("high"));

            TableColumn<StockModel, Double> low = new TableColumn<>("Low");
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
            tabContent.getChildren().addAll(newStocks, table);

            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1); //add all probably

            ButtonHandler buttonHandler = new ButtonHandler(folios.get(i));
            Task<Void> refreshThread = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception exc) {
                    }
                    Platform.runLater(() -> {
                        Timeline autoRefresh = autoRefresh(stocks, table, buttonHandler);
                        autoRefresh.setCycleCount(Timeline.INDEFINITE);
                        autoRefresh.play();
                    });
                    return null;
                }
            };

            new Thread(refreshThread).start();

            tickerSymbol_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, buttonHandler);
                }
            });
            numberShares_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, buttonHandler);
                }
            });
            name_txt.setOnKeyPressed(a -> {
                if (a.getCode().equals(KeyCode.ENTER)) {
                    handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, buttonHandler);
                }
            });
            add.setOnAction(a -> {
                handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, buttonHandler);
            });

            refresh.setOnAction(a -> {
                ObservableList<StockModel> refreshedStocks = buttonHandler.mainRefresh(stocks);
                for (int j = 0; j < refreshedStocks.size(); j++) {
                    stocks.set(j, refreshedStocks.get(j));
                }
            });

            table.setRowFactory(a -> {
                TableRow<StockModel> row = new TableRow<>();
                row.setOnMouseClicked(e -> {
                    if (e.getClickCount() == 2 && (!row.isEmpty())) {
                        EditStockPopup popupEdit = new EditStockPopup(row.getItem(), folios.get(finalI).getName());
                        Stage popup = popupEdit.popup();
                        popup.showAndWait();
                    }
                    if (!row.isEmpty()) {
                        StockModel refreshedStock = buttonHandler.soloRefresh(row.getItem());
                        if (refreshedStock != null)
                            if (stocks.contains(refreshedStock))
                                stocks.set(stocks.indexOf(refreshedStock), refreshedStock);
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

        menuSave.setOnAction(e -> {
            if (folios.size() > 0) {
                FileChooser fc = new FileChooser();
                fc.setTitle("Save Folio");
                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FOLIO files (*.folio)", "*.folio"));
                File file = fc.showSaveDialog(primaryStage);

                if (file != null) {
                    System.out.println(tabpane.getSelectionModel().getSelectedIndex());
                    folios.get(tabpane.getSelectionModel().getSelectedIndex()).save(file.toString());
                }
            } else {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("No Folio open");
                error.setHeaderText("No Folio open");
                error.setContentText("Please Create a Folio or load one from disk before saving");
                error.showAndWait();
            }
        });

        menuLoad.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Load Folio");
            File file = fc.showOpenDialog(primaryStage);
            if (file != null) {
                FolioModel loadedFolio = FolioModel.load(file.toString());
                if(loadedFolio != null) {
                    folios.add(FolioModel.load(file.toString()));
                    try {
                        start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
            }

                Alert addedError = new Alert(Alert.AlertType.ERROR);
                addedError.setTitle("Error");
                addedError.setHeaderText("Error loading your folio");
                addedError.setContentText("Please make sure you have selected the correct file");
                System.out.println("Failed to load file");
                addedError.showAndWait();

        });
    }

    private Timeline autoRefresh(ObservableList<StockModel> stocks, TableView<StockModel> table, ButtonHandler buttonHandler) {
        return new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
            System.out.println("\nRefreshing stonks");
            ObservableList<StockModel> refreshedStocks = buttonHandler.mainRefresh(stocks);
            for (int j = 0; j < refreshedStocks.size(); j++) {
                stocks.set(j, refreshedStocks.get(j));
            }
            System.out.println("stocks refreshed");
        }));
    }

    private void handleAdd(TextField name_txt, TextField tickerSymbol_txt, TextField numberShares_txt, ObservableList<StockModel> stocks, ButtonHandler buttonHandler) {
        StockModel stock = buttonHandler.mainAdd(name_txt.getText(), tickerSymbol_txt.getText(), numberShares_txt.getText());
        if (stock != null) {
            if (stocks.contains(stock))
                stocks.set(stocks.indexOf(stock), stock);
            else
                stocks.add(stock);
            name_txt.clear();
            tickerSymbol_txt.clear();
            numberShares_txt.clear();
        } else {
            Alert addedError = new Alert(Alert.AlertType.ERROR);
            addedError.setTitle("Error");
            addedError.setHeaderText("Error adding your stock");
            addedError.setContentText("Please make sure all the provided info is correct");
            System.out.println("Failed");
            addedError.showAndWait();
        }
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

    public static void main(String[] args) {
        launch(args);
    }

}
