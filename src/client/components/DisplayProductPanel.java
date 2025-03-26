package client.components;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.services.InventoryService;

public class DisplayProductPanel {
    private String[][] Products = new InventoryService().getAllProductData();

    
    public JScrollPane getPanel(Cart cart, String type) {
        switch (type) {
            case "all" -> {return AllPanel(cart);}
            case "1","2","3","4","5","6","7","8" -> {return simplePanel(cart, type);}
        }
        return null; 
    }
    
    public JScrollPane getSearchPanel(Cart cart,String name){
        ArrayList<String> soldOut = new ArrayList<>();
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int c = 0;
        for (String[] recode : Products) {
            String nameLowerInventory = recode[1].toLowerCase();
            name = name.toLowerCase();
            if (nameLowerInventory.contains(name)) {
                if (recode[4].equals("0")) {
                    soldOut.add(recode[0]);
                    c++;
                } else {
                    panel.add(new ProductCard(recode[0], cart));
                    c++;
                }
            }
        }

        for (int i = 0;i < soldOut.size();i++){
            panel.add(new ProductCard(soldOut.get(i),cart));
        }

        int column = 5;
        int row = (int) Math.ceil((double) c / column);
        panel.setPreferredSize(new Dimension((column*215)+10, (255 * row)+10));
        scrollPane.setPreferredSize(new Dimension((column*172)+5,255*row));
        area.setPreferredSize(new Dimension((column*215)+10,255*row));
        area.add(panel);
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JScrollPane AllPanel(Cart cart) {
        ArrayList<String> soldOut = new ArrayList<>();
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setBackground(null);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int c = 0;
        for (String[] recode : Products) {
            if (recode[4].equals("0")) {
                soldOut.add(recode[0]);
                c++;
            } else {
                panel.add(new ProductCard(recode[0], cart));
                c++;
            }
        }

        for (int i = 0; i < soldOut.size(); i++) {
            panel.add(new ProductCard(soldOut.get(i), cart));
        }

        int column = 5;
        int row = (int) Math.ceil((double) c / column);
        panel.setPreferredSize(new Dimension((column*215)+10, (255 * row)+10));
        scrollPane.setPreferredSize(new Dimension((column*172)+5,255*row));
        area.setPreferredSize(new Dimension((column*215)+10,255*row));
        area.add(panel);
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        return scrollPane;
    }

    private JScrollPane simplePanel(Cart cart, String type) {
        ArrayList<String> soldOut = new ArrayList<>();
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        int c = 0;
        for (String[] recode : Products) {
            if (recode[5].equals(type)) {
                if (recode[4].equals("0")) {
                    soldOut.add(recode[0]);
                    c++;
                } else {
                    panel.add(new ProductCard(recode[0], cart));
                    c++;
                }
            }
        }

        for (int i = 0;i < soldOut.size();i++){
            panel.add(new ProductCard(soldOut.get(i),cart));
        }

        int column = 5;
        int row = (int) Math.ceil((double) c / column);
        panel.setPreferredSize(new Dimension((column*215)+10, (255 * row)+10));
        scrollPane.setPreferredSize(new Dimension((column*172)+5,255*row));
        area.setPreferredSize(new Dimension((column*215)+10,255*row));
        area.add(panel);
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        return scrollPane;
    }

}