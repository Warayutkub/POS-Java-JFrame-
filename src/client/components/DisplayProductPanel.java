package client.components;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import backend.services.InventoryService;

public class DisplayProductPanel {
    String[][] Products = new InventoryService().getAllProductData();
    Cart cart;

    public JPanel getPanel(Cart cart,String type){
        switch (type) {
            case "all" : return AllPanel(cart);
            case "1" : return simplePanel(cart,type);
            case "2" : return simplePanel(cart,type);
            case "3" : return simplePanel(cart,type);
            case "4" : return simplePanel(cart,type);
            case "5" : return simplePanel(cart,type);
            case "6" : return simplePanel(cart,type);
            case "7" : return simplePanel(cart,type);
            case "8" : return simplePanel(cart,type);
        }
        return null;
    }

    private JPanel AllPanel(Cart cart){
        this.cart = cart;
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        area.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scroller = new JScrollPane(panel);

        scroller.setBorder(null);

        int c = 0;
        for (String[] recode : Products){
            panel.add(new ProductCard(recode[0],cart));
            c++;
        }

        int column = 5;
        int row = (int) Math.ceil((double) c / column);
        panel.setPreferredSize(new Dimension(500, 250*row));
        scroller.setPreferredSize(new Dimension(1200,1000));
        area.add(scroller);

        return area;
    }

    private JPanel simplePanel(Cart cart,String type){
        this.cart = cart;
        JPanel area = new JPanel(new FlowLayout(FlowLayout.CENTER));
        area.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scroller = new JScrollPane(panel);

        scroller.setBorder(null);

        int c = 0;
        for (String[] recode : Products){
            if (recode[5].equals(type)){
                panel.add(new ProductCard(recode[0],cart));
                c++;
            }
        }

        int column = 5;
        int row = (int) Math.ceil((double) c / column);
        panel.setPreferredSize(new Dimension(500, 250*row));
        scroller.setPreferredSize(new Dimension(1200,1000));
        area.add(scroller);

        return area;
    }
    
}