package Controller;

import Model.FolioModel;
import Model.iFolioModel;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.Optional;

public class FolioAdder {

    public FolioAdder() {
    }

    public iFolioModel addFolio(int id, String name) {
        return new FolioModel(id, name);
    }
}
