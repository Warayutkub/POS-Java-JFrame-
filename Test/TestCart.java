import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.components.Cart;
import client.components.ProductCard;

public class TestCart extends JFrame{
    private Cart cart = new Cart();

    public TestCart() {
        CreateGUI();
        CreateWindow();
    }

    private void CreateWindow() {
        setSize(900, 700);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void CreateGUI() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.setBackground(Color.BLUE);
        container.setPreferredSize(new Dimension(200,200));
        container.add(Body());
        container.add(cart,BorderLayout.EAST);
    }
    
    private JPanel Body(){
        JPanel Body = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Body.add(new ProductCard(100,146,"1",cart),BorderLayout.CENTER);
        Body.add(new ProductCard(100,146,"2",cart),BorderLayout.CENTER);
        return Body;
    }
}
