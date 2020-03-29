package Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UserModelTests {
    public UserModel testUser;
    public ArrayList<iFolioModel> testFolios = new ArrayList<>();

    @BeforeEach
    public void populateUser(){
        iFolioModel testFolio1 = new FolioModel(1, "test1");
        iFolioModel testFolio2 = new FolioModel(2, "test2");
        testFolios.add(testFolio1);
        testFolios.add(testFolio2);
        testUser = new UserModel("test", testFolios);
    }

    @Test
    public void setUsernameTest(){
        testUser.setUsername("newTest");
        Assertions.assertEquals(testUser.getUsername(), "newTest");
    }

    @Test
    public void addFolioTest(){
        iFolioModel newFolio = new FolioModel(3, "newTest");
        testUser.addFolio(newFolio);
        Assertions.assertTrue(testUser.getFolios().contains(newFolio));
    }

    @Test
    public void deleteFolioTestExists(){
        testUser.deleteFolio("test2");
        Assertions.assertFalse(testUser.getFolios().contains(new FolioModel(1, "test2")));
    }

    @Test
    public void deleteFolioTestDoesNotExist(){
        Assertions.assertEquals(-1, testUser.deleteFolio("blah"));
    }
}
