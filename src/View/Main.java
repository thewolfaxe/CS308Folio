package View;

import Controller.FileHandler;
import Controller.FolioAdder;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    private ArrayList<iFolioModel> folios = new ArrayList<>(); // all current opened folio's
    VBox vBox;
    AtomicReference<TabPane> tabpane;
    FileHandler fileHandler;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FolioTracker");
        vBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem menuNew = new MenuItem("New");
        MenuItem menuSave = new MenuItem("Save Folio");
        MenuItem menuLoad = new MenuItem("Load Folio");
        MenuItem menuExit = new MenuItem("Exit");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        tabpane = new AtomicReference<>(setupFolios());

        fileMenu.getItems().addAll(menuNew, menuSave, menuLoad, separator, menuExit);
        menuBar.getMenus().add(fileMenu);
        vBox.getChildren().add(menuBar);

        menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
        menuNew.setOnAction(e -> {
            addNewFolio();
            TabPane newTabs = setupFolios();
            tabpane.get().getTabs().setAll(newTabs.getTabs());
        });

        fileHandler = new FileHandler();

        menuSave.setOnAction(e -> {
            if (folios.size() > 0) {
                if (fileHandler.save(folios.get(tabpane.get().getSelectionModel().getSelectedIndex()))) {
                    System.out.println("File saved successfully");
                }
            } else {
                errorAlert("Error", "No Folio open", "Please create or load a folio before saving",
                        "No folio detected to save");
            }
        });

        menuLoad.setOnAction(e -> {
            folios = fileHandler.load(folios);
            TabPane newTabs = setupFolios();
            tabpane.get().getTabs().setAll(newTabs.getTabs());
        });

        menuExit.setOnAction(e -> {
            if (folios.size() != 0) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Quitting");
                warning.setHeaderText("There are still folios open");
                warning.setContentText("Please close all folios before exiting");
                warning.showAndWait();
            }else
                System.exit(0);
        });

        primaryStage.setOnCloseRequest(close -> {
            if (folios.size() != 0) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Quitting");
                warning.setHeaderText("There are still folios open");
                warning.setContentText("Please close all folios before exiting");
                warning.showAndWait();
                close.consume();
            }
        });


        vBox.getChildren().add(tabpane.get());
        primaryStage.setScene(new Scene(vBox, 1000, 700));
        primaryStage.show();
    }

    public void errorAlert(String title, String header, String content, String consolePrint) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(header);
        error.setContentText(content);
        System.out.println(consolePrint);
        error.showAndWait();
    }

    public void autoRefreshSetup(ObservableList<iStockModel> stocks, ObservableList<iFolioModel> totalValue, RefreshHandler refreshHandler) {
        Task<Void> refreshThread = new Task<>() {
            @Override
            protected Void call() {
                Platform.runLater(() -> {
                    Timeline autoRefresh = autoRefresh(stocks, totalValue, refreshHandler);
                    autoRefresh.setCycleCount(Timeline.INDEFINITE);
                    autoRefresh.play();
                });

                return null;
            }
        };

        new Thread(refreshThread).start();
    }

    private Timeline autoRefresh(ObservableList<iStockModel> stocks, ObservableList<iFolioModel> totValue, RefreshHandler refreshHandler) {
        return new Timeline(new KeyFrame(Duration.seconds(60), actionEvent -> {
            System.out.println("\nRefreshing stocks");
            ObservableList<iStockModel> refreshedStocks = refreshHandler.stockRefresh(stocks);
            for (int j = 0; j < refreshedStocks.size(); j++)
                stocks.set(j, refreshedStocks.get(j));

            ObservableList<iFolioModel> resfreshedTotal = refreshHandler.totalRefresh(totValue);
            for (int j = 0; j < resfreshedTotal.size(); j++)
                totValue.set(j, resfreshedTotal.get(j));
            System.out.println("stocks refreshed");
        }));
    }

    private void handleAdd(TextField name_txt, TextField tickerSymbol_txt, TextField numberShares_txt, ObservableList<iStockModel> stocks, NewStockHandler newStockHandler, RefreshHandler refresh, ObservableList<iFolioModel> totals) {
        iStockModel stock = newStockHandler.addStock(name_txt.getText(), tickerSymbol_txt.getText().toUpperCase().trim(), numberShares_txt.getText(), stocks);
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

    private void addNewFolio() {
        FolioAdder adder = new FolioAdder();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Folio");
        dialog.setHeaderText("Enter folio name");
        dialog.setContentText("Folio Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            String folioName = result.get();
            folios.add(adder.addFolio(folios.size(), folioName));
        });
    }

    public TabPane setupFolios() {
        TabPane tabpane = new TabPane();

        if (folios.size() == 0) {
            Tab tab1 = new Tab("*No Folio*");
            VBox tabContent = new VBox();
            Label noFolio = new Label("No Folio open");

            tabContent.getChildren().add(noFolio);
            tab1.setContent(tabContent);
            tabpane.getTabs().add(tab1);
        } else {
            for (int i = 0; i < folios.size(); i++) {
                Tab tab = setUpTab(folios.get(i));

                tab.setOnCloseRequest(e -> { // On close, prompt save before exit
                    ButtonType workSaved = new ButtonType("Save Folio");
                    ButtonType goBack = new ButtonType("Quit without saving");
                    ButtonType cancel = new ButtonType("Cancel");

                    iFolioModel currentFolio = null;
                    for (iFolioModel folio : folios) {
                        if (tab.getId().equals(String.valueOf(folio.getId()))) {
                            currentFolio = folio;
                            break;
                        }
                    }

                    if(currentFolio != null) {
                        Alert warning = new Alert(Alert.AlertType.CONFIRMATION, "", workSaved, goBack, cancel);
                        warning.setTitle("Quitting");
                        warning.setHeaderText("Quitting without saving folio " + currentFolio.getName());
                        warning.setContentText("Would you like to save your folio?");
                        warning.showAndWait().ifPresent(response -> {
                            iFolioModel current = null;
                            for (iFolioModel folio : folios) {
                                if (tab.getId().equals(String.valueOf(folio.getId()))) {
                                    current = folio;
                                    break;
                                }
                            }

                            if (current != null) {
                                if (response == workSaved) {
                                    fileHandler.save(current);
                                    folios.remove(current);
                                }else if (response == cancel)
                                    e.consume();
                                else
                                    folios.remove(current);
                            }else
                                System.out.println("If this shows ive done fucked it");
                        });
                    }else
                        System.out.println("If this shows ive once again have done fucked it");
                });
                tabpane.getTabs().add(tab);
            }
        }
        return tabpane;
    }

    public Tab setUpTab(iFolioModel folio) {
        Tab tab = new Tab(folio.getName());
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

        ObservableList<iStockModel> stocks = FXCollections.observableArrayList(folio.getStocks());

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

        change.setCellFactory(a -> {
            return new TableCell<iStockModel, Double>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setBackground(null);
                        setStyle("");
                    } else {
                        double num = item;
                        num = Math.round(num*100)/100.00;
                        if (num < 0) {
                            setText(Double.toString(num));
                            setBackground(new Background(new BackgroundFill(Color.RED,
                                    null, null)));
                        } else {
                            setText(Double.toString(num));
                            setBackground(new Background(new BackgroundFill(Color.GREEN,
                                    null, null)));
                        }
                    }
                }
            };
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

        ObservableList<iFolioModel> test = FXCollections.observableArrayList(folio);
        TableView<iFolioModel> tot = new TableView<>();
        tot.setItems(test);
        TableColumn<iFolioModel, Double> total = new TableColumn<>("Total Value");
        total.setMinWidth(100);
        total.setCellValueFactory(new PropertyValueFactory<>("value"));
        tot.getColumns().add(total);

        tabContent.getChildren().addAll(newStocks, table, tot);

        RefreshHandler refreshHandler = new RefreshHandler(folio);
        NewStockHandler newStockHandler = new NewStockHandler(folio);

        autoRefreshSetup(stocks, test, refreshHandler);

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
            System.out.println("Stocks in array before add in " + folio.getName());
            handleAdd(name_txt, tickerSymbol_txt, numberShares_txt, stocks, newStockHandler, refreshHandler, test);
        });

        refresh.setOnAction(a -> {
            ObservableList<iStockModel> refreshedStocks = refreshHandler.stockRefresh(stocks);
            for (int j = 0; j < refreshedStocks.size(); j++)
                stocks.set(j, refreshedStocks.get(j));

            ObservableList<iFolioModel> refreshedTotal = refreshHandler.totalRefresh(test);
            for (int j = 0; j < refreshedTotal.size(); j++)
                test.set(j, refreshedTotal.get(j));
        });

        table.setRowFactory(a -> {
            TableRow<iStockModel> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    EditStockPopup popupEdit = new EditStockPopup(row.getItem(), folio);
                    int before = folio.getStocks().size();
                    folio.setStocks(popupEdit.popup());
                    int after = folio.getStocks().size();

                    if (before > after) {
                        System.out.println("IM GETTUIG HERE");
                        stocks.remove(row.getItem());
                    }

                    ObservableList<iStockModel> refreshedStocks = refreshHandler.stockRefresh(stocks);
                    for (int j = 0; j < refreshedStocks.size(); j++)
                        stocks.set(j, refreshedStocks.get(j));

                    ObservableList<iFolioModel> refreshedTotal = refreshHandler.totalRefresh(test);
                    for (int j = 0; j < refreshedTotal.size(); j++)
                        test.set(j, refreshedTotal.get(j));
                }
            });
            return row;
        });

        tab.setContent(tabContent);
        tab.setId(String.valueOf(folio.getId()));
        return tab;

    }

    public static void main(String[] args) {
        launch(args);
    }

}
