package client.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.LineBorder;

import backend.models.Products.Product;
import backend.services.InventoryService;
import client.MainFrame;
import resources.Tools;

public class ManageProduct extends JPanel implements ActionListener {
    private int width = 0;
    private MainFrame mainFrame;
    private InsertImage insertImage = new InsertImage();
    private Product product = new Product();

    public ManageProduct(MainFrame mainFrame, int width) {
        this.mainFrame = mainFrame;
        this.width = width;

        setLayout(new BorderLayout());
        add(Top(), BorderLayout.NORTH);
        add(body(), BorderLayout.CENTER);
    }

    private JPanel Top() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(Color.RED);
        topPanel.setPreferredSize(new Dimension(width, 100));
        JButton newProductBtn = new JButton("New Product");
        newProductBtn.addActionListener(e -> {
            newProductDialog().setVisible(true);
        });

        topPanel.add(newProductBtn);
        return topPanel;
    }

    private JPanel body() {
        JPanel bodyPanel = new JPanel();
        bodyPanel.setBackground(Color.BLUE);
        bodyPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bodyPanel.setPreferredSize(new Dimension(width, mainFrame.getHeight() - 100));

        for (String[] recode : new InventoryService().getAllProductData()) {
            System.out.println(Arrays.toString(recode));
            bodyPanel.add(new Card(recode, mainFrame));
        }
        return bodyPanel;
    }

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
    }

    private void refreshPage() {
        removeAll();
        add(Top(), BorderLayout.NORTH);
        add(body(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}

class Card extends JPanel {
    private Product product;
    private JButton editBtn;
    private JButton deleteBtn;
    private JButton plusStockBtn;
    private JButton minusStockBtn;
    private MainFrame mainFrame;

    public Card(String[] recode, MainFrame mainFrame) {
        this.product = new Product(recode[0], recode[1], recode[2], recode[3], recode[4], recode[5], recode[6]);
        this.editBtn = new JButton("Edit");
        this.deleteBtn = new JButton("Delete");
        this.plusStockBtn = new JButton("+");
        this.minusStockBtn = new JButton("-");
        this.mainFrame = mainFrame;
        setUi();
    }

    private void setUi() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK, 1));

        JLabel nameLabel = new JLabel(product.getName());
        JLabel priceLabel = new JLabel(product.getPrice());
        JLabel stockLabel = new JLabel(product.getStock());
        JLabel typeLabel = new JLabel(product.getType());

        JButton infoBtn = new JButton("more");
        infoBtn.addActionListener(e -> {
            infoProductDialog().setVisible(true);
        });

        add(nameLabel);
        add(priceLabel);
        add(stockLabel);
        add(typeLabel);
        add(infoBtn);
    }

    private JDialog infoProductDialog() {
        JDialog infoProductDialog = new JDialog(mainFrame, "Product Information", true);
        infoProductDialog.setSize(new Dimension(450, 670));
        infoProductDialog.setLocationRelativeTo(mainFrame);
        infoProductDialog.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel picLabel = new JLabel(product.getImage());
        JLabel nameLabel = new JLabel(product.getName());
        JLabel priceLabel = new JLabel(product.getPrice());
        JLabel stockLabel = new JLabel(product.getStock());
        JLabel typeLabel = new JLabel(product.getType());
        
        infoProductDialog.add(picLabel);
        infoProductDialog.add(nameLabel);
        infoProductDialog.add(priceLabel);
        infoProductDialog.add(stockLabel);
        infoProductDialog.add(typeLabel);
        
        return infoProductDialog;
    }
}
