package backend.services;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import resources.SetPreferences;

public class SalesServices {

    private final String pathFileProduct = "./src/backend/data/ProductData.txt";
    private final String pathFileProductNow = "./src/backend/data/NowProduct.txt";

    public void minusStock(String[][] productInCart) {

        try {
            String[][] productMinus = new InventoryService().getAllProductData();
            String[][] productMinusNow = new InventoryService().getProductDataNow();

            for (String[] cartItem : productInCart) {
                String productId = cartItem[5];
                int amountSold = Integer.parseInt(cartItem[3]);
                
                for (String[] product : productMinus) {
                    if (product[0].equals(productId)) {
                        int currentStock = Integer.parseInt(product[4]);
                        product[4] = String.valueOf(currentStock - amountSold);
                        break;
                    }
                }
                for (String[] product : productMinusNow) {
                    if (product[0].equals(productId)) {
                        int currentStock = Integer.parseInt(product[4]);
                        product[4] = String.valueOf(currentStock - amountSold);
                        break;
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileProduct))) {
                for (String[] product : productMinus) {
                    if (product != null && product.length > 0) {
                        writer.write(String.join(",", product) + "\n");
                    }
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFileProductNow))) {
                for (String[] product : productMinusNow) {
                    if (product != null && product.length > 0) {
                        writer.write(String.join(",", product) + "\n"); 
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] genDataProduct(String id) {
        for (String[] recode : new InventoryService().getAllProductData()) {
            if (recode[0].equals(id)) {
                return recode;
            }
        }
        return null;
    }

    public double CalDisCount(String id, int amount) {
        String[] product = genDataProduct(id);
        double discount = Double.parseDouble(product[3]);
        double price = Double.parseDouble(product[2]);
        return ((discount / 100) * price * amount);
    }

    public void NotificationLowProduct(String[][] dataInCart) {
        ArrayList<String> LowStock = new ArrayList<>();
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER,10,20));
        JLabel headText = new JLabel("Low Stock Product!!!");
        JTextArea display = new JTextArea();
        String text = "";
        JButton Exits = new JButton("OK");
        headText.setPreferredSize(new Dimension(400,40));
        headText.setHorizontalAlignment(JLabel.CENTER);
        headText.setFont(new SetPreferences().getFont(24));
        headText.setForeground(Color.RED);

        Exits.setPreferredSize(new Dimension(300,50));
        Exits.setBackground(null);
        Exits.setFont(new SetPreferences().getFont(20));

        area.setPreferredSize(new Dimension(300, 300));
        display.setPreferredSize(new Dimension(280, 270));
        display.setFont(new SetPreferences().getFont(16));

        display.setEditable(false);
        display.setBackground(null);
        display.setBorder(null);

        for (int c = 0; c < dataInCart.length; c++) {
            for (int i = 0; i < new InventoryService().getAllProductData().length;i++){
                if (dataInCart[c][5].equals(new InventoryService().getAllProductData()[i][0])) {
                    if (Integer.parseInt(new InventoryService().getAllProductData()[i][4]) <= 10) {
                        LowStock.add(new InventoryService().getAllProductData()[i][0]);
                    }
                }
            }
        }

        for (int c = 0; c < LowStock.size(); c++) {
            text +=  "- " + genDataProduct(LowStock.get(c))[1] + " left : " + genDataProduct(LowStock.get(c))[4] + " ";
        }

        display.setText(text);
        display.setFont(new SetPreferences().getFont(18));
        area.add(display);

        if (!display.getText().isEmpty()) {
            JFrame frame = new JFrame("Notification");
            frame.setSize(400, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout()); 
            panel.add(headText,BorderLayout.NORTH);
            panel.add(new JScrollPane(area), BorderLayout.CENTER); 
            panel.add(Exits, BorderLayout.SOUTH);

            frame.add(panel); // Add the panel to the frame
            Exits.addActionListener(e -> frame.dispose());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
