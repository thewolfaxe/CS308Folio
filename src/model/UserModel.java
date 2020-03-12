package model;

import model.iFolioModel;
import model.iUserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModel implements iUserModel {
private String username;
private List<iFolioModel> folios;

public UserModel(String username, ArrayList<iFolioModel> folios){
    this.username = username;
    this.folios = folios;
}

public String getUsername(){
    return username;
    }

public void setUsername(String newUsername){
    username = newUsername;
}

public void addFolio(iFolioModel newFolio){
    folios.add(newFolio);
}

    /**
     * Method to delete a specified folio from the folio list
     * @param folioName the name of the folio to be removed
     * @return 1 if the folio existed and was removed. -1 if not
     */
    public int deleteFolio(String folioName){
    for(int i = 0; i < folios.size(); i++){
        if(folios.get(i).getName().equals(folioName)){
            folios.remove(i);
            return 1;
        }
    }
    return -1;
}

}
