package client.components;

import backend.services.InventoryService;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import resources.Tools;

public class ManageProduct extends JPanel implements ActionListener {
    //  NewProduct
    Container container ,  container2;
    JLabel ButtonLabel, NewLabel , FromNewLabelName , FromNewLabelPrice , FromNewLabelQyt , ImagePreviewLabel , EditLabel , imageLabel;
    JButton NewButton , EdiButton, DelButton ,SaveButton,CancelButton , UploadImageButton;
    JPanel ButtonPanel , NewPanel ,FromNewPanelName,typeLabe ,EditPanel;  ;
    JTextField TextName , TextPrice ,TextQyt;
    String imagePath = "";
    InsertImage FromNewPanelPic  = new InsertImage();
    JComboBox<String> typeComboBox;
    int selectedTypeId = 0;
    String type[] ;

    public ManageProduct(){
        container = this; // Initialize the container variable
        container.setLayout(new BorderLayout());
    
        initGui();
    
        setSize(500, 350);
        setVisible(true);
    }

    public void initGui(){
        JPanel formPanel = new JPanel(); // รวมฟอร์มทั้งหมด
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        UploadPic();
        guiNew();         // สร้าง NewPanel

        formPanel.add(FromNewPanelPic);
        formPanel.add(NewPanel);


        container.add(formPanel, BorderLayout.CENTER);
        SaveCanButton();
        MainEditAndDelete(); // สร้าง EditPanel

        
    }

    public void UploadPic(){
        FromNewPanelPic = new InsertImage();
        container.add(FromNewPanelPic);
    }

    public void TypeProduct(){
        
    }

    public void guiNew(){
        NewPanel = new JPanel();
        NewPanel.setPreferredSize(new Dimension(450,110));
        NewPanel.setBorder(new LineBorder(Color.GRAY,0));
        NewLabel = new JLabel();
        NewPanel.add(NewLabel);

        FromNewPanelName = new JPanel();
        FromNewPanelName.setLayout(new GridLayout(4,2));
        FromNewPanelName.setPreferredSize(new Dimension(400,100));

        // Product
        FromNewLabelName = new JLabel("Enter Product Name : ");
        FromNewPanelName.add(FromNewLabelName);

        TextName = new JTextField(10);
        FromNewPanelName.add(TextName);
        
        // Price
        FromNewLabelPrice = new JLabel("Enter Product Price : ");
        FromNewPanelName.add(FromNewLabelPrice);

        TextPrice = new JTextField(10);
        FromNewPanelName.add(TextPrice);

        // Quantity
        FromNewLabelQyt = new JLabel("Enter Product Quantity : ");
        FromNewPanelName.add(FromNewLabelQyt);

        TextQyt = new JTextField(10);
        FromNewPanelName.add(TextQyt);

        //type
        JLabel typeLabel = new JLabel("Select Product Type : ");
        FromNewPanelName.add(typeLabel);

        String[] typeNames = {
        "Electronic",  // index 0 → id 1
        "Food",        // index 1 → id 2
        "Fashion",
        "Cosmetic",
        "Household",
        "Tool",
        "Sport",
        "Toy"
        };

        typeComboBox = new JComboBox<>(typeNames);
        FromNewPanelName.add(typeComboBox);

        // Listener เพื่ออัปเดต selectedTypeId
        typeComboBox.addActionListener(e -> {
        int index = typeComboBox.getSelectedIndex();
        selectedTypeId = index + 1; // เนื่องจาก index เริ่มที่ 0
        });

        NewPanel.add(FromNewPanelName);
        container.add(NewPanel);
    }


    public void SaveCanButton(){
        ButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // ปุ่มอยู่ตรงกลาง

        SaveButton = new JButton("Save");
        SaveButton.addActionListener(this);

        CancelButton = new JButton("Delete Data");
        CancelButton.addActionListener(this);

        ButtonPanel.add(SaveButton);
        ButtonPanel.add(CancelButton);

        container.add(ButtonPanel, BorderLayout.SOUTH);
    }

    private void showEditProductDialog(String id, String name, String price, String quantity, String imagePath, String typeIdStr) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Product", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Components
        JTextField nameField = new JTextField(name, 20);
        JTextField priceField = new JTextField(price, 20);
        JTextField quantityField = new JTextField(quantity, 20);
    
        // ComboBox Type
        JLabel typeLabel = new JLabel("Product Type:");
        String[] typeNames = {
            "Electronic", "Food", "Fashion", "Cosmetic",
            "Household", "Tool", "Sport", "Toy"
        };
        JComboBox<String> typeComboBox = new JComboBox<>(typeNames);
    
        // ตั้งค่าประเภทตามข้อมูลเดิม
        try {
            int selectedTypeIndex = Integer.parseInt(typeIdStr) - 1;
            if (selectedTypeIndex >= 0 && selectedTypeIndex < typeNames.length) {
                typeComboBox.setSelectedIndex(selectedTypeIndex);
            }
        } catch (NumberFormatException ignored) {}
    
        // Layout: Label + Field
    
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(nameField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        dialog.add(priceField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        dialog.add(quantityField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(typeLabel, gbc);
        gbc.gridx = 1;
        dialog.add(typeComboBox, gbc);
    
        // Save Button
        JButton saveBtn = new JButton("Save");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        dialog.add(saveBtn, gbc);
    
        saveBtn.addActionListener(ev -> {
            String newName = nameField.getText().trim();
            String newPrice = priceField.getText().trim();
            String newQty = quantityField.getText().trim();
            int newTypeId = typeComboBox.getSelectedIndex() + 1;
    
            if (!newName.isEmpty() && !newPrice.isEmpty() && !newQty.isEmpty()) {
                try {
                    java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("./src/backend/data/ProductData.txt"));
                    FileWriter writer = new FileWriter("./src/backend/data/ProductData.txt", false);
                    for (String line : lines) {
                        String[] parts = line.split(",");
                        if (parts.length >= 6 && parts[0].equals(id)) {
                            writer.write(id + "," + newName + "," + newPrice + ","+"0"+"," + newTypeId + "," + newQty + "," + imagePath + "\n");
                        } else {
                            writer.write(line + "\n");
                        }
                    }
                    writer.close();
                    JOptionPane.showMessageDialog(dialog, "Product updated!");
                    dialog.dispose();
                    refreshUI();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(dialog, "Failed to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields.");
            }
        });
    
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void MainEditAndDelete() {
        JPanel productListPanel = new JPanel();
    productListPanel.setLayout(new BoxLayout(productListPanel, BoxLayout.Y_AXIS));

    // ชื่อประเภทสินค้าแมปกับเลข Type
    String[] typeNames = {
        "Electronic", "Food", "Fashion", "Cosmetic",
        "Household", "Tool", "Sport", "Toy"
    };

    try {
        java.util.List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get("./src/backend/data/ProductData.txt"));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                String id = parts[0];
                String name = parts[1];
                String price = parts[2];
                String typeId = parts[5];
                String quantity = parts[4];

                int typeIndex = Integer.parseInt(typeId) - 1;
                String typeName = (typeIndex >= 0 && typeIndex < typeNames.length) ? typeNames[typeIndex] : "Unknown";

                // Panel แสดง 1 สินค้า
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                rowPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                rowPanel.setPreferredSize(new Dimension(800, 40));

                // ข้อมูลสินค้าแบบชิดซ้าย
                rowPanel.add(new JLabel("ID: " + id + "   "));
                rowPanel.add(new JLabel("Name: " + name + "   "));
                rowPanel.add(new JLabel("Price: " + price + "   "));
                rowPanel.add(new JLabel("Quantity: " + quantity + "   "));
                rowPanel.add(new JLabel("Type: " + typeName + "   "));

                // ปุ่ม Edit / Delete
                JButton editBtn = new JButton("Edit");
                JButton deleteBtn = new JButton("Delete");

                rowPanel.add(editBtn, BorderLayout.EAST);
                rowPanel.add(deleteBtn,BorderLayout.EAST);

                // Action ปุ่ม Edit
                editBtn.addActionListener(ev -> {
                    showEditProductDialog(id, name, price, quantity, parts[5], typeId);
                });

                // Action ปุ่ม Delete
                deleteBtn.addActionListener(ev -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Delete product \"" + name + "\"?", "Confirm", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            java.util.List<String> updated = new java.util.ArrayList<>();
                            for (String l : lines) {
                                if (!l.startsWith(id + ",")) {
                                    updated.add(l);
                                }
                            }
                            java.nio.file.Files.write(java.nio.file.Paths.get("./src/backend/data/ProductData.txt"), updated);
                            JOptionPane.showMessageDialog(this, "Deleted!");
                            refreshUI();
                        } catch (IOException ex) {
                        }
                    }
                });

                productListPanel.add(rowPanel);
            }
        }
    } catch (IOException e) {
    }

    JScrollPane scrollPane = new JScrollPane(productListPanel);
    scrollPane.setPreferredSize(new Dimension(850, 250));
    container.add(scrollPane, BorderLayout.EAST);
    }


    private void refreshUI() {
        container.removeAll();
        initGui();
        container.revalidate();
        container.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == SaveButton) {
            String name = TextName.getText().trim();
            String price = TextPrice.getText().trim();
            String quantity = TextQyt.getText().trim();

            if (!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                String invet[][] = new InventoryService().getAllProductData();
                String newId = new Tools().genNewId(invet);
                try (FileWriter writer = new FileWriter("./src/backend/data/ProductData.txt", true)) {
                    writer.write("\n"+ newId + "," + name + "," + price + ","+"0"+"," + quantity + "," + selectedTypeId + "," + FromNewPanelPic.getPath() + "\n");
                    JOptionPane.showMessageDialog(this, "Product saved successfully!");
                    clearForm();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }

        } else if (source == CancelButton) {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to clear your data?", "confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                clearForm();
            }
        }
        else if (source == UploadImageButton) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        imagePath = selectedFile.getAbsolutePath();

        // แสดง preview
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImagePreviewLabel.setIcon(new ImageIcon(img));
        ImagePreviewLabel.setText("");
    }
}
    }
    private void clearForm() {
        TextName.setText("");
        TextPrice.setText("");
        TextQyt.setText("");
    }


    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        Container container = jFrame.getContentPane();
        container.add(new ManageProduct());
        container.setSize(450,200);
        jFrame.setSize(450, 550);
        container.setLayout(new FlowLayout());
        jFrame.setVisible(true);

    }
}
