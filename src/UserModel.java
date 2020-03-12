import java.util.ArrayList;
import java.util.List;

public class UserModel implements User{
private String username;
private List<iFolio> folios;

public UserModel(String username, ArrayList<iFolio> folios){
    this.username = username;
    this.folios = folios;
}

public String getUsername(){
    return username;
    }

public void setUsername(String newUsername){
    username = newUsername;
}

public void addFolio(iFolio newFolio){
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
