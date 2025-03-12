import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProductCardWithImageUpload {
    private JLabel imageLabel; // Label to display image

    public ProductCardWithImageUpload() {
        // Create frame
        JFrame frame = new JFrame("Product Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new FlowLayout());

        // Image placeholder
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border

        // Product name
        JLabel nameLabel = new JLabel("Product Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Product price
        JLabel priceLabel = new JLabel("$19.99");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(Color.RED);

        // Button to select image
        JButton selectImageButton = new JButton("Insert Image");
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage(); // Open file chooser
            }
        });

        // Product panel
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());
        productPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        productPanel.setPreferredSize(new Dimension(200, 250));

        // Add components to panel
        productPanel.add(imageLabel, BorderLayout.CENTER);
        productPanel.add(nameLabel, BorderLayout.NORTH);
        productPanel.add(priceLabel, BorderLayout.SOUTH);

        // Add components to frame
        frame.add(productPanel);
        frame.add(selectImageButton);

        frame.setVisible(true);
    }

    // Method to open file chooser and set image
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

            // Resize image to fit label
            Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        }
    }

    public static void main(String[] args) {
        new ProductCardWithImageUpload();
    }
}
