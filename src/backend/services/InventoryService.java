package backend.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InventoryService {
    private final String productPath = "./src/backend/data/ProductData.txt";
    private final String historyPath = "./src/backend/data/InMemoryStore.txt";

    public String[][] getAllProductData() {
        String[][] product = new String[0][0];
        try(BufferedReader bf = new BufferedReader(new FileReader(productPath))){
            String line;
            product = new String[(int) bf.lines().count()][6];
            int i = 0;
            BufferedReader bf2 = new BufferedReader(new FileReader(productPath));
            while ((line = bf2.readLine()) != null) {
                product[i] = line.split(",");
                i++;
            }
            bf2.close();
            return product;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public String[][] getAllSalesHistory() {
        String data[][] = new String[0][0];

        try {
            BufferedReader bf = new BufferedReader(new FileReader(historyPath));
            BufferedReader bf2 = new BufferedReader(new FileReader(historyPath));
            String line;
            data = new String[(int) bf.lines().count()][7];
            int i = 0;
            while ((line = bf2.readLine()) != null) {
                data[i] = line.split(",");
                i++;
            }
            bf.close();
            bf2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


}
