package backend.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.components.FindBill;
import resources.Tools;

public class DashboardService {
    private DecimalFormat format = new DecimalFormat("#,###.##");
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

        for (String[] recode : tableData) {
            recode[6] = format.format(Double.parseDouble(recode[6]));
            recode[7] = format.format(Double.parseDouble(recode[7]));
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
                    if (!(new Tools().LinearSearch(dataOrder, History[line][0]))) {
                        dataOrder.add(History[line][0]);
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
                if (!(new Tools().LinearSearch(dataOrder, History[line][0]))) {
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

    public void refundBill(String id) {
        String[][] HistoryRefund = new InventoryService().getAllSalesHistory();
        int count = 0;
        for (int c = 0; c < HistoryRefund.length; c++) {
            if (!(HistoryRefund[c][0]).equals(id)) {
                count++;
            }
        }
        String[][] newData = new String[count][7];
        int index = 0;
        for (int c = 0; c < HistoryRefund.length; c++) {
            if (!HistoryRefund[c][0].equals(id)) {
                newData[index++] = HistoryRefund[c];
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/InMemoryStore.txt"))) {
            for (String[] recode : newData) {
                writer.write(recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + ","
                        + recode[5] + "," + recode[6] + "," + recode[7] + "\n");
            }
            refundBillEmp(id);
            JOptionPane.showMessageDialog(null, "Refund success!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error File");
        }
    }

    private void refundBillEmp(String BillID){
        String [][] HistoryEmpBill = new FindBill().pullStoreBillEmp();
        int count = 0;
        for (int c = 0; c < HistoryEmpBill.length; c++) {
            if (!(History[c][0]).equals(BillID)) {
                count++;
            }
        }
        String[][] newData = new String[count][2];
        int index = 0;
        for (int c = 0; c < HistoryEmpBill.length; c++) {
            if (!this.History[c][0].equals(BillID)) {
                newData[index++] = HistoryEmpBill[c];
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/StoreBillEmp.txt"))) {
            for (String[] recode : newData) {
                writer.write(recode[0] + "," + recode[1] + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error File");
        }
    }


}
