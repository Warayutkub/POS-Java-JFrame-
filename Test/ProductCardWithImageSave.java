import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProductCardWithImageSave {
    private JLabel imageLabel; // Label to display image
    private String savedImagePath; // Path of saved image

    public ProductCardWithImageSave() {
        // Create frame
        JFrame frame = new JFrame("Product Card");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 450);
        frame.setLayout(new FlowLayout());
        frame.setAlwaysOnTop(true);

        // Image placeholder
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Product name
        JLabel nameLabel = new JLabel("Product Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Product price
        JLabel priceLabel = new JLabel("$19.99");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        priceLabel.setForeground(Color.RED);

        // Button to select image
        JButton selectImageButton = new JButton("Select Image");
        selectImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        // Button to print saved image path
        JButton printPathButton = new JButton("Print Saved Image Path");
        printPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (savedImagePath != null) {
                    System.out.println("Saved Image Path: " + savedImagePath);
                } else {
                    System.out.println("No image saved.");
                }
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
        frame.add(printPathButton);

        frame.setVisible(true);
    }

    // Method to open file chooser, copy image to folder, and set image
    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String targetFolder = "images"; // Folder to save images
            File folder = new File(targetFolder);

            // Create folder if not exists
            if (!folder.exists()) {
                folder.mkdir();
            } 

            // Create destination file path
            File destinationFile = new File(folder, selectedFile.getName());

            try {
                // Copy file to the destination
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                savedImagePath = destinationFile.getAbsolutePath(); // Save the new path

                // Print path for debugging
                System.out.println("Image saved at: " + savedImagePath);

                // Resize image to fit label
                ImageIcon imageIcon = new ImageIcon(savedImagePath);
                Image image = imageIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error saving image.");
            }
        }
    }

 
}
