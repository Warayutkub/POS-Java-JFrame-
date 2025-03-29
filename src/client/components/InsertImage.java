package client.components;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InsertImage extends JPanel {
    private final String defaultPathImage = "./src/backend/data/images/Default_image.png";
    private final String SaveImageDirection = "./src/backend/data/images/";

    private String pathSelect;
    private String pathFinal;
    private String originalPath;

    private int width = 300;
    private int height = 300;
    private JLabel areaImage = AreaImage(defaultPathImage);

    public InsertImage() {
        createGui();
    }

    public InsertImage(int size) {
        createGui();
        this.width = size;
    }

    private void createGui() {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        add(areaImage);
        add(AddButton());
    }

    private JLabel AreaImage(String path) {
        int widthImg = width - 50;
        int heightImg = widthImg;
        JLabel areaImage = new JLabel();
        areaImage.setPreferredSize(new Dimension(widthImg, heightImg));
        areaImage.setBorder(new LineBorder(Color.black));
        areaImage.setIcon(null);
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(widthImg, heightImg, Image.SCALE_SMOOTH);
        areaImage.setIcon(new ImageIcon(resizedImage));
        return areaImage;
    }

    private JButton AddButton() {
        JButton Btn = new JButton("Insert Image");
        Btn.setPreferredSize(new Dimension(width - 100, height / 12));
        Btn.addActionListener(e -> {
            chooseImage();
            areaImage = AreaImage(pathSelect);
            removeAll();
            add(areaImage);
            add(Btn);
            revalidate();
            repaint();
        });
        return Btn;
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (.jpg, .png)", "jpg", "png");
        fileChooser.setFileFilter(filter);

        int option = fileChooser.showOpenDialog(null);

        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathFinal = SaveImageDirection + selectedFile.getName();
            pathSelect = String.valueOf(selectedFile.toPath());
            originalPath = pathSelect;

        }

    }

    public String getPath() {
        return this.pathFinal;
    }
    public String getOriginalPath(){
        return this.originalPath;
    }

}
