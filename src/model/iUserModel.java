package model;

public interface iUserModel {
    String getUsername();
    void setUsername(String newUsername);
    void addFolio(iFolioModel newFolio);
    int deleteFolio(String folioName);

}
