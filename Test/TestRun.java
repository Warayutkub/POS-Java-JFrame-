import backend.services.AuthService;
import backend.services.InventoryService;
import client.Dashboard;
import resources.Tools;

public class TestRun {
    public static void main(String[] args) {
        // new TestProduct();
        // new ProductCardWithImageSave();
        // new Dashboard().On();
        // new Tools().genNewId(new InventoryService().getAllProductData());
        // new Tools().genNewId(new AuthService().getAllUserData("user"));
        // new Tools().genNewId(new AuthService().getAllUserData("emp"));
        new Tools().SaveFileCopy("./src/backend/data/images/Default_image.png","./Test/Default_image.png");
    }
} 
 