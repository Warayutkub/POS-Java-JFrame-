import javax.swing.*;
import java.awt.*;

public class ProductCardExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Product Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new FlowLayout()); // Layout for positioning

        // Product Image
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\xshar\\Desktop\\TEst\\src\\lockscreen.png"); // Change to your image path
        JLabel imageLabel = new JLabel(imageIcon);

        // Product Name
        JLabel nameLabel = new JLabel("Product Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Product Price
        JLabel priceLabel = new JLabel("$19.99");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(Color.RED);

        // Panel for Product Card
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Add border
        productPanel.setPreferredSize(new Dimension(200, 250)); // Set fixed size

        // Add components to panel
        productPanel.add(imageLabel, BorderLayout.CENTER);
        productPanel.add(nameLabel, BorderLayout.NORTH);
        productPanel.add(priceLabel, BorderLayout.SOUTH);

        // Add product panel to frame
        frame.add(productPanel);
        frame.setVisible(true);
    }
}
