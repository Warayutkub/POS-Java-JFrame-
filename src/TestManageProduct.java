import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TestManageProduct extends JFrame implements ActionListener {
    Container container;
    JLabel ButtonLabel, NewLabel , FromNewLabelName , FromNewLabelPrice , FromNewLabelQyt , ImagePreviewLabel , PicLabel , imageLabel;
    JButton NewButton , EdiButton, DelButton ,SaveButton,CancelButton , UploadImageButton;
    JPanel ButtonPanel , NewPanel ,FromNewPanelName,FromNewPanelPrice,FromNewPanelPic  ;
    JTextField TextName , TextPrice ,TextQyt;
    String imagePath = "";
    

    public TestManageProduct(){
        super("New Product");

        container = getContentPane();
        container.setLayout(new BorderLayout()); 
    
        initGui();
    
        setSize(500, 350);
        setLocationRelativeTo(null); // จัดให้อยู่กลางหน้าจอ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initGui(){
        JPanel formPanel = new JPanel(); // รวมฟอร์มทั้งหมด
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        guiNew();         // สร้าง NewPanel
        UploadPic();      // สร้าง FromNewPanelPic

        formPanel.add(NewPanel);
        formPanel.add(FromNewPanelPic);

        container.add(formPanel, BorderLayout.CENTER);
        SaveCanButton();
    }

    public void guiNew(){
        NewPanel = new JPanel();
        NewPanel.setPreferredSize(new Dimension(450,110));
        NewPanel.setBorder(new LineBorder(Color.GRAY,0));
        NewLabel = new JLabel();
        NewPanel.add(NewLabel);

        FromNewPanelName = new JPanel();
        FromNewPanelName.setLayout(new GridLayout(3,2));
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

        NewPanel.add(FromNewPanelName);
        container.add(NewPanel);
    }

    public void UploadPic(){
        FromNewPanelPic = new JPanel();
        FromNewPanelPic.setPreferredSize(new Dimension(400,100));
    
        JLabel imageLabel = new JLabel("Product Image : ");
        FromNewPanelPic.add(imageLabel);
    
        UploadImageButton = new JButton("UploadImage");
        UploadImageButton.setEnabled(true);
        UploadImageButton.addActionListener(this);
        FromNewPanelPic.add(UploadImageButton);
    
        ImagePreviewLabel = new JLabel("Haven't chosen a picture yet.", SwingConstants.CENTER);
        ImagePreviewLabel.setPreferredSize(new Dimension(100, 100));
        FromNewPanelPic.add(new JLabel("Preview :"));
        FromNewPanelPic.add(ImagePreviewLabel);
    
        container.add(FromNewPanelPic);
    }

    public void SaveCanButton(){
        ButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // ปุ่มอยู่ตรงกลาง

        SaveButton = new JButton("Save");
        SaveButton.addActionListener(this);

        CancelButton = new JButton("Cancel");
        CancelButton.addActionListener(this);

        ButtonPanel.add(SaveButton);
        ButtonPanel.add(CancelButton);

        container.add(ButtonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == SaveButton) {
            String name = TextName.getText().trim();
            String price = TextPrice.getText().trim();
            String quantity = TextQyt.getText().trim();

            if (!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                try (FileWriter writer = new FileWriter("./src/backend/data/ProductData.txt", true)) {
                    writer.write(name + "," + price + "," + quantity + "," + imagePath + "\n");
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
}

    
