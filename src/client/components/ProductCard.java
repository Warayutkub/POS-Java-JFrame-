package client.components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.services.InventoryService;
import resources.SetPreferences;

public class ProductCard extends JPanel {
    private DecimalFormat format = new DecimalFormat("#,###.##");
    private SetPreferences setFontO = new SetPreferences();
    private int cardWidth = 172;
    private int cardHeight = 248;
    private String pathImg;
    private String name;
    private String price;
    private String id;
    private Cart cart;
    private String Stock;

    public ProductCard(){}

    public ProductCard(String id,Cart cart) {
        this.id = id;
        genName();
        this.cart = cart;
        createGui();
    }

    private void createGui() {
        setLayout(new FlowLayout(FlowLayout.CENTER, cardWidth/28, cardHeight/40));
        setPreferredSize(new Dimension(this.cardWidth,this.cardHeight));
        setBackground(Color.white);
        add(IMGShow());
        add(TextName(this.name));
        add(TextPrice(this.price));
        add(BtnSend());
    }

    private JLabel IMGShow() {
        int width = cardWidth-25;
        int height = width;
        JLabel image = new JLabel();
        image.setPreferredSize(new Dimension(width, height));
        image.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon originalIcon = new ImageIcon(this.pathImg);
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
        JLabel priceP = new JLabel(format.format(Double.parseDouble(price)) + "$");
        priceP.setHorizontalAlignment(JLabel.CENTER);
        priceP.setFont(setFontO.getFont(cardHeight/17));
        priceP.setPreferredSize(new Dimension(cardWidth-25, cardHeight/17));
        return priceP;
    }
    
    private JButton BtnSend() {
        JButton Btn = new JButton("Add");
        Btn.setBackground(Color.WHITE); // Set a valid background color
        Btn.setPreferredSize(new Dimension(cardWidth-25, cardHeight/8));
        Btn.setFont(setFontO.getFont(cardHeight/17));
        if (this.Stock.equals("0")){
            Btn.setEnabled(false);
            Btn.setText("Out Of Stock");
        }else{
            Btn.addActionListener(e -> {
                if (!cart.CheckProduct(this.id)){
                    if (cart.getStoreProduct().size() == 0) {
                        cart.setCart();
                    }
                    cart.addProductToStoreID(id);
                    cart.addProduct(this);
                    cart.revalidate();
                    cart.repaint();
                }
            });
        }
        return Btn;
    }

    private void genName(){
        String[][] data = new InventoryService().getAllProductData();
        
        for (String[] recode : data){
            if (recode[0].equals(this.id)){
                this.name = recode[1];
                this.price = recode[2];
                this.Stock = recode[4];
                this.pathImg = recode[6];
            }
        }
    }


    public  int getWidth(){return this.cardWidth;}
    public  int getHeight(){return this.cardHeight;}
    public  String getId(){return this.id;}
    public String getName(){return this.name;}
    public String getPrice(){return this.price;}


}
