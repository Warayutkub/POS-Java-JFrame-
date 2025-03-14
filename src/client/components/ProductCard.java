package client.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resources.SetPreferences;

public class ProductCard extends JPanel {
    private SetPreferences setFontO = new SetPreferences();
    private int cardWidth = 275;
    private int cardHeight = 400;
    private String id;

    public ProductCard(){}

    public ProductCard(String id,String name,String price,String pathImg) {
        createGui(pathImg,name,price);
        this.id = id;
    }

    private void createGui(String pathImg,String name,String price) {
        setLayout(new FlowLayout(FlowLayout.CENTER, cardWidth/28, cardHeight/40));
        setPreferredSize(new Dimension(this.cardWidth,this.cardHeight));
        setBackground(Color.white);
        add(IMGShow(pathImg));
        add(TextName(name));
        add(TextPrice(price));
        add(BtnSend());
    }

    private JLabel IMGShow(String path) {
        int width = cardWidth-25;
        int height = width;
        JLabel image = new JLabel();
        image.setPreferredSize(new Dimension(width, height));
        image.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        image.setIcon(new ImageIcon(resizedImage));
        return image;
    }

    private JLabel TextName(String name) {
        JLabel nameP = new JLabel(name);
        nameP.setHorizontalAlignment(JLabel.CENTER);
        nameP.setFont(setFontO.getFont(cardHeight/17));
        nameP.setPreferredSize(new Dimension(cardWidth-25, cardHeight/12));
        return nameP;
    }

    private JLabel TextPrice(String price) {
        JLabel priceP = new JLabel(price + "$");
        priceP.setHorizontalAlignment(JLabel.CENTER);
        priceP.setFont(setFontO.getFont(cardHeight/17));
        priceP.setPreferredSize(new Dimension(cardWidth-25, cardHeight/17));
        return priceP;
    }
    
    private JButton BtnSend() {
        JButton Btn = new JButton("Add");
        Btn.setPreferredSize(new Dimension(cardWidth-25, cardHeight/8));
        Btn.setFont(setFontO.getFont(cardHeight/17));
        return Btn;
    }

    public  int getWidth(){return this.cardWidth;}
    public  int getHeight(){return this.cardHeight;}
    public  String getId(){return this.id;}


}
