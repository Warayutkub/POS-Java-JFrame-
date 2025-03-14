package backend.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class InventoryService {
    private final String productPath = "../data/ProductData.txt";
    private final String historyPath = "../data/InMemoryStore.txt";

    public String[][] getAllProductData() {
        String[][] product = new String[0][0];
        try (BufferedReader br = new BufferedReader(
                new FileReader(getClass().getResource(productPath).getFile()))) {
            String line;
            product = new String[(int) br.lines().count()][7];
            int i = 0;
            try (BufferedReader br2 = new BufferedReader(
                    new FileReader(getClass().getResource(productPath).getPath()))) {
                while ((line = br2.readLine()) != null) {
                    product[i] = line.split(",");
                    i++;
                }
            }
            return product;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public String[][] getAllSalesHistory() {
        String data[][] = new String[0][0];
        try (BufferedReader bf = new BufferedReader(
                new FileReader(getClass().getResource(historyPath).getPath()))) {
            String line;
            data = new String[(int) bf.lines().count()][7];
            int i = 0;
            try (BufferedReader bf2 = new BufferedReader(
                    new FileReader(getClass().getResource(historyPath).getPath()))) {
                while ((line = bf2.readLine()) != null) {
                    data[i] = line.split(",");
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
