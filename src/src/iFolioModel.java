public interface iFolio{
    
    boolean create(String name);
    boolean delete(String name);
    ArrayList<StockModel> refresh(); //calls a refresh method in each stock and returns all the updated stocks in an ArrayList
    ArrayList<StockModel> sort(int sortCode, ArrayList<StockModel> stocks);
    boolean addStock(String ticker);
    boolean deleteStock(String ticker);
    float getFolioValue();
}
