package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import backend.services.InventoryService;
import client.components.Cart;
import client.components.ProductCard;

public class MainFrame extends JFrame{
    private Cart cart = new Cart();
    private String[][] inventoryProduct = new InventoryService().getAllProductData();
    
    public MainFrame(){
        CreateGui();
        SetupWindow();
    }

    private void SetupWindow(){
        setTitle("POS");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setResizable(false);
        setVisible(true);
    }

    private void CreateGui(){
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(cart,BorderLayout.EAST);
        container.add(TopBar(),BorderLayout.NORTH);
        container.add(DisplayProduct(),BorderLayout.CENTER);
        container.add(SideBar(),BorderLayout.WEST);
    }

    private JPanel TopBar(){
        JPanel area = new JPanel(new FlowLayout());
        area.setPreferredSize(new Dimension(1000,50));
        area.setBackground(Color.BLUE);
        return area;
    }

    private JPanel SideBar(){
        JPanel area = new JPanel(new FlowLayout());
        area.setPreferredSize(new Dimension(150,500));
        area.setBackground(Color.BLACK);
        return area;
    }

    private JPanel DisplayProduct(){
        JPanel area = new JPanel(new FlowLayout(FlowLayout.LEADING));
        for (String[] product : inventoryProduct){
            area.add(new ProductCard(product[0], cart));
        }
        return area;
    }

}
