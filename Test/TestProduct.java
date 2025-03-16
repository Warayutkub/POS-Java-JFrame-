import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import client.components.Cart;
import client.components.ProductCard;

public class TestProduct extends JFrame {
    public TestProduct() {
        CreateGUI();
        CreateWindow();
    }

    private void CreateWindow() {
        setSize(900, 500);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void CreateGUI() {
        int cardWidth = new ProductCard().getWidth();
        int cardHeight = new ProductCard().getHeight();
        int row = 1;
        int column = 3;

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setPreferredSize(new Dimension((cardWidth + 10) * column, (cardHeight + 10) * 4));
        panel.setBorder(new LineBorder(Color.pink));
        Container c = getContentPane();
        c.setLayout(new FlowLayout(FlowLayout.CENTER));

        for (int i = 0; i < 12; i++) {
            panel.add(new ProductCard(100,146,"1",new Cart()));
        }
        
        JScrollPane scroller = new JScrollPane(panel);
        scroller.setPreferredSize(new Dimension((cardWidth + 10) * column,((cardHeight + 10) * 4)/4*row));
        System.out.println((cardWidth + 10) * column);
        c.add(scroller, c);

    }
}