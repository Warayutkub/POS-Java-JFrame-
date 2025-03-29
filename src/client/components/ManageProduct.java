package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import backend.models.Products.Product;
import backend.services.InventoryService;
import client.MainFrame;
import resources.SetPreferences;
import resources.Tools;

public class ManageProduct extends JPanel implements ActionListener {
    private int width = 0;
    private MainFrame mainFrame;
    private InsertImage insertImage = new InsertImage();
    private Product product = new Product();
    private JPanel body;

    public ManageProduct(MainFrame mainFrame, int width) {
        this.mainFrame = mainFrame;
        this.width = width;

        setLayout(new BorderLayout());
        add(Top(), BorderLayout.NORTH);
        body = body();
        add(body, BorderLayout.CENTER);
    }

    private JPanel Top() {
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,3,20));
        topPanel.setBackground(null);
        topPanel.setPreferredSize(new Dimension(width, 60));
        JButton newProductBtn = new JButton("New Product");
        JButton allBtn = new JButton("All");
        JButton typeBtnElectronic = new JButton("Electronic");
        JButton typeBtnFood = new JButton("Food");
        JButton typeBtnFashion = new JButton("Fashion");
        JButton typeBtnCosmetic = new JButton("Cosmetic");
        JButton typeBtnHousehold = new JButton("Household");
        JButton typeBtnTool = new JButton("Tool");
        JButton typeBtnSport = new JButton("Sport");
        JButton typeBtnToy = new JButton("Toy");

        for (JButton button : new JButton[] { newProductBtn, allBtn, typeBtnElectronic, typeBtnFood, typeBtnFashion, typeBtnCosmetic, typeBtnHousehold, typeBtnTool, typeBtnSport, typeBtnToy }) {
            if (button.getText().equals("New Product")) {
                button.setPreferredSize(new Dimension(150, 30));
                button.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
                button.setForeground(Color.WHITE);
            } else {
                button.setPreferredSize(new Dimension(100, 30));
                button.setBackground(Color.WHITE);
            }
        }
        newProductBtn.addActionListener(e -> {
            newProductDialog().setVisible(true);
        });

        typeBtnElectronic.addActionListener(e -> {
            refreshPage("Electronic");
        });

        typeBtnFood.addActionListener(e -> {
            refreshPage("Food");
        });

        typeBtnFashion.addActionListener(e -> {
            refreshPage("Fashion");
        });     

        typeBtnCosmetic.addActionListener(e -> {
            refreshPage("Cosmetic");
        });

        typeBtnHousehold.addActionListener(e -> {
            refreshPage("Household");
        });

        typeBtnTool.addActionListener(e -> {
            refreshPage("Tool");
        });

        typeBtnSport.addActionListener(e -> {
            refreshPage("Sport");
        });

        typeBtnToy.addActionListener(e -> {
            refreshPage("Toy");
        });

        allBtn.addActionListener(e -> {
            refreshPage();
        });

        topPanel.add(allBtn);
        topPanel.add(typeBtnElectronic);
        topPanel.add(typeBtnFood);
        topPanel.add(typeBtnFashion);
        topPanel.add(typeBtnCosmetic);
        topPanel.add(typeBtnHousehold);
        topPanel.add(typeBtnTool);
        topPanel.add(typeBtnSport);
        topPanel.add(typeBtnToy);
        topPanel.add(newProductBtn);
        return topPanel;
    }

    private JPanel body() {
        JPanel bodyPanel = new JPanel();
        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        bodyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bodyPanel.setPreferredSize(new Dimension(width, mainFrame.getHeight() - 100));

        int row = 0;
        for (String[] recode : new InventoryService().getProductDataNow()) {
            innerPanel.add(new Card(recode, mainFrame, width, this));
            row++;
        }

        innerPanel.setPreferredSize(new Dimension(width, row*42));
        bodyPanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(width, mainFrame.getHeight() - 100));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        return bodyPanel;
    }

    private JPanel body(String type) {
        JPanel bodyPanel = new JPanel();
        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        bodyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bodyPanel.setPreferredSize(new Dimension(width, mainFrame.getHeight() - 100));

        int row = 0;
        for (String[] recode : new InventoryService().getProductDataNow()) {
            if (recode[5].equals(transformType(type, "number"))) {
                innerPanel.add(new Card(recode, mainFrame, width, this));
                row++;
            }
        }

        innerPanel.setPreferredSize(new Dimension(width, row*42));
        bodyPanel.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(width, mainFrame.getHeight() - 100));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        return bodyPanel;
    }

    @SuppressWarnings("rawtypes")
    private JDialog newProductDialog() {
        JDialog newProductDialog = new JDialog(mainFrame, "New Product", true);
        newProductDialog.setSize(new Dimension(450, 670));
        newProductDialog.setLocationRelativeTo(mainFrame);
        newProductDialog.setLayout(new FlowLayout(FlowLayout.CENTER));
        newProductDialog.add(insertImage);
        JPanel areaForm = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setPreferredSize(new Dimension(380, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(380, 30));
        JLabel priceLabel = new JLabel("Price");
        priceLabel.setPreferredSize(new Dimension(380, 30));
        priceLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(380, 30));
        JLabel stockLabel = new JLabel("Stock");
        stockLabel.setPreferredSize(new Dimension(380, 30));
        stockLabel.setHorizontalAlignment(JLabel.LEFT);
        JTextField stockField = new JTextField();
        stockField.setPreferredSize(new Dimension(380, 30));
        JLabel typeLabel = new JLabel("Type");
        typeLabel.setPreferredSize(new Dimension(380, 30));
        typeLabel.setHorizontalAlignment(JLabel.LEFT);
        JComboBox typeComboBox = new JComboBox<String>(
                new String[] { "Electronic", "Food", "Fashion", "Cosmetic", "Household", "Tool", "Sport", "Toy" });
        typeComboBox.setPreferredSize(new Dimension(380, 30));

        JButton saveBtn = new JButton("Save");
        saveBtn.addActionListener(e -> {
            String name = nameField.getText();
            String price = priceField.getText();
            String stock = stockField.getText();
            String type = typeComboBox.getSelectedItem().toString();
            if (name.isEmpty() || price.isEmpty() || stock.isEmpty() || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                product.setId(new Tools().genNewId(new InventoryService().getAllProductData()));
                product.setName(name);
                product.setPrice(price);
                product.setStock(stock);
                switch (type) {
                    case "Electronic" -> product.setType("1");
                    case "Food" -> product.setType("2");
                    case "Fashion" -> product.setType("3");
                    case "Cosmetic" -> product.setType("4");
                    case "Household" -> product.setType("5");
                    case "Tool" -> product.setType("6");
                    case "Sport" -> product.setType("7");
                    case "Toy" -> product.setType("8");
                }
                product.setImage(insertImage.getPath());
                saveProduct();
                newProductDialog.dispose();
                refreshPage();
            }
        });

        areaForm.add(nameLabel);
        areaForm.add(nameField);
        areaForm.add(priceLabel);
        areaForm.add(priceField);
        areaForm.add(stockLabel);
        areaForm.add(stockField);
        areaForm.add(typeLabel);
        areaForm.add(typeComboBox);
        areaForm.add(saveBtn);
        areaForm.setPreferredSize(new Dimension(400, 670));
        newProductDialog.add(areaForm);
        refreshPage();
        return newProductDialog;
    }

    private void saveProduct() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/ProductData.txt", true))) {
            writer.write(
                    product.getID() + "," + product.getName() + "," + product.getPrice() + "," + product.getDiscount()
                            + "," + product.getStock() + "," + product.getType() + "," + product.getImage() + "\n");
            new Tools().SaveFileCopy(insertImage.getOriginalPath(), insertImage.getPath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving product", "Error", JOptionPane.ERROR_MESSAGE);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowProduct.txt", true))) {
            writer.write(
                    product.getID() + "," + product.getName() + "," + product.getPrice() + "," + product.getDiscount()
                            + "," + product.getStock() + "," + product.getType() + "," + product.getImage() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving product", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refreshPage() {
        removeAll();
        add(Top(), BorderLayout.NORTH);
        add(body(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void refreshPage(String type) {
        removeAll();
        add(Top(), BorderLayout.NORTH);
        add(body(type), BorderLayout.CENTER);
        revalidate();
        repaint();
    }


    public String transformType(String type, String wantGet) {
        String result = "";
        if (wantGet.equals("word")) {
            switch (type) {
                case "1" -> result = "Electronic";
                case "2" -> result = "Food";
                case "3" -> result = "Fashion";
                case "4" -> result = "Cosmetic";
                case "5" -> result = "Household";
                case "6" -> result = "Tool";
                case "7" -> result = "Sport";
                case "8" -> result = "Toy";
            }
        } else if (wantGet.equals("number")) {
            switch (type) {
                case "Electronic" -> result = "1";
                case "Food" -> result = "2";
                case "Fashion" -> result = "3";
                case "Cosmetic" -> result = "4";
                case "Household" -> result = "5";
                case "Tool" -> result = "6";
                case "Sport" -> result = "7";
                case "Toy" -> result = "8";
            }
        }
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}

class Card extends JPanel {
    private Product product;
    private JButton editBtn = new JButton("Edit");
    private JButton deleteBtn = new JButton("Delete");
    private JButton plusStockBtn = new JButton("+");
    private JButton minusStockBtn = new JButton("-");
    private MainFrame mainFrame;
    private ManageProduct manageProduct;
    private int width;
    private JDialog infoProductDialog;
    private InsertImage insertImage = new InsertImage();

    public Card(String[] recode, MainFrame mainFrame, int width, ManageProduct manageProduct) {
        this.product = new Product(recode[0], recode[1], recode[2], recode[3], recode[4], recode[5], recode[6]);
        this.editBtn = new JButton("Edit Product");
        this.deleteBtn = new JButton("Delete Product");
        this.plusStockBtn = new JButton("Plus Stock");
        this.minusStockBtn = new JButton("Minus Stock");
        this.mainFrame = mainFrame;
        this.width = width;
        this.manageProduct = manageProduct;
        setUi();
    }
    
    private void setUi() {
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(width - 50, 40));
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 1));

        JLabel idLabel = new JLabel("ID: " + product.getID());
        idLabel.setPreferredSize(new Dimension(width / 12, 30));
        JLabel nameLabel = new JLabel("Name: " + product.getName());
        nameLabel.setPreferredSize(new Dimension(width / 3, 30));
        JLabel priceLabel = new JLabel("Price: " + product.getPrice());
        priceLabel.setPreferredSize(new Dimension(width / 6, 30));
        JLabel stockLabel = new JLabel("Stock: " + product.getStock());
        stockLabel.setPreferredSize(new Dimension(width / 8, 30));
        JLabel typeLabel = new JLabel("Type: " + manageProduct.transformType(product.getType(), "word"));
        typeLabel.setPreferredSize(new Dimension(width / 8, 30));

        JButton infoBtn = new JButton("more");
        infoBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        infoBtn.setForeground(Color.WHITE);
        infoBtn.addActionListener(e -> {
            infoProductDialog = infoProductDialog();
            infoProductDialog.setVisible(true);
        });

        add(idLabel);
        add(nameLabel);
        add(priceLabel);
        add(stockLabel);
        add(typeLabel);
        add(infoBtn);
    }

    private JDialog infoProductDialog() {
        float[] hsbValues = Color.RGBtoHSB(3, 153, 254, null);
        JDialog infoProductDialog = new JDialog(mainFrame, "Product Information", true);
        infoProductDialog.setSize(new Dimension(350, 620));
        infoProductDialog.setLocationRelativeTo(mainFrame);
        infoProductDialog.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel picLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(product.getImage());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        picLabel.setIcon(new ImageIcon(scaledImage));
        picLabel.setPreferredSize(new Dimension(300, 300));

        JLabel nameLabel = new JLabel("Name  :   " + product.getName());
        nameLabel.setPreferredSize(new Dimension(300, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setFont(new SetPreferences().getFont(16));
        JLabel priceLabel = new JLabel("Price  :   " + product.getPrice());
        priceLabel.setPreferredSize(new Dimension(300, 30));
        priceLabel.setHorizontalAlignment(JLabel.LEFT);
        priceLabel.setFont(new SetPreferences().getFont(16));
        JLabel stockLabel = new JLabel("Stock  :   " + product.getStock());
        stockLabel.setPreferredSize(new Dimension(300, 30));
        stockLabel.setHorizontalAlignment(JLabel.LEFT);
        stockLabel.setFont(new SetPreferences().getFont(16));

        String type = manageProduct.transformType(product.getType(), "word");
        JLabel typeLabel = new JLabel("Type  :   " + type);
        typeLabel.setPreferredSize(new Dimension(300, 30));
        typeLabel.setHorizontalAlignment(JLabel.LEFT);
        typeLabel.setFont(new SetPreferences().getFont(16));

        JPanel okPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton okBtn = new JButton("OK");
        okBtn.setPreferredSize(new Dimension(140, 30));
        okPanel.add(okBtn);

        editBtn.setPreferredSize(new Dimension(140, 30));
        deleteBtn.setPreferredSize(new Dimension(140, 30));
        plusStockBtn.setPreferredSize(new Dimension(140, 30));
        minusStockBtn.setPreferredSize(new Dimension(140, 30));
        editBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        deleteBtn.setBackground(Color.RED);
        plusStockBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        minusStockBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        okBtn.setBackground(Color.getHSBColor(hsbValues[0], hsbValues[1], hsbValues[2]));
        editBtn.setForeground(Color.WHITE);
        deleteBtn.setForeground(Color.WHITE);
        plusStockBtn.setForeground(Color.WHITE);
        minusStockBtn.setForeground(Color.WHITE);
        okBtn.setForeground(Color.WHITE);

        plusStockBtn.addActionListener(e -> {
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter the stock to add"));
            product.setStock(String.valueOf(Integer.parseInt(product.getStock()) + stock));
            saveEditProduct();
            refreshPageInfoProductDialog();
            manageProduct.refreshPage();
        });

        minusStockBtn.addActionListener(e -> {
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter the stock to minus"));
            product.setStock(String.valueOf(Integer.parseInt(product.getStock()) - stock));
            saveEditProduct();
            refreshPageInfoProductDialog();
            manageProduct.refreshPage();
        });

        editBtn.addActionListener(e -> {
            editProductDialog();
        });

        deleteBtn.addActionListener(e -> {
            deleteProduct();
            infoProductDialog.dispose();
            manageProduct.refreshPage();
        });

        okBtn.addActionListener(e -> {
            infoProductDialog.dispose();
        });

        infoProductDialog.add(picLabel);
        infoProductDialog.add(nameLabel);
        infoProductDialog.add(priceLabel);
        infoProductDialog.add(stockLabel);
        infoProductDialog.add(typeLabel);
        infoProductDialog.add(minusStockBtn);
        infoProductDialog.add(plusStockBtn);
        infoProductDialog.add(editBtn);
        infoProductDialog.add(deleteBtn);
        infoProductDialog.add(okPanel);

        return infoProductDialog;
    }

    @SuppressWarnings("rawtypes")
    private JDialog editProductDialog() {
        JDialog editProductDialog = new JDialog(mainFrame, "Edit Product", true);
        editProductDialog.setSize(new Dimension(350, 700));
        editProductDialog.setLocationRelativeTo(mainFrame);
        editProductDialog.setLayout(new FlowLayout(FlowLayout.CENTER));


        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setPreferredSize(new Dimension(300, 30));
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setFont(new SetPreferences().getFont(16));

        JTextField nameField = new JTextField(product.getName());
        nameField.setPreferredSize(new Dimension(300, 30));

        JLabel priceLabel = new JLabel("Price: ");
        priceLabel.setPreferredSize(new Dimension(300, 30));
        priceLabel.setHorizontalAlignment(JLabel.LEFT);
        priceLabel.setFont(new SetPreferences().getFont(16));

        JTextField priceField = new JTextField(product.getPrice());
        priceField.setPreferredSize(new Dimension(300, 30));

        JLabel typeLabel = new JLabel("Type: ");
        typeLabel.setPreferredSize(new Dimension(300, 30));
        typeLabel.setHorizontalAlignment(JLabel.LEFT);
        typeLabel.setFont(new SetPreferences().getFont(16));

        JComboBox typeComboBox = new JComboBox<String>(
                new String[] { "Electronic", "Food", "Fashion", "Cosmetic", "Household", "Tool", "Sport", "Toy" });
        typeComboBox.setPreferredSize(new Dimension(300, 30));
        String type = manageProduct.transformType(product.getType(), "word");
        typeComboBox.setSelectedItem(type);

        JButton saveBtn = new JButton("Save");

        saveBtn.addActionListener(e -> {
            product.setName(nameField.getText());
            product.setPrice(priceField.getText());
            product.setType(manageProduct.transformType(typeComboBox.getSelectedItem().toString(), "number"));
            saveEditProduct();
            editProductDialog.dispose();
            refreshPageInfoProductDialog();
            manageProduct.refreshPage();
        });

        editProductDialog.add(insertImage);
        editProductDialog.add(nameLabel);
        editProductDialog.add(nameField);
        editProductDialog.add(priceLabel);
        editProductDialog.add(priceField);
        editProductDialog.add(typeLabel);
        editProductDialog.add(typeComboBox);
        editProductDialog.add(saveBtn);
        editProductDialog.setVisible(true);
        return editProductDialog;
    }

    private void refreshPageInfoProductDialog() {
        if (infoProductDialog != null) {
            infoProductDialog.dispose();
            infoProductDialog = null;
        }
        
        infoProductDialog = infoProductDialog();
        infoProductDialog.revalidate();
        infoProductDialog.repaint();
        infoProductDialog.setVisible(true);
    }

    private void saveEditProduct() {
        String[][] productData = new InventoryService().getAllProductData();
        String[][] productDataNow = new InventoryService().getProductDataNow();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/ProductData.txt"))) {
            for (String[] recode : productData) {
                if (recode[0].equals(product.getID())) {
                    writer.write(product.getID() + "," + product.getName() + "," + product.getPrice() + ","
                            + product.getDiscount()
                            + "," + product.getStock() + "," + product.getType() + "," + insertImage.getPath() + "\n");
                } else {
                    writer.write(recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + ","
                            + recode[5] + "," + recode[6] + "\n");
                }
                new Tools().removeFile(product.getImage());
                product.setImage(insertImage.getPath());
            }
            new Tools().SaveFileCopy(insertImage.getOriginalPath(), insertImage.getPath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving product", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowProduct.txt"))) {
            for (String[] recode : productDataNow) {
                if (recode[0].equals(product.getID())) {
                    writer.write(product.getID() + "," + product.getName() + "," + product.getPrice() + ","
                            + product.getDiscount()
                            + "," + product.getStock() + "," + product.getType() + "," + insertImage.getPath() + "\n");
                } else {
                    writer.write(recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + ","
                            + recode[5] + "," + recode[6] + "\n");
                }
                new Tools().removeFile(product.getImage());
                product.setImage(insertImage.getPath());
            }
            new Tools().SaveFileCopy(insertImage.getOriginalPath(), insertImage.getPath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error saving product", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        String[][] productData = new InventoryService().getProductDataNow();
        int confirm = JOptionPane.showConfirmDialog(
                    infoProductDialog,
                    "Are you sure you want to delete this product?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/NowProduct.txt"))) {
                    for (String[] recode : productData) {
                        if (!recode[0].equals(product.getID())) {
                            writer.write(
                                    recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + ","
                                            + recode[5] + "," + recode[6] + "\n");
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, "Error deleting product", "Error", JOptionPane.ERROR_MESSAGE);
                }
    
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/backend/data/ProductData.txt"))) {
                    for (String[] recode : productData) {
                        if (!recode[0].equals(product.getID())) {
                            writer.write(
                                    recode[0] + "," + recode[1] + "," + recode[2] + "," + recode[3] + "," + recode[4] + ","
                                            + recode[5] + "," + recode[6] + "\n");
                        }
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, "Error deleting product", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
    }

}
