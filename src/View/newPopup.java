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
import javafx.stage.Stage;

public class newPopup extends Application {

    public String folioName;
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


        GridPane mainContent = new GridPane();

        Label nameLabel = new Label("Enter new Folio name: ");
        TextField name = new TextField("...");

        mainContent.add(nameLabel, 0, 0);
        mainContent.add(name, 1, 0);
        mainContent.setVgap(1);
        mainContent.setHgap(5);
        mainContent.setAlignment(Pos.CENTER);

        HBox buttons = new HBox();
        Button submit = new Button("Submit");
        buttons.getChildren().addAll(submit);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(3);

        popup.getChildren().addAll(mainContent, buttons);
        popup.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(popup, 400, 100));

        submit.setOnAction(e->{
            folioName = name.getText();
            stage.close();
        });

        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
