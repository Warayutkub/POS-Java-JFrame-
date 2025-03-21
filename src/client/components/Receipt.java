package client.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt extends JFrame {
    Container c;

    public Receipt() {
        super("Receipt");
        c = getContentPane();
        c.setLayout(new FlowLayout());
        receiptGUI();
        setSize(450, 450);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void receiptGUI() {

        String salesFilePath = "sales.txt";
        String productFilePath = "product.txt";
        String customerFilePath = "customers.txt";

        Object[][] data = readDataFromFile(salesFilePath, productFilePath);
        if (data.length == 0) {
            JOptionPane.showMessageDialog(this, "No Data in this File.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] columns = {"Qty", "Product", "Unit Price", "Total"};

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);  // Qty
        table.getColumnModel().getColumn(1).setPreferredWidth(167); // Product
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Unit Price
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Amount

        table.setPreferredScrollableViewportSize(new Dimension(500, 500));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // CALCULATOR TOTAL
        double totalPrice = calculateTotalPrice(data);
        double cash = 1500.00;
        double change = cash >= totalPrice ? cash - totalPrice : 0;

        String currentDate = getCurrentDate();

        // GET ID CUSTOMER
        String customerId = "C001";
        String memberName = getMemberNameFromFile(customerFilePath, customerId);

        // Header
        JLabel headerLabel = new JLabel("SHOP", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel memberNameLabel = new JLabel("Member: " + memberName);
        JLabel dateLabel = new JLabel("Date: " + currentDate);
        JLabel empIdLabel = new JLabel("Emp: EMP1234");

        // Payment Panel
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridLayout(3, 1));
        paymentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        Font myFont = new Font("Arial", Font.BOLD, 18);

        JLabel totalLabel = new JLabel("Total Price: " + totalPrice);
        totalLabel.setForeground(Color.RED);
        totalLabel.setFont(myFont);
        JLabel cashLabel = new JLabel("Cash: " + cash);
        cashLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel changeLabel = new JLabel("Change: " + change);
        changeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        paymentPanel.add(totalLabel);
        paymentPanel.add(cashLabel);
        paymentPanel.add(changeLabel);

        // Bottom Panel
        JLabel thankYouLabel = new JLabel("Thank you!", JLabel.CENTER);
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 20));
        thankYouLabel.setForeground(Color.BLACK);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(thankYouLabel, BorderLayout.NORTH);
        bottomPanel.add(empIdLabel, BorderLayout.EAST);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(headerLabel);
        panel.add(memberNameLabel);
        panel.add(dateLabel);
        panel.add(scrollPane);
        panel.add(paymentPanel);
        panel.add(bottomPanel);

        c.add(panel, BorderLayout.CENTER);

      
    }

    public Object[][] readDataFromFile(String salesFilePath, String productFilePath) {
        ArrayList<Object[]> dataList = new ArrayList<>();
        Map<String, String[]> productMap = readProductFile(productFilePath);

        try (BufferedReader br = new BufferedReader(new FileReader(salesFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 5) {
                    String receiptId = values[4].trim();  

                    String productId = values[0].trim();
                    int qty = Integer.parseInt(values[1].trim());
                    String customerId = values[2].trim();
                    String date = values[3].trim(); // Date 
                    String[] productInfo = productMap.get(productId);

                    if (productInfo != null) {
                        String productName = productInfo[0];
                        double price = Double.parseDouble(productInfo[1]);
                        double amount = qty * price;
                        dataList.add(new Object[]{qty, productName, price, amount});
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return dataList.toArray(new Object[0][0]);
    }

    // READ DATA PRODUCT
    public static Map<String, String[]> readProductFile(String filePath) {
        Map<String, String[]> productMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    String productId = values[0].trim();
                    String productName = values[1].trim();
                    String priceStr = values[2].trim();

                    // Remove commas
                    priceStr = priceStr.replace(",", "");
                    double price = Double.parseDouble(priceStr);

                    productMap.put(productId, new String[]{productName, String.valueOf(price)});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productMap;
    }

    // TOTAL
    public static double calculateTotalPrice(Object[][] data) {
        double total = 0.0;
        for (Object[] row : data) {
            total += (double) row[3];  
        }
        return total;
    }

    // DATE
    public static String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.now().format(formatter);
    }

    // ID TO NAME
    public static String getMemberNameFromFile(String filePath, String customerId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].trim().equals(customerId)) {
                    return values[1].trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        new Receipt();
    }
}