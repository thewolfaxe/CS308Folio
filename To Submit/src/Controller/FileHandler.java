package Controller;

import Model.FolioModel;
import Model.iFolioModel;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class FileHandler {
    FileChooser fc;
    Stage stage;

    public FileHandler() {
        fc = new FileChooser();
        stage = new Stage();
    }

    public boolean save(iFolioModel folio) {
        fc.setTitle("Save Folio");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FOLIO files (*.folio)", "*.folio"));
        File file = fc.showSaveDialog(stage);

        if (file != null) {
            System.out.println("Cant save folio: " + folio.getName());
            folio.save(file.toString());
            return true;
        }
        return false;
    }

    public ArrayList<iFolioModel> load(ArrayList<iFolioModel> folios) {
        fc.setTitle("Load Folio");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("FOLIO files (*.folio)", "*.folio"));
        File file = fc.showOpenDialog(stage);;

        if (file != null) {
                iFolioModel loadedFolio = FolioModel.load(file.toString());
                if (loadedFolio != null) {
                    folios.add(FolioModel.load(file.toString()));
                }else {
                    errorAlert("Error", "We had a problem loading your folio", "Your .folio file " +
                                    "may be out of date, or you may have selected the wrong file. Check console log for more",
                            "Load returned null");
                }
        }

        return folios;
    }

    public void errorAlert(String title, String header, String content, String consolePrint) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle(title);
        error.setHeaderText(header);
        error.setContentText(content);
        System.out.println(consolePrint);
        error.showAndWait();
    }
}
