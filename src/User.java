public interface User {
    String getUsername();
    void setUsername(String newUsername);
    void addFolio(iFolio newFolio);
    int deleteFolio(String folioName);

}
