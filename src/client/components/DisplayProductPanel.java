package client.components;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.services.InventoryService;

public class DisplayProductPanel {

    public JScrollPane getPanel(Cart cart, String type, int width) {
        switch (type) {
            case "all" -> {
                return AllPanel(cart, width);
            }
            case "1", "2", "3", "4", "5", "6", "7", "8" -> {
                return simplePanel(cart, type, width);
            }
        }
        return null;
    }

    public JScrollPane getSearchPanel(Cart cart, String name, int width) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setBackground(null);
        
        int c = 0;
        String[][] products = new InventoryService().getProductDataNow();
        
        for (String[] recode : products) {
            String nameLowerInventory = recode[1].toLowerCase();
            String searchName = name.toLowerCase();
            if (nameLowerInventory.contains(searchName) && Integer.parseInt(recode[4]) > 0) {
                area.add(new ProductCard(recode[0], cart));
                c++;
            }
        }
        
        for (String[] recode : products) {
            String nameLowerInventory = recode[1].toLowerCase();
            String searchName = name.toLowerCase();
            if (nameLowerInventory.contains(searchName) && Integer.parseInt(recode[4]) == 0) {
                area.add(new ProductCard(recode[0], cart));
                c++;
            }
        }

        int column = 6;
        int row = (int) Math.ceil((double) c / column);
        int rowHeight = new ProductCard().getHeight();
        
        Dimension dim = new Dimension(width, (rowHeight * row)+(10*row));
        scrollPane.setPreferredSize(dim);
        area.setPreferredSize(dim);
        
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        return scrollPane;
    }

    private JScrollPane AllPanel(Cart cart, int width) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setBackground(null);
        
        String[][] products = new InventoryService().getProductDataNow();
        int c = products.length;
        
        for (String[] recode : products) {
            if (Integer.parseInt(recode[4]) > 0) {
                area.add(new ProductCard(recode[0], cart));
            }
        }

        for (String[] recode : products) {
            if (Integer.parseInt(recode[4]) == 0) {
                area.add(new ProductCard(recode[0], cart));
            }
        }

        int column = 6;
        int row = (int) Math.ceil((double) c / column);
        int rowHeight = new ProductCard().getHeight();
        
        Dimension dim = new Dimension(width, (rowHeight * row)+(10*row));
        scrollPane.setPreferredSize(dim);
        area.setPreferredSize(dim);
        
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        return scrollPane;
    }

    private JScrollPane simplePanel(Cart cart, String type, int width) {
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(area);
        area.setBackground(null);
        
        String[][] products = new InventoryService().getProductDataNow();
        int c = 0;
        
        for (String[] recode : products) {
            if (recode[5].equals(type)) {
                if (Integer.parseInt(recode[4]) > 0) {
                    area.add(new ProductCard(recode[0], cart));
                }
            }
        }

        for (String[] recode : products) {
            if (recode[5].equals(type)) {
                if (Integer.parseInt(recode[4]) == 0) {
                    area.add(new ProductCard(recode[0], cart));
                }
            }
        }

        int column = 6;
        int row = (int) Math.ceil((double) c / column);
        int rowHeight = new ProductCard().getHeight();
        
        Dimension dim = new Dimension(width, (rowHeight * row)+(10*row));
        scrollPane.setPreferredSize(dim);
        area.setPreferredSize(dim);
        
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        return scrollPane;
    }

}