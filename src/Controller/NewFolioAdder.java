package Controller;

import Model.FolioModel;
import Model.iFolioModel;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Optional;

public class NewFolioAdder {
    ArrayList<iFolioModel> folios;

    public NewFolioAdder(ArrayList<iFolioModel> folios) {
        this.folios = folios;
    }

    public ArrayList<iFolioModel> addFolio() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Enter new stock name");
        dialog.setHeaderText("Enter stock name");
        dialog.setContentText("Stock Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            String newPopupField = result.get();
            if (folios.size() > 0) {
                folios.add(new FolioModel(folios.get(folios.size() - 1).getId() + 1, newPopupField));
            } else {
                folios.add(new FolioModel(0, newPopupField));

            }
        });
        return folios;
    }
}
