package backend.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import resources.Tools;

public class DashboardService {
    private LocalDateTime now = LocalDateTime.now();
    private String History[][] = new InventoryService().getAllSalesHistory();
    private String Inventory[][] = new InventoryService().getAllProductData();
    private String Users[][] = new AuthService().getAllUserData("user");

    public String[][] getTable(String date) {
        int count = 0;
        for (int line = 0; line < History.length; line++) {
            if (History[line][2].equals(date)) {
                count++;
            }
        }
        String[][] tableData = new String[count][8];

        int index = 0;
        for (int line = 0; line < History.length; line++) {
            if (History[line][2].equals(date)) {
                tableData[index] = History[line];
                index++;
            }
        }
        // Format
        for (int c = 0; c < tableData.length; c++) {
            for (int i = 0; i < Inventory.length; i++) {
                if (new Tools().LinearSearch(Inventory[i], tableData[c][5])) {
                    tableData[c][5] = Inventory[i][1];
                    break;
                }
            }
        }

        for (int c = 0; c < tableData.length; c++) {
            for (int i = 0; i < Users.length; i++) {
                if (new Tools().LinearSearch(Users[i], tableData[c][4])) {
                    tableData[c][4] = Users[i][1];
                    break;
                }
            }
        }

        for (int c = 0; c < tableData.length; c++) {
            switch (tableData[c][1]) {
                case "1" -> tableData[c][1] = "Electronic"; 
                case "2" -> tableData[c][1] = "Food";
                case "3" -> tableData[c][1] = "Fashion";
                case "4" -> tableData[c][1] = "Cosmetic";
                case "5" -> tableData[c][1] = "Household";
                case "6" -> tableData[c][1] = "Tool";
                case "7" -> tableData[c][1] = "Sport";
                case "8" -> tableData[c][1] = "Toy";
            }
        }
        return tableData;
    }

    public ArrayList<String> getChoice() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        ArrayList<String> date = new ArrayList<>();
        date.add(now.format(formatter));
        for (int i = 0; i < History.length; i++) {
            if (!date.contains(History[i][2])) {
                date.add(History[i][2]);
            }
        }

        return date;
    }

    public double getDataTopDashboard(String type, String date) {
        if (type.equals("order")) {
            ArrayList<String> dataOrder = new ArrayList<String>();
            for (int line = 0; line < History.length; line++) {
                if (History[line][2].equals(date)) {
                    if (!(new Tools().LinearSearch(dataOrder, date))){
                        dataOrder.add(date);
                    }
                }
            }
            return dataOrder.size();
        } else {
            double total = 0;
            for (int line = 0; line < History.length; line++) {
                if (History[line][2].equals(date)) {
                    total += Double.parseDouble(History[line][7]);
                }
            }
            return total;
        }

    }

    public double getDataAllForTopBoard(String type) {
        if (type.equals("order")) {
            ArrayList<String> dataOrder = new ArrayList<String>();
            for (int line = 0; line < History.length; line++) {
                System.out.println(Arrays.toString(dataOrder.toArray()));
                if (!(new Tools().LinearSearch(dataOrder, History[line][0]))){
                    dataOrder.add(History[line][0]);
                }
            }
            return dataOrder.size();
        } else {
            double total = 0;
            for (int line = 0; line < History.length; line++) {
                total += Double.parseDouble(History[line][7]);
            }
            return total;
        }
    }

}
